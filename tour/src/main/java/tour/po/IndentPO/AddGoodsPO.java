package tour.po.IndentPO;

import lombok.Data;


@Data
public class AddGoodsPO {
    int count;
    String consignee;
    String phone;
    String shippingAddress;
    String goodsName;
}
