package cn.monkeyframework.goods.service;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.goods.data.vo.GoodsMetaVo;
import org.springframework.lang.Nullable;

import java.util.List;

public interface  IGoodsMetaService {
    Result<List<GoodsMetaVo>> get(@Nullable String type);
}
