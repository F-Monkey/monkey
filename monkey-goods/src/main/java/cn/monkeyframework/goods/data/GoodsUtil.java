package cn.monkeyframework.goods.data;

import cn.monkeyframework.goods.data.dto.GoodsDto;
import cn.monkeyframework.goods.data.dto.GoodsQueryRequest;
import cn.monkeyframework.goods.data.pojo.Goods;
import cn.monkeyframework.goods.data.vo.GoodsVo;
import com.google.common.base.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

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
