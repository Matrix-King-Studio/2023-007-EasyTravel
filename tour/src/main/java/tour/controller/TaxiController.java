package tour.controller;


import tour.base.utils.ApiResponse;
import tour.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/taxi")
public class TaxiController {
    @Autowired
    TaxiService taxiService;

    @GetMapping("/{id}")
    public ApiResponse getTaxiByName(@PathVariable("id") Long id){
        return taxiService.getTaxiById(id);
    }
    @GetMapping("/list")
    public ApiResponse getAllTaxi(){
        return taxiService.getAllTaxi();
    }
    @GetMapping("/condition/{name}")
    public ApiResponse getHomestayByCondition(@PathVariable("name") String name) {
        return taxiService.getTaxiByCondition(name);
    }
}

