package cn.monkeyframework.goods.service;

import cn.monkeyframework.commons.data.vo.GoodsMetaVo;
import cn.monkeyframework.commons.data.vo.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsMetaService implements IGoodsMetaService{
    @Override
    public Result<List<GoodsMetaVo>> get(String type) {
        return null;
    }
}
