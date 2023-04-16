package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Homestay;
import com.baomidou.mybatisplus.extension.service.IService;


public interface HomestayService extends IService<Homestay> {

    ApiResponse getAllHomestay();

    ApiResponse getHomestayByName(String name);
    ApiResponse getHomestayByCondition(String name);
    Homestay getHomestay(String name);
}
