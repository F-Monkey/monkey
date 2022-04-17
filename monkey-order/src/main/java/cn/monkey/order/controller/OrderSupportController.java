package cn.monkey.order.controller;

import cn.monkey.order.service.IOrderSupportService;
import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.common.spring.mvc.Uid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order_support")
public class OrderSupportController {

    private final IOrderSupportService orderSupportService;
    public OrderSupportController(IOrderSupportService orderSupportService){
        this.orderSupportService = orderSupportService;
    }

    @GetMapping(params = {"action=new_order_id"})
    Result<String> newOrderId(@Uid String uid){
        return orderSupportService.newOrderId(uid);
    }
}
