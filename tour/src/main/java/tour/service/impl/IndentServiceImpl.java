package tour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import tour.base.utils.ApiResponse;
import tour.base.utils.UserThreadLocal;
import tour.entity.*;
import tour.mapper.IndentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tour.po.IndentPO.*;
import tour.service.*;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class IndentServiceImpl extends ServiceImpl<IndentMapper, Indent> implements IndentService {
    @Autowired
    ScenicService scenicService;
    @Autowired
    HomestayService homestayService;
    @Autowired
    TaxiService taxiService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;
    @Autowired
    UserThreadLocal userThreadLocal;

    @Override
    public ApiResponse getAllIndent() {
        List<Indent> scenicIndentList = list(new LambdaQueryWrapper<Indent>().eq(Indent::getType, "scenic").eq(Indent::getUserId, userThreadLocal.get()).eq(Indent::getStatus, 0));
        List<Indent> homestayIndentList = list(new LambdaQueryWrapper<Indent>().eq(Indent::getType, "homestay").eq(Indent::getUserId, userThreadLocal.get()).eq(Indent::getStatus, 0));
        List<Indent> goodsIndentList = list(new LambdaQueryWrapper<Indent>().eq(Indent::getType, "goods").eq(Indent::getUserId, userThreadLocal.get()).eq(Indent::getStatus, 0));
        List<Indent> taxiIndentList = list(new LambdaQueryWrapper<Indent>().eq(Indent::getType, "taxi").eq(Indent::getUserId, userThreadLocal.get()).eq(Indent::getStatus, 0));
        ArrayList<List> indentList = new ArrayList<>();
        for (Indent indent : scenicIndentList){
            Scenic scenic = scenicService.getScenic(indent.getName());
            indent.setUrl(scenic.getUrl());
        }
        for (Indent indent : homestayIndentList){
            Homestay homestay = homestayService.getHomestay(indent.getName());
            indent.setUrl(homestay.getUrl());
        }
        for (Indent indent : goodsIndentList){
            Goods goods = goodsService.getGoods(indent.getName());
            indent.setUrl(goods.getUrl());
        }
        for (Indent indent : taxiIndentList){
            Taxi taxi = taxiService.getTaxiByName(indent.getName());
            indent.setCarId(taxi.getId());
            indent.setPath(taxi.getPath());
        }
        indentList.add(scenicIndentList);
        indentList.add(homestayIndentList);
        indentList.add(goodsIndentList);
        indentList.add(taxiIndentList);
        return new ApiResponse(indentList);
    }

    @Override
    public Indent getIndentById(long id) {
        Indent indent = getOne(new LambdaQueryWrapper<Indent>().eq(Indent::getId, id));
        return indent;
    }

    @Override
    public ApiResponse deleteIndent(IndentDeletePO indentDeletePO) {
        Indent indent = getIndentById(indentDeletePO.getId());
        if (indent == null){
            return new ApiResponse("410","删除失败");
        }
        if ("taxi".equals(indent.getType())){
            Taxi taxi = taxiService.getTaxiByName(indent.getName());
            Integer count = indent.getCount();
            taxiService.updateTaxiDecreaseUsed(taxi.getId(),count);
        }
        boolean b = removeById(indentDeletePO.getId());
        if (b){
            return new ApiResponse();
        }
        return new ApiResponse("410","删除失败");

    }

    @Override
    public ApiResponse addScenicIndent(AddScenicPO addScenicPO) {
        Scenic scenic = scenicService.getScenic(addScenicPO.getScenicName());
        Integer unitPrice = scenic.getPrice();
        int price = unitPrice * addScenicPO.getCount();
        String detail = "游客姓名:" + addScenicPO.getTouristName() + ";门票日期:" + addScenicPO.getTime() + ";游客电话:" + addScenicPO.getPhone() + ";";
        save(new Indent(addScenicPO.getScenicName(), addScenicPO.getCount(), unitPrice, price, detail, "scenic",userThreadLocal.get(), 0));
        return new ApiResponse("200", "景点预定成功");
    }

    @Override
    public ApiResponse addHomestayIndent(AddHomestayPO addHomestayPO) {
        Homestay homestay = homestayService.getHomestay(addHomestayPO.getHomestayName());
        int unitPrice = homestay.getPrice();
        int i = Integer.parseInt(addHomestayPO.getArrivalTime().substring(8));
        int i1 = Integer.parseInt(addHomestayPO.getDepartureTime().substring(8));
        int price = unitPrice * (i1 - i);
        String detail = "住客姓名:" + addHomestayPO.getTouristName() + ";入住时间:" + addHomestayPO.getArrivalTime() + ";退房时间:" + addHomestayPO.getDepartureTime() + ";电话号码:" + addHomestayPO.getPhone() + ";";
        save(new Indent(addHomestayPO.getHomestayName(), addHomestayPO.getCount(), unitPrice, price, detail, "homestay",userThreadLocal.get(), 0));
        return new ApiResponse("200", "民宿预定成功");
    }

    @Override
    public ApiResponse addGoodsIndent(AddGoodsPO addGoodsPO) {
        Goods goods = goodsService.getGoods(addGoodsPO.getGoodsName());
        int unitPrice = goods.getPrice();
        int count = addGoodsPO.getCount();
        int price = count * unitPrice;
        String detail = "收货人:" + addGoodsPO.getConsignee() + ";收货地址:" + addGoodsPO.getShippingAddress() + ";联系方式:" + addGoodsPO.getPhone() + ";";
        save(new Indent(addGoodsPO.getGoodsName(), count, unitPrice, price, detail, "goods",userThreadLocal.get(), 0));
        return new ApiResponse("200", "下单成功");
    }

    @Override
    public ApiResponse addTaxiIndent(AddTaxiPO addTaxiPO) {
        Taxi taxi = taxiService.getTaxi(addTaxiPO.getId());
        int unitPrice = taxi.getPrice();
        int count = addTaxiPO.getCount();
        if (count > taxi.getMaxNum() - taxi.getUsed()) {
            return new ApiResponse("300","人数超过上限");
        }
        int price = unitPrice * count;
        taxiService.updateTaxiIncreaseUsed(addTaxiPO.getId(), addTaxiPO.getCount());
        String detail = "乘客姓名:" + addTaxiPO.getPassageName() + ";联系电话:" + addTaxiPO.getPhone() + ";";
        save(new Indent(taxi.getId(),taxi.getCarType(), count, unitPrice, price, detail, "taxi",userThreadLocal.get(), 0));
        return new ApiResponse("200", "拼车成功");
    }

    @Override
    public ApiResponse updateHomestayIndent(UpdateHomestayPO updateHomestayPO) {
        Indent indent = getIndentById(updateHomestayPO.getIndentId());
        String detail = indent.getDetail();
        int i1 = detail.indexOf(":");
        int j1 = detail.indexOf(";");
        String replace = detail.replace(detail.substring(i1 + 1, j1), updateHomestayPO.getTouristName());
        log.info(replace);
        int i2 = detail.indexOf(":", i1 + 1);
        int j2 = detail.indexOf(";", j1 + 1);
        int i3 = detail.indexOf(":", i2 + 1);
        int j3 = detail.indexOf(";", j2 + 1);
        int i4 = detail.indexOf(":", i3 + 1);
        int j4 = detail.indexOf(";", j3 + 1);
        String replace1 = replace.replace(detail.substring(i4 + 1, j4), updateHomestayPO.getPhone());
        update(new LambdaUpdateWrapper<Indent>().eq(Indent::getId, updateHomestayPO.getIndentId()).set(Indent::getDetail, replace1));
        return new ApiResponse("200", "修改成功");
    }

    @Override
    public ApiResponse updateScenicIndent(UpdateScenicPO updateScenicPO) {
        Indent indent = getIndentById(updateScenicPO.getIndentId());
        String detail = indent.getDetail();
        int i1 = detail.indexOf(":");
        int j1 = detail.indexOf(";");
        String replace = detail.replace(detail.substring(i1 + 1, j1), updateScenicPO.getTouristName());
        log.info(replace);
        int i2 = detail.indexOf(":", i1 + 1);
        int j2 = detail.indexOf(";", j1 + 1);
        String replace1 = replace.replace(detail.substring(i2 + 1, j2), updateScenicPO.getTime());
        log.info(replace1);
        int i3 = detail.indexOf(":", i2 + 1);
        int j3 = detail.indexOf(";", j2 + 1);
        String replace2 = replace1.replace(detail.substring(i3 + 1, j3), updateScenicPO.getPhone());
        log.info(replace2);
        update(new LambdaUpdateWrapper<Indent>().eq(Indent::getId, updateScenicPO.getIndentId()).set(Indent::getDetail, replace2));
        return new ApiResponse("200", "修改成功");
    }


    @Override
    public ApiResponse updateGoodsIndent(UpdateGoodsPO updateGoodsPO) {
        Indent indent = getIndentById(updateGoodsPO.getIndentId());
        String detail = indent.getDetail();
        int i1 = detail.indexOf(":");
        int j1 = detail.indexOf(";");
        String replace = detail.replace(detail.substring(i1 + 1, j1), updateGoodsPO.getConsignee());
        log.info(replace);
        int i2 = detail.indexOf(":", i1 + 1);
        int j2 = detail.indexOf(";", j1 + 1);
        String replace1 = replace.replace(detail.substring(i2 + 1, j2), updateGoodsPO.getShippingAddress());
        log.info(replace1);
        int i3 = detail.indexOf(":", i2 + 1);
        int j3 = detail.indexOf(";", j2 + 1);
        String replace2 = replace1.replace(detail.substring(i3 + 1, j3), updateGoodsPO.getPhone());
        log.info(replace2);
        update(new LambdaUpdateWrapper<Indent>().eq(Indent::getId, updateGoodsPO.getIndentId()).set(Indent::getDetail, replace2));
        return new ApiResponse("200", "修改成功");
    }

    @Override
    public ApiResponse updateTaxiIndent(UpdateTaxiPO updateTaxiPO) {
        Indent indent = getIndentById(updateTaxiPO.getIndentId());
        String detail = indent.getDetail();
        int i1 = detail.indexOf(":");
        int j1 = detail.indexOf(";");
        String replace = detail.replace(detail.substring(i1 + 1, j1), updateTaxiPO.getPassageName());
        log.info(replace);
        int i2 = detail.indexOf(":", i1 + 1);
        int j2 = detail.indexOf(";", j1 + 1);
        String replace1 = replace.replace(detail.substring(i2 + 1, j2), updateTaxiPO.getPhone());
        update(new LambdaUpdateWrapper<Indent>().eq(Indent::getId, updateTaxiPO.getIndentId()).set(Indent::getDetail, replace1));
        return new ApiResponse("200", "修改成功");
    }

    @Override
    public ApiResponse settlement(long indentId) {
        Indent indent = getOne(new LambdaQueryWrapper<Indent>().eq(Indent::getId, indentId));
        userService.settlement(indent.getPrice());
        update(new LambdaUpdateWrapper<Indent>().eq(Indent::getId, indentId).set(Indent::getStatus, 1));
        return new ApiResponse("200", "结算成功");
    }

}
