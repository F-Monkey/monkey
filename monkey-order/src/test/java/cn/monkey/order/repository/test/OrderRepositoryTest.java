package cn.monkey.order.repository.test;

import cn.monkey.order.OrderApplication;
import cn.monkeyframework.commons.data.dto.OrderQueryRequest;
import cn.monkeyframework.commons.data.pojo.Order;
import cn.monkey.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest(classes = OrderApplication.class)
@Slf4j
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void test01() {
        String uid = "624b102e3cb0d372acc0af2e";
        String goodsName = null;
        Page<Order> byGoodsName = this.orderRepository.findByGoodsName(OrderQueryRequest.of(uid,goodsName,null), Pageable.ofSize(4));
        log.info("orders: {}", byGoodsName);
    }
}
