package tour.entity;

import tour.base.database.EntityBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Taxi对象", description = "")
public class Taxi extends EntityBase {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("车型")
    private String carType;

    @ApiModelProperty("已乘人数")
    private Integer used;

    @ApiModelProperty("最大乘客数")
    private Integer maxNum;

    @ApiModelProperty("路线")
    private String path;

    @ApiModelProperty("司机联系方式")
    private String phone;

    @ApiModelProperty("单人价格")
    private int price;

}
