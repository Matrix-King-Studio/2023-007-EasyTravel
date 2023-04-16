package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import tour.po.UserPO;

import javax.servlet.http.HttpServletRequest;


public interface UserService extends IService<User> {
    ApiResponse login(UserPO userPo, HttpServletRequest request);
    ApiResponse register(UserPO userPo);

    void settlement(int price);
    User getUserByUsername(String username);
}
