package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Taxi;
import com.baomidou.mybatisplus.extension.service.IService;


public interface TaxiService extends IService<Taxi> {

    ApiResponse getTaxiById(Long id);

    ApiResponse getAllTaxi();
    ApiResponse getTaxiByCondition(String name);

    Taxi getTaxi(Long id);

    void updateTaxiIncreaseUsed(long id,int count);

    void deleteTaxiById(long id);
    Taxi getTaxiByName(String name);
    void updateTaxiDecreaseUsed(long id,int count);
}
