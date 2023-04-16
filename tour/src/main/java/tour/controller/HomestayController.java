package tour.controller;


import tour.base.utils.ApiResponse;
import tour.service.HomestayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/homestay")
public class HomestayController {
    @Autowired
    HomestayService homestayService;

    @GetMapping("/list")
    public ApiResponse getAllHomestay() {
        return homestayService.getAllHomestay();
    }

    @GetMapping("/{name}")
    public ApiResponse getHomestayByName(@PathVariable String name) {
        return homestayService.getHomestayByName(name);
    }

    @GetMapping("/condition/{name}")
    public ApiResponse getHomestayByCondition(@PathVariable("name") String name) {
        return homestayService.getHomestayByCondition(name);
    }
}

