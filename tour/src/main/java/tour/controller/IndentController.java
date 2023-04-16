package tour.controller;

import tour.base.utils.ApiResponse;
import tour.po.IndentPO.*;
import tour.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indent")
public class IndentController {
    @Autowired
    IndentService indentService;

    @PostMapping("/list")
    public ApiResponse getAllIndent() {
        return indentService.getAllIndent();
    }

    @PostMapping("/scenic")
    public ApiResponse addScenicIndent(@RequestBody AddScenicPO addScenicPO) {
        return indentService.addScenicIndent(addScenicPO);
    }

    @PostMapping("/homestay")
    public ApiResponse addHomestayIndent(@RequestBody AddHomestayPO addHomestayPO) {
        return indentService.addHomestayIndent(addHomestayPO);
    }

    @PostMapping("/goods")
    public ApiResponse addGoodsIndent(@RequestBody AddGoodsPO addGoodsPO) {
        return indentService.addGoodsIndent(addGoodsPO);
    }

    @PostMapping("/taxi")
    public ApiResponse addTaxiIndent(@RequestBody AddTaxiPO addTaxiPO) {
        return indentService.addTaxiIndent(addTaxiPO);
    }

    @DeleteMapping("/")
    public ApiResponse deleteIndent(@RequestBody IndentDeletePO indentDeletePO) {
        return indentService.deleteIndent(indentDeletePO);
    }

    @PutMapping("/homestay")
    public ApiResponse updateHomestayIndent(@RequestBody UpdateHomestayPO updateHomestayPO) {
        return indentService.updateHomestayIndent(updateHomestayPO);
    }

    @PutMapping("/scenic")
    public ApiResponse updateScenicIndent(@RequestBody UpdateScenicPO updateScenicPO) {
        return indentService.updateScenicIndent(updateScenicPO);
    }

    @PutMapping("/goods")
    public ApiResponse updateGoodsIndent(@RequestBody UpdateGoodsPO updateGoodsPO) {
        return indentService.updateGoodsIndent(updateGoodsPO);
    }

    @PutMapping("/taxi")
    public ApiResponse updateTaxiIndent(@RequestBody UpdateTaxiPO updateTaxiPO) {
        return indentService.updateTaxiIndent(updateTaxiPO);
    }
    @PutMapping("/settlement")
    public ApiResponse settlement(@RequestParam("indentId") long indentId) {
        return indentService.settlement(indentId);
    }
}

