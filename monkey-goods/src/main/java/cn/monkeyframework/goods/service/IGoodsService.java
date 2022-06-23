package cn.monkeyframework.goods.service;

import cn.monkeyframework.commons.data.dto.GoodsDto;
import cn.monkeyframework.commons.data.dto.GoodsQueryRequest;
import cn.monkeyframework.commons.data.vo.GoodsVo;
import cn.monkeyframework.commons.data.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGoodsService {

    String GOODS_TYPE_CODE = "goodsType";

    String CURRENCY_CODE = "currency";

    /**
     * @param id 可以是单个id，或者多个id以','分割
     * @return GoodsVo or List<GoodsVo>
     */
    Result<?> select(String id);

    Result<Page<GoodsVo>> select(GoodsQueryRequest queryRequest, Pageable pageable);

    Result<GoodsVo> add(GoodsDto goods);

    Result<GoodsVo> update(String id, GoodsDto goods);

    Result<Void> delete(String id);
}
