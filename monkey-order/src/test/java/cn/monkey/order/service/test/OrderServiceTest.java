package cn.monkey.order.service.test;

import cn.monkey.order.OrderApplication;
import cn.monkeyframework.commons.data.dto.OrderDetailDto;
import cn.monkeyframework.commons.data.dto.OrderDto;
import cn.monkeyframework.commons.data.vo.OrderVo;
import cn.monkey.order.service.IOrderService;
import cn.monkeyframework.commons.data.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest(classes = OrderApplication.class)
@Slf4j
public class OrderServiceTest {
    @Autowired
    IOrderService orderService;

    @Test
    public void testCreate() {
        OrderDto orderDto = new OrderDto();
        orderDto.setUid("1111");
        OrderDetailDto orderDetail = new OrderDetailDto();
        orderDetail.setQuantity(1.0);
        orderDetail.setGoodsId("g_1000");
        orderDetail.setGoodsName("good");
        orderDto.setOrderDetail(Collections.singletonList(orderDetail));
        this.orderService.add(orderDto);
    }

    @Test
    public void testQuery(){
        String id = "o_0000000014";
        Result<OrderVo> select = this.orderService.select(id);
        log.info("query result: {}", select);
    }
}
