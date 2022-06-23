package cn.monkeyframework.commons.data;

import cn.monkeyframework.commons.data.dto.OrderDetailDto;
import cn.monkeyframework.commons.data.dto.OrderDto;
import cn.monkeyframework.commons.data.pojo.Order;
import cn.monkeyframework.commons.data.pojo.OrderDetail;
import cn.monkeyframework.commons.data.vo.OrderDetailVo;
import cn.monkeyframework.commons.data.vo.OrderVo;

public abstract class OrderUtil {
    public static OrderVo copy(Order order) {
        OrderVo orderVo = new OrderVo();
        orderVo.setId(order.getId());
        orderVo.setState(order.getState());
        orderVo.setCreateTs(order.getCreateTs());
        orderVo.setUpdateTs(order.getUpdateTs());
        orderVo.setUid(order.getUid());
        orderVo.setRemarks(order.getRemarks());
        return orderVo;
    }

    public static Order copy(OrderDto orderDto) {
        Order order = new Order();
        order.setUid(orderDto.getUid());
        order.setId(orderDto.getId());
        order.setRemarks(orderDto.getRemarks());
        return order;
    }

    public static Order copy(String id, OrderDto orderDto) {
        Order copy = copy(orderDto);
        copy.setId(id);
        return copy;
    }

    public static OrderDetailVo copy(OrderDetail orderDetail) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setId(orderDetail.getId());
        orderDetailVo.setQuantity(orderDetail.getQuantity());
        orderDetailVo.setGoodsId(orderDetail.getGoodsId());
        orderDetailVo.setPrice(orderDetail.getPrice());
        orderDetailVo.setGoodsName(orderDetail.getGoodsName());
        orderDetailVo.setUnit(orderDetail.getUnit());
        orderDetailVo.setGoodsImgSrc(orderDetail.getGoodsImgSrc());
        orderDetailVo.setGoodsType(orderDetail.getGoodsType());
        orderDetailVo.setGoodsDesc(orderDetail.getGoodsDesc());
        orderDetailVo.setCurrency(orderDetail.getCurrency());
        return orderDetailVo;
    }

    public static OrderDetail copy(String orderId, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setGoodsId(orderDetailDto.getGoodsId());
        orderDetail.setGoodsName(orderDetailDto.getGoodsName());
        orderDetail.setCurrency(orderDetailDto.getCurrency());
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setUnit(orderDetailDto.getUnit());
        orderDetail.setGoodsDesc(orderDetailDto.getGoodsDesc());
        orderDetail.setGoodsType(orderDetailDto.getGoodsType());
        orderDetail.setGoodsImgSrc(orderDetailDto.getGoodsImgSrc());
        return orderDetail;
    }
}
