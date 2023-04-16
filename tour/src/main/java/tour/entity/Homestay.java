package tour.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import tour.base.database.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Homestay对象", description = "")
@ToString
public class Homestay extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("民俗名称")
    private String name;

    @ApiModelProperty("民宿地址")
    private String address;

    @ApiModelProperty("联系方式")
    private String phone;

    @ApiModelProperty("联系人姓名")
    private String sellerName;

    @ApiModelProperty("民宿简介")
    private String introduction;

    @ApiModelProperty("评分")
    private Double score;

    @ApiModelProperty("民宿标签")
    private String label;

    @ApiModelProperty("民宿价格")
    private Integer price;

    @ApiModelProperty("图片")
    private String url;
    //评论数量
    @TableField(exist = false)
    private int commentCnt;
}
