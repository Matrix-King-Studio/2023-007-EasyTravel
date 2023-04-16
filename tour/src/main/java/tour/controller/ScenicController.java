package tour.controller;


import tour.base.utils.ApiResponse;
import tour.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/scenic")
public class ScenicController {
    @Autowired
    ScenicService scenicService;

    @GetMapping("/list")
    public ApiResponse getAllScenic(){
        return scenicService.getAllScenic();
    }
    @GetMapping("/{name}")
    public ApiResponse getScenicByName(@PathVariable("name") String name){
        return scenicService.getScenicByName(name);
    }
    @GetMapping("/condition/{name}")
    public ApiResponse getScenicByCondition(@PathVariable("name") String name){
        return scenicService.getScenicByCondition(name);
    }

}

