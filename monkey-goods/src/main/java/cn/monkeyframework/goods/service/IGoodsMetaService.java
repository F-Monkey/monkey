package cn.monkeyframework.goods.service;

import cn.monkeyframework.commons.data.vo.GoodsMetaVo;
import cn.monkeyframework.commons.data.vo.Result;
import org.springframework.lang.Nullable;

import java.util.List;

public interface  IGoodsMetaService {
    Result<List<GoodsMetaVo>> get(@Nullable String type);
}
