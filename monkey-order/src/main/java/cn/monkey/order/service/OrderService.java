package cn.monkey.order.service;

import cn.monkey.order.handler.UserOrderLock;
import cn.monkey.order.model.OrderState;
import cn.monkey.order.model.OrderUtil;
import cn.monkey.order.model.dto.OrderDetailDto;
import cn.monkey.order.model.dto.OrderDto;
import cn.monkey.order.model.dto.OrderQueryRequest;
import cn.monkey.order.model.pojo.Order;
import cn.monkey.order.model.pojo.OrderDetail;
import cn.monkey.order.model.vo.OrderDetailVo;
import cn.monkey.order.model.vo.OrderVo;
import cn.monkey.order.repository.OrderDetailRepository;
import cn.monkey.order.repository.OrderRepository;
import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.common.util.PageUtil;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserOrderLock userOrderLock;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        UserOrderLock userOrderLock) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.userOrderLock = userOrderLock;
    }

    @Override
    @Transactional
    public Result<OrderVo> add(OrderDto orderDto) {
        List<OrderDetailDto> orderDetail = orderDto.getOrderDetail();
        if (CollectionUtils.isEmpty(orderDetail)) {
            return Result.fail("can not find any order detail");
        }
        String orderId = orderDto.getId();
        String uid = orderDto.getUid();

        if(Strings.isNullOrEmpty(orderId)){
            return Result.fail("order.id is required");
        }
        int unlock = this.userOrderLock.unlock(orderId, uid);
        switch (unlock){
            case UserOrderLock.ORDER_NOT_EXISTS:
                return Result.fail("orderId: "+ orderId +" is not exists");
            case UserOrderLock.BAD_UID:
                return Result.fail("orderId: "+ orderId+" is not belongs to uid: "+ uid);
            default:
                break;
        }
        long l = System.currentTimeMillis();
        Order copy = OrderUtil.copy(orderDto);
        copy.setState(OrderState.NEW.getCode());
        copy.setCreateTs(l);
        copy.setUpdateTs(l);
        // BUILD order details
        List<OrderDetail> orderDetailList = this.buildOrderDetailList(orderId, orderDetail);
        Order order = this.orderRepository.saveAndFlush(copy);
        List<OrderDetail> saveAll = this.orderDetailRepository.saveAllAndFlush(orderDetailList);
        List<OrderDetailVo> detailVoList = saveAll.stream().map(OrderUtil::copy).collect(Collectors.toList());
        OrderVo orderVo = OrderUtil.copy(order);
        orderVo.setOrderDetail(detailVoList);
        return Result.ok(orderVo);
    }

    protected List<OrderDetail> buildOrderDetailList(String orderId, List<OrderDetailDto> orderDetailDtoList){
        int index = 0;
        long l = System.currentTimeMillis();
        List<OrderDetail> orderDetailList = new ArrayList<>(orderDetailDtoList.size());
        for(OrderDetailDto orderDetailDto: orderDetailDtoList){
            OrderDetail copy = OrderUtil.copy(orderId, orderDetailDto);
            copy.setCreateTs(l);
            copy.setUpdateTs(l);
            copy.setId(orderId +"_"+ Strings.padStart(String.valueOf(index++), 2, '0'));
            orderDetailList.add(copy);
        }
        return orderDetailList;
    }


    @Override
    public Result<OrderVo> update(String orderId, OrderDto orderDto) {
        Order copy = OrderUtil.copy(orderId, orderDto);
        Order save = this.orderRepository.saveAndFlush(copy);
        return Result.ok(OrderUtil.copy(save));
    }

    @Override
    public Result<Page<OrderVo>> select(OrderQueryRequest queryRequest, Pageable pageable) {
        Integer state = queryRequest.getState();
        if(state != null){
            try {
                OrderState.of(state);
            }catch (IllegalArgumentException e){
                return Result.fail("invalid state: "+ state);
            }
        }
        Page<Order> page = this.orderRepository.findByGoodsName(queryRequest, pageable);
        if (page.isEmpty()) {
            return Result.ok(Page.empty(pageable));
        }
        List<Order> content = page.getContent();
        List<String> collect = content.stream().map(Order::getId).collect(Collectors.toList());
        List<OrderDetail> orderDetailList = this.orderDetailRepository.findAllByOrderId(collect);
        Map<String, List<OrderDetail>> orderDetailMap = orderDetailList.stream().collect(Collectors.groupingBy(OrderDetail::getOrderId));
        Page<OrderVo> orderVoPage = PageUtil.copy(page, (order -> {
            OrderVo copy = OrderUtil.copy(order);
            List<OrderDetail> orderDetails = orderDetailMap.get(copy.getId());
            if (CollectionUtils.isEmpty(orderDetails)) {
                return copy;
            }
            List<OrderDetailVo> detailVoList = orderDetails.stream().map(OrderUtil::copy).collect(Collectors.toList());
            copy.setOrderDetail(detailVoList);
            return copy;
        }));
        return Result.ok(orderVoPage);
    }

    @Override
    public Result<OrderVo> select(String id) {
        Optional<Order> optional = this.orderRepository.findById(id);
        return optional.map(order -> {
            List<OrderDetail> orderDetails = this.orderDetailRepository.findAllByOrderId(order.getId());
            OrderVo copy = OrderUtil.copy(order);
            if (!CollectionUtils.isEmpty(orderDetails)) {
                List<OrderDetailVo> collect = orderDetails.stream().map(OrderUtil::copy).collect(Collectors.toList());
                copy.setOrderDetail(collect);
            }
            return Result.ok(copy);
        }).orElse(Result.fail("can not find order by id: " + id));
    }

    @Override
    public Result<Void> delete(String id) {
        Order order = new Order();
        order.setId(id);
        order.setState(OrderState.CANCELED.getCode());
        this.orderRepository.save(order);
        return Result.ok();
    }

    @Override
    public Result<List<String>> selectDistinctGoodsName(String uid, String goodsName) {
        List<String> distinctGoodsName = this.orderDetailRepository.findDistinctGoodsName(uid, goodsName);
        return CollectionUtils.isEmpty(distinctGoodsName)? Result.ok(): Result.ok(distinctGoodsName);
    }
}
