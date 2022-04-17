package cn.monkeyframework.goods.controller;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.goods.data.dto.GoodsDto;
import cn.monkeyframework.goods.data.dto.GoodsQueryRequest;
import cn.monkeyframework.goods.data.vo.GoodsVo;
import cn.monkeyframework.goods.service.IGoodsService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final IGoodsService goodsService;

    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @GetMapping({"{id}", ""})
    Result<?> get(@PathVariable(name = "id", required = false) String id,
                  @RequestParam(name = "name", required = false) String name,
                  @RequestParam(name = "type", required = false) String type,
                  @RequestParam(name = "action", required = false) String action,
                  @PageableDefault Pageable pageable) {
        if (!Strings.isNullOrEmpty(id)) {
            return this.goodsService.select(id);
        }
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setName(name);
        goodsQueryRequest.setType(type);
        goodsQueryRequest.setAction(action);
        return this.goodsService.select(goodsQueryRequest, pageable);
    }

    @PostMapping
    Result<GoodsVo> add(@RequestBody @Validated GoodsDto goodsDto) {
        return this.goodsService.add(goodsDto);
    }

    @PutMapping("/{id}")
    Result<GoodsVo> update(@PathVariable("id") String id,
                           @RequestBody GoodsDto goodsDto) {
        return this.goodsService.update(id, goodsDto);
    }

    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") String id) {
        return this.goodsService.delete(id);
    }
}
