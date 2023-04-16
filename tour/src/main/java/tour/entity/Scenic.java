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
@ApiModel(value = "Scenic对象", description = "")
public class Scenic extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("景点名称")
    private String name;

    @ApiModelProperty("景点描述")
    private String introduction;

    @ApiModelProperty("景点图片")
    private String url;

    @ApiModelProperty("评分")
    private double score;

    @ApiModelProperty("门票价格")
    private Integer price;

    @ApiModelProperty("排名描述")
    private String ranking;

    @ApiModelProperty("开放时间")
    private String openTime;
    //评论数量
    @TableField(exist = false)
    private int commentCnt;
}
