package cn.monkey.order.service;

import cn.monkey.order.model.dto.OrderDto;
import cn.monkey.order.model.dto.OrderQueryRequest;
import cn.monkey.order.model.vo.OrderVo;
import cn.monkeyframework.common.data.vo.Result;
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
