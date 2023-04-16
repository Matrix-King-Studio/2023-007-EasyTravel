package tour.controller;


import tour.base.utils.ApiResponse;
import tour.base.utils.UserThreadLocal;
import tour.entity.User;
import tour.po.UserPO;
import tour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserThreadLocal userThreadLocal;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserPO userPo, HttpServletRequest request){
        return userService.login(userPo,request);
    }
    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserPO userPo){
        return userService.register(userPo);
    }

    @PostMapping("/info")
    public ApiResponse<User> info() {
        return new ApiResponse<>(userService.getById(userThreadLocal.get()));
    }

}

