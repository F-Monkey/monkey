package cn.monkey.order.service;

import cn.monkeyframework.commons.data.vo.Result;

public interface IOrderSupportService {
    Result<String> newOrderId(String uid);
}
