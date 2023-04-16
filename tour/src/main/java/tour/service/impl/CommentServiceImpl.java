package tour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.RedisKeys;
import tour.base.utils.RedisUtil;
import tour.base.utils.UserThreadLocal;
import tour.entity.Comment;
import tour.entity.User;
import tour.mapper.CommentMapper;
import tour.po.CommentPO;
import tour.po.LovePO;
import tour.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import tour.service.UserService;

import java.util.List;
import java.util.Map;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserThreadLocal userThreadLocal;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    @Override
    //传入的articleId是当前景点或民宿的id
    public ApiResponse sendComment(Comment comment) {
        comment.setUserId(userThreadLocal.get());
        comment.setLoveStatus(0);
        save(comment);
        redisUtil.sAdd(RedisKeys.getArticleCommentKey(comment.getArticleId(),comment.getType()),userThreadLocal.get());
        return new ApiResponse(userThreadLocal.get());
    }

    @Override
    public ApiResponse deleteComment(CommentPO commentPo) {
        Comment comment = getOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, commentPo.getCommentId()));
        if (comment.getUserId().equals(userThreadLocal.get())){
            removeById(comment.getId());
            return new ApiResponse();
        }
        return new ApiResponse("403","没有权限删除");
    }

    @Override
    public ApiResponse getAllComment(@RequestParam Map<String, Object> params) {
        List<Comment> commentList = list(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getType, params.get("type"))
                .eq(Comment::getArticleId, params.get("articleId")));
        for (Comment comment: commentList){
            User user = userService.getById(comment.getUserId());
            comment.setUserName(user.getUsername());
            comment.setLoveCnt(redisUtil.sSize(RedisKeys.getCommentLoveKey(comment.getId())));
            comment.setLoveStatus(userThreadLocal.get() == null ? 0 :
                    (redisUtil.sIsMember(RedisKeys.getCommentLoveKey(comment.getId()), userThreadLocal.get()) ? 1 : 0));
        }
        return new ApiResponse(commentList);
    }


    @Override
    public ApiResponse loveComment(LovePO lovePO) {
        if (lovePO.getStatus() == 0){
            redisUtil.sAdd(RedisKeys.getCommentLoveKey(lovePO.getId()),userThreadLocal.get());
//            Comment comment = getCommentById(lovePO.getId());
//            comment.setLoveStatus(1);
//            Long cnt = comment.getLoveCnt();
//            comment.setLoveCnt(cnt + 1);
            return new ApiResponse("200","点赞成功");
        }else {
            redisUtil.sRemove(RedisKeys.getCommentLoveKey(lovePO.getId()),userThreadLocal.get());
            return new ApiResponse("200","取消点赞成功");
        }
    }

    @Override
    public Comment getCommentById(long id) {
        return getOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, id));
    }


}
