package cn.monkeyframework.goods.service;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.common.util.PageUtil;
import cn.monkeyframework.goods.data.GoodsQueryRequestUtil;
import cn.monkeyframework.goods.data.GoodsUtil;
import cn.monkeyframework.goods.data.SystemCodeUtl;
import cn.monkeyframework.goods.data.dto.GoodsDto;
import cn.monkeyframework.goods.data.dto.GoodsQueryRequest;
import cn.monkeyframework.goods.data.pojo.Goods;
import cn.monkeyframework.goods.data.pojo.HotGoodsInfo;
import cn.monkeyframework.goods.data.pojo.SystemCode;
import cn.monkeyframework.goods.data.vo.GoodsVo;
import cn.monkeyframework.goods.repository.GoodsRepository;
import cn.monkeyframework.goods.repository.HotGoodsRepository;
import cn.monkeyframework.system.context.SystemCodeContext;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService implements IGoodsService {

    private final GoodsRepository goodsRepository;

    private final HotGoodsRepository hotGoodsRepository;

    private final SystemCodeContext systemCodeContext;

    public GoodsService(GoodsRepository goodsRepository,
                        HotGoodsRepository hotGoodsRepository,
                        SystemCodeContext systemCodeContext) {
        this.goodsRepository = goodsRepository;
        this.hotGoodsRepository = hotGoodsRepository;
        this.systemCodeContext = systemCodeContext;
    }

    @Override
    public Result<?> select(String id) {
        String[] split = id.split(",");
        Map<String, SystemCode> goodsTypeMap = this.systemCodeContext.get(IGoodsService.GOODS_TYPE_CODE);
        Map<String, SystemCode> currencyMap = this.systemCodeContext.get(IGoodsService.CURRENCY_CODE);
        if (split.length > 1) {
            Iterable<Goods> all = this.goodsRepository.findAllById(Arrays.asList(split));
            List<Goods> copy = PageUtil.copy(all);
            return Result.ok(copy.stream().map(goods -> this.buildFullGoodsVo(goodsTypeMap,currencyMap,goods)).collect(Collectors.toList()));
        }
        return this.goodsRepository.findById(id)
                .map(goods -> Result.ok(this.buildFullGoodsVo(goodsTypeMap,currencyMap,goods)))
                .orElseGet(() -> Result.fail("id:" + id + " is not exits"));
    }

    protected GoodsVo buildFullGoodsVo(Map<String, SystemCode> goodsTypeMap,
                                       Map<String, SystemCode> currencyMap,
                                       Goods goods){
        GoodsVo goodsVo = GoodsUtil.copy(goods);
        goodsVo.setCurrency(SystemCodeUtl.copy(currencyMap.get(goods.getCurrency())));
        goodsVo.setType(SystemCodeUtl.copy(goodsTypeMap.get(goods.getType())));
        return goodsVo;
    }


    @Override
    public Result<Page<GoodsVo>> select(GoodsQueryRequest queryRequest, Pageable pageable) {
        String action = queryRequest.getAction();
        if (!Strings.isNullOrEmpty(action)) {
            switch (action) {
                case GoodsQueryRequest.Action.GET_HOT_GOODS:
                    Page<GoodsVo> hotGoods = this.getHotGoods(pageable);
                    return Result.ok(hotGoods);
                default:
                    return Result.ok(Page.empty());
            }
        }
        Map<String, SystemCode> goodsTypeMap = this.systemCodeContext.get(IGoodsService.GOODS_TYPE_CODE);
        Map<String, SystemCode> currencyMap = this.systemCodeContext.get(IGoodsService.CURRENCY_CODE);
        Page<Goods> all = this.goodsRepository.findAll(GoodsQueryRequestUtil.build(queryRequest), pageable);
        Page<GoodsVo> copy = PageUtil.copy(all, goods -> this.buildFullGoodsVo(goodsTypeMap,currencyMap,goods));
        return Result.ok(copy);
    }

    protected Page<GoodsVo> getHotGoods(Pageable pageable) {
        Page<HotGoodsInfo> hotGoodsInfos = this.hotGoodsRepository.findValidHotGoods(pageable);
        if (!hotGoodsInfos.hasContent()) {
            return Page.empty();
        }
        Optional<String> reduce = hotGoodsInfos.get().map(HotGoodsInfo::getGoodsId).reduce((id1, id2) -> id1 + "," + id2);
        if (reduce.isEmpty()) {
            return Page.empty();
        }
        Result<?> result = this.select(reduce.get());
        Object data = result.getData();
        if (data instanceof List) {
            @SuppressWarnings("unchecked")
            PageImpl<GoodsVo> goodsVos = new PageImpl<>((List<GoodsVo>) data, pageable, hotGoodsInfos.getTotalElements());
            return goodsVos;
        }
        return new PageImpl<>(Collections.singletonList((GoodsVo) data), pageable, hotGoodsInfos.getTotalElements());
    }

    @Override
    public Result<GoodsVo> add(GoodsDto goods) {
        Goods copy = GoodsUtil.copy(goods);
        Goods save = this.goodsRepository.save(copy);
        return Result.ok(GoodsUtil.copy(save));
    }

    @Override
    public Result<GoodsVo> update(String id, GoodsDto goods) {
        Goods copy = GoodsUtil.copy(id, goods);
        Goods save = this.goodsRepository.save(copy);
        return Result.ok(GoodsUtil.copy(save));
    }

    @Override
    public Result<Void> delete(String id) {
        this.goodsRepository.deleteById(id);
        return Result.ok();
    }
}
