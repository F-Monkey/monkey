package cn.monkeyframework.goods.controller;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.goods.data.vo.GoodsMetaVo;
import cn.monkeyframework.goods.service.IGoodsMetaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods_meta")
public class GoodsMetaDataController {

    private final IGoodsMetaService goodsMetaService;

    public GoodsMetaDataController(IGoodsMetaService goodsMetaService) {
        this.goodsMetaService = goodsMetaService;
    }

    @GetMapping
    Result<List<GoodsMetaVo>> get(@RequestParam(name = "type", required = false) String type) {
        return this.goodsMetaService.get(type);
    }
}
