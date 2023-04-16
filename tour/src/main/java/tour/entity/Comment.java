package tour.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import tour.base.database.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Comment对象", description = "")
public class Comment extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("类型: 民宿/景点/购物/拼车")
    private String type;

    @ApiModelProperty("帖子信息 id")
    private Long articleId;

    @ApiModelProperty("用户 id")
    private Long userId;

    @ApiModelProperty("用户名称")
    @TableField(exist = false)
    private String userName;

    @ApiModelProperty("评论内容")
    private String content;

    //评论是否点赞  0-未点赞 1-已点赞
    @TableField(exist = false)
    private Integer loveStatus = 0;

    //评论的点赞数
    @TableField(exist = false)
    private Long loveCnt = 0l;
}
