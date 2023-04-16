package tour.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.RedisKeys;
import tour.base.utils.RedisUtil;
import tour.entity.Scenic;
import tour.mapper.ScenicMapper;
import tour.service.ScenicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScenicServiceImpl extends ServiceImpl<ScenicMapper, Scenic> implements ScenicService {

    @Autowired
    RedisUtil redisUtil;
    @Override
    public ApiResponse getAllScenic() {
        List<Scenic> scenicList = list();
        if (scenicList.size() != 0){
            for (Scenic scenic: scenicList){
                scenic.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(scenic.getId(),"scenic")).intValue());
            }
        }
        return  new ApiResponse(scenicList);
    }

    @Override
    public ApiResponse getScenicByName(String name) {
        Scenic scenic = getOne(new LambdaQueryWrapper<Scenic>()
                .eq(Scenic::getName, name));
        if (scenic == null){
            return new ApiResponse("400","未找到该内容");
        }
        scenic.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(scenic.getId(),"scenic")).intValue());
        return new ApiResponse(scenic);
    }

    @Override
    public ApiResponse getScenicByCondition(String name) {
        List<Scenic> scenicList = list(new LambdaQueryWrapper<Scenic>()
                .like(StrUtil.isNotBlank(name), Scenic::getName, name));
        if (scenicList.size() == 0){
            return new ApiResponse("404","没有找到相关信息");
        }
        for (Scenic scenic: scenicList){
            scenic.setCommentCnt(redisUtil.sSize(RedisKeys.getArticleCommentKey(scenic.getId(),"scenic")).intValue());
        }
        return new ApiResponse(scenicList);
    }

    @Override
    public Scenic getScenic(String name) {
        Scenic scenic = getOne(new LambdaQueryWrapper<Scenic>()
                .eq(Scenic::getName, name));
        return scenic;
    }
}
