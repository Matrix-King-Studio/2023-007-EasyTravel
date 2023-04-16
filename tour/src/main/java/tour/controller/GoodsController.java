package tour.controller;


import tour.base.utils.ApiResponse;
import tour.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/{name}")
    public ApiResponse getGoodsByName(@PathVariable("name") String name){
        return goodsService.getGoodsByName(name);
    }
    @GetMapping("/list")
    public ApiResponse getAllGoods(){
        return goodsService.getAllGoods();
    }
    @GetMapping("/condition/{name}")
    public ApiResponse getHomestayByCondition(@PathVariable("name") String name) {
        return goodsService.getGoodsByCondition(name);
    }
}

