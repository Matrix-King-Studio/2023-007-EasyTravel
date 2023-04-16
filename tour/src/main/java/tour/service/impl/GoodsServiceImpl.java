package tour.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.RedisKeys;
import tour.base.utils.RedisUtil;
import tour.entity.Goods;
import tour.mapper.GoodsMapper;
import tour.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    RedisUtil redisUtil;
    @Override
    public ApiResponse getAllGoods() {
        List<Goods> goodsList = list();
        if (goodsList.size() != 0){
            for (Goods goods : goodsList){
                goods.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(goods.getId(),"goods")).intValue());
            }
        }

        return new ApiResponse(goodsList);
    }

    @Override
    public ApiResponse getGoodsByName(String name) {
        Goods goods = getOne(new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsName, name));
        if (goods == null){
            return  new ApiResponse("400","未找到该内容");
        }
        goods.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(goods.getId(),"goods")).intValue());
        return new ApiResponse(goods);
    }
    @Override
    public ApiResponse getGoodsByCondition(String name) {
        List<Goods> goodsList = list(new LambdaQueryWrapper<Goods>()
                .like(StrUtil.isNotBlank(name), Goods::getGoodsName, name));

        if (goodsList.size() == 0){
            return new ApiResponse("404","没有找到相关信息");
        }
        for (Goods goods : goodsList){
            goods.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(goods.getId(),"goods")).intValue());
        }
        return new ApiResponse(goodsList);
    }

    @Override
    public Goods getGoods(String name) {
        Goods goods = getOne(new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsName, name));
        return goods;
    }

}
