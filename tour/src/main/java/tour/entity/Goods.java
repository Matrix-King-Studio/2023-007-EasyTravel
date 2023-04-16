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
@ApiModel(value = "Goods对象", description = "")
public class Goods extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店名")
    private String shopName;

    @ApiModelProperty("特产名称")
    private String goodsName;

    @ApiModelProperty("特产图片")
    private String url;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("简介")
    private String introduction;

    //评论数量
    @TableField(exist = false)
    private int commentCnt;

}
