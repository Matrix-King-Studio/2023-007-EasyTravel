package tour.po.IndentPO;

import lombok.Data;


@Data
public class UpdateGoodsPO {
    //收货人
    String consignee;
    String phone;
    //收获地址
    String shippingAddress;
    //订单id
    long indentId;
}
