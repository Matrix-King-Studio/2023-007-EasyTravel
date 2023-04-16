package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;


public interface GoodsService extends IService<Goods> {
    ApiResponse getAllGoods();
    ApiResponse getGoodsByName(String name);
    ApiResponse getGoodsByCondition(String name);
    Goods getGoods(String name);
}
