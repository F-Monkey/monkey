package cn.monkey.order.service;

import cn.monkey.order.handler.OrderIdGenerator;
import cn.monkey.order.handler.UserOrderLock;
import cn.monkeyframework.common.data.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderSupportService implements IOrderSupportService {

    private static final Logger log = LoggerFactory.getLogger(OrderSupportService.class);

    private final OrderIdGenerator orderIdGenerator;

    private final UserOrderLock userOrderLock;

    public OrderSupportService(OrderIdGenerator orderIdGenerator,
                               UserOrderLock userOrderLock){
        this.orderIdGenerator = orderIdGenerator;
        this.userOrderLock = userOrderLock;
    }

    @Override
    public Result<String> newOrderId(String uid) {
        String s = this.orderIdGenerator.newId();
        boolean b = this.userOrderLock.tryLock(s, uid);
        if(!b){
            log.error("can not lock orderId: {} for uid: {}",s, uid);
        }
        return b? Result.ok(s): Result.fail("create order fail");
    }
}
