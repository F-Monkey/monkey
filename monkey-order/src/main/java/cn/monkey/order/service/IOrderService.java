package cn.monkey.order.service;

import cn.monkeyframework.commons.data.dto.OrderDto;
import cn.monkeyframework.commons.data.dto.OrderQueryRequest;
import cn.monkeyframework.commons.data.vo.OrderVo;
import cn.monkeyframework.commons.data.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {

    Result<OrderVo> add(OrderDto orderDto);

    Result<OrderVo> update(String orderId, OrderDto orderDto);

    Result<Page<OrderVo>> select(OrderQueryRequest queryRequest, Pageable pageable);

    Result<OrderVo> select(String id);

    Result<Void> delete(String id);

    Result<List<String>> selectDistinctGoodsName(String uid, String goodsName);
}
