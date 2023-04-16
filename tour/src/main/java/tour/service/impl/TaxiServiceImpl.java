package tour.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.RedisUtil;
import tour.entity.Taxi;
import tour.mapper.TaxiMapper;
import tour.service.TaxiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaxiServiceImpl extends ServiceImpl<TaxiMapper, Taxi> implements TaxiService {

    @Autowired
    RedisUtil redisUtil;
    @Override
    public ApiResponse getAllTaxi() {
        List<Taxi> taxiList = list();
        return new ApiResponse(taxiList);
    }

    @Override
    public ApiResponse getTaxiById(Long id) {
        Taxi taxi = getOne(new LambdaQueryWrapper<Taxi>().eq(Taxi::getId, id));
        if (taxi == null){
            return  new ApiResponse("400","未找到该内容");
        }
        return new ApiResponse(taxi);
    }
    @Override
    public ApiResponse getTaxiByCondition(String name) {
        List<Taxi> taxiList = list(new LambdaQueryWrapper<Taxi>()
                .like(StrUtil.isNotBlank(name), Taxi::getPath, name));
        if (taxiList.size() == 0){
            return new ApiResponse("404","没有找到相关信息");
        }
        return new ApiResponse(taxiList);
    }

    @Override
    public Taxi getTaxi(Long id) {
        Taxi taxi = getOne(new LambdaQueryWrapper<Taxi>().eq(Taxi::getId, id));
        return taxi;
    }

    @Override
    public void deleteTaxiById(long id) {
        remove(new LambdaQueryWrapper<Taxi>().eq(Taxi::getId,id));
    }

    @Override
    public Taxi getTaxiByName(String name) {
        return getOne(new LambdaQueryWrapper<Taxi>().eq(Taxi::getCarType, name));
    }

    @Override
    public void updateTaxiIncreaseUsed(long id,int count) {
        Taxi taxi = getTaxi(id);
        Integer used = taxi.getUsed();
        update(new LambdaUpdateWrapper<Taxi>().eq(Taxi::getId,id).set(Taxi::getUsed,used+count));

    }

    @Override
    public void updateTaxiDecreaseUsed(long id,int count) {
        Taxi taxi = getTaxi(id);
        Integer used = taxi.getUsed();
        update(new LambdaUpdateWrapper<Taxi>().eq(Taxi::getId,id).set(Taxi::getUsed,used-count));

    }


}
