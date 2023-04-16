package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Scenic;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ScenicService extends IService<Scenic> {
    ApiResponse getAllScenic();
    ApiResponse getScenicByName(String name);

    ApiResponse getScenicByCondition(String name);

    Scenic getScenic(String name);
}
