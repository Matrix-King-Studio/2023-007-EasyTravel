package tour.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.RedisKeys;
import tour.base.utils.RedisUtil;
import tour.entity.Homestay;
import tour.mapper.HomestayMapper;
import tour.service.HomestayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



@Service
@Slf4j
public class HomestayServiceImpl extends ServiceImpl<HomestayMapper, Homestay> implements HomestayService {
    @Resource
    RedisUtil redisUtil;

    @Override
    public ApiResponse getAllHomestay() {
        List<Homestay> homestayList = list();
        if (homestayList.size() != 0){
            for (Homestay homestay:homestayList){
                homestay.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(homestay.getId(),"homestay")).intValue());
            }
        }

        return new ApiResponse(homestayList);
    }

    @Override
    public ApiResponse getHomestayByName(String name) {
        Homestay homestay = getOne(new LambdaQueryWrapper<Homestay>()
                .eq(Homestay::getName, name));

        if (homestay == null) {
            return new ApiResponse("400", "未找到该内容");
        }
        homestay.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(homestay.getId(),"homestay")).intValue());
        log.info(homestay.toString());
        return new ApiResponse(homestay);
    }
    @Override
    public ApiResponse getHomestayByCondition(String name) {
        List<Homestay> homestayList = list(new LambdaQueryWrapper<Homestay>()
                .like(StrUtil.isNotBlank(name), Homestay::getName, name));

        if (homestayList.size() == 0){
            return new ApiResponse("404","没有找到相关信息");
        }
        for (Homestay homestay:homestayList){
            homestay.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(homestay.getId(),"homestay")).intValue());
        }
        return new ApiResponse(homestayList);
    }

    @Override
    public Homestay getHomestay(String name) {
        Homestay homestay = getOne(new LambdaQueryWrapper<Homestay>()
                .eq(Homestay::getName, name));
        return homestay;
    }
}
