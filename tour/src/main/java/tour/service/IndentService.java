package tour.service;

import tour.base.utils.ApiResponse;
import tour.entity.Indent;
import com.baomidou.mybatisplus.extension.service.IService;

import tour.po.IndentPO.*;


public interface IndentService extends IService<Indent> {

    ApiResponse getAllIndent();

    Indent getIndentById(long id);

    ApiResponse deleteIndent(IndentDeletePO indentDeletePO);

    ApiResponse addScenicIndent(AddScenicPO addScenicPO);

    ApiResponse addHomestayIndent(AddHomestayPO addHomestayPO);

    ApiResponse addGoodsIndent(AddGoodsPO addGoodsPO);

    ApiResponse addTaxiIndent(AddTaxiPO addTaxiPO);

    ApiResponse updateHomestayIndent(UpdateHomestayPO updateHomestayPO);

    ApiResponse updateScenicIndent(UpdateScenicPO updateScenicPO);

    ApiResponse updateGoodsIndent(UpdateGoodsPO updateGoodsPO);

    ApiResponse updateTaxiIndent(UpdateTaxiPO updateTaxiPO);

    ApiResponse settlement(long indentId);
}
