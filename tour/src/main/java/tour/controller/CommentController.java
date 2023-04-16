package tour.controller;


import tour.base.utils.ApiResponse;
import tour.entity.Comment;
import tour.po.CommentPO;
import tour.po.LovePO;
import tour.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    //发表评论
    @PostMapping("/send")
    public ApiResponse send(@RequestBody Comment comment){
        return commentService.sendComment(comment);
    }
    //删除评论
    @DeleteMapping("/")
    public ApiResponse deleteComment(@RequestBody CommentPO commentPo){

        return commentService.deleteComment(commentPo);
    }
    //获取评论列表
    @GetMapping("/list")
    public ApiResponse getAllComment(@RequestParam Map<String, Object> params){
        return commentService.getAllComment(params);
    }
    //点赞评论
    @PostMapping("/love")
    public ApiResponse loveComment(@RequestBody LovePO lovePO){
        return commentService.loveComment(lovePO);
    }

}

