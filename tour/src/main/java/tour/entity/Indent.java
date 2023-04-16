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
@ApiModel(value = "Order对象", description = "")
public class Indent extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("购买数量")
    private Integer count;

    @ApiModelProperty("单价")
    private Integer unitPrice;

    @ApiModelProperty("总价")
    private Integer price;

    @ApiModelProperty("详情")
    private String detail;

    @ApiModelProperty("类型: 民宿/景点/购物/拼车")
    private String type;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("订单状态 0-未支付 1-已结算")
    private Integer status;

    @ApiModelProperty("图片url")
    @TableField(exist = false)
    private String url;

    @ApiModelProperty("拼车路径")
    @TableField(exist = false)
    private String path;

    @ApiModelProperty("文章id")
    @TableField(exist = false)
    private Long carId;



    public Indent() {
    }

    public Indent(String name, Integer count, Integer unitPrice, Integer price, String detail, String type, Integer status) {
        this.name = name;
        this.count = count;
        this.unitPrice = unitPrice;
        this.price = price;
        this.detail = detail;
        this.type = type;
        this.status = status;
    }

    public Indent(Long carId,String name, Integer count, Integer unitPrice, Integer price, String detail, String type, Long userId, Integer status) {
        this.name = name;
        this.count = count;
        this.unitPrice = unitPrice;
        this.price = price;
        this.detail = detail;
        this.type = type;
        this.userId = userId;
        this.status = status;
    }
    public Indent(String name, Integer count, Integer unitPrice, Integer price, String detail, String type, Long userId, Integer status) {
        this.name = name;
        this.count = count;
        this.unitPrice = unitPrice;
        this.price = price;
        this.detail = detail;
        this.type = type;
        this.userId = userId;
        this.status = status;
    }

}
