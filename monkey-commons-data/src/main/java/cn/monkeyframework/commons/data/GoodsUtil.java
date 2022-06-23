package cn.monkeyframework.commons.data;

import cn.monkeyframework.commons.data.dto.GoodsDto;
import cn.monkeyframework.commons.data.pojo.Goods;
import cn.monkeyframework.commons.data.vo.GoodsVo;

public abstract class GoodsUtil {

    public static GoodsVo copy(Goods goods) {
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setId(goods.getId());
        goodsVo.setName(goods.getName());
        goodsVo.setPrice(goods.getPrice());
        goodsVo.setImgSrc(goods.getImgSrc());
        goodsVo.setUnit(goods.getUnit());
        goodsVo.setDesc(goods.getDesc());
        return goodsVo;
    }

    public static Goods copy(GoodsDto goodsDto) {
        Goods goods = new Goods();
        goods.setName(goodsDto.getName());
        goods.setPrice(goodsDto.getPrice());
        goods.setImgSrc(goodsDto.getImgSrc());
        goods.setType(goodsDto.getType());
        goods.setUnit(goodsDto.getUnit());
        goods.setCurrency(goods.getCurrency());
        goods.setDesc(goodsDto.getDesc());
        return goods;
    }

    public static Goods copy(String id, GoodsDto goodsDto) {
        Goods copy = copy(goodsDto);
        copy.setId(id);
        return copy;
    }
}
