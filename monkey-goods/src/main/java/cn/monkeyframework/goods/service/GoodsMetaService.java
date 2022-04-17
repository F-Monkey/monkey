package cn.monkeyframework.goods.service;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.goods.data.vo.GoodsMetaVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsMetaService implements IGoodsMetaService{
    @Override
    public Result<List<GoodsMetaVo>> get(String type) {
        return null;
    }
}
