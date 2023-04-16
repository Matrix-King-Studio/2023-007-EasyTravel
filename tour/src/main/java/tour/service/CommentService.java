package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import tour.po.CommentPO;
import tour.po.LovePO;

import java.util.Map;


public interface CommentService extends IService<Comment> {

    ApiResponse sendComment(Comment comment);

    ApiResponse deleteComment(CommentPO commentPo);

    ApiResponse getAllComment(Map<String, Object> params);

    ApiResponse loveComment(LovePO lovePO);

    Comment getCommentById(long id);

}
