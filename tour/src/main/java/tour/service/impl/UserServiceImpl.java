package tour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.JwtUtils;
import tour.base.utils.MD5Util;
import tour.base.utils.UserThreadLocal;
import tour.entity.User;
import tour.mapper.UserMapper;
import tour.po.UserPO;
import tour.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserThreadLocal userThreadLocal;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public ApiResponse login(UserPO userPo, HttpServletRequest request) {
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userPo.getUsername())
                .eq(User::getPassword, MD5Util.MD5Encode(userPo.getPassword(), null))
        );
        if (user == null) {
            return new ApiResponse("401", "用户名或密码输入有误, 请重新输入...");
        }
        String token = jwtUtils.generateToken(user.getUsername());
        ApiResponse apiResponse = new ApiResponse("200", "登录成功",user.getId());
        apiResponse.setToken(token);
        return apiResponse;

    }

    @Override
    public ApiResponse register(UserPO userPo) {
        User user = getOne(new LambdaUpdateWrapper<User>().eq(User::getUsername, userPo.getUsername()));
        if (user != null){
            return new ApiResponse("405","该用户已被注册");
        }else {
            save(new User(userPo.getUsername(), MD5Util.MD5Encode(userPo.getPassword(), ""), 10000));
            return new ApiResponse("200","注册成功");
        }

    }

    @Override
    public void settlement(int price) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getId, userThreadLocal.get()));
        update(new LambdaUpdateWrapper<User>().eq(User::getId,userThreadLocal.get()).set(User::getAccount, user.getAccount()-price));
    }

    @Override
    public User getUserByUsername(String username) {
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return user;
    }
}
