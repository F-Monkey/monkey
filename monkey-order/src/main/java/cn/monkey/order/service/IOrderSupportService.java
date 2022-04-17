package cn.monkey.order.service;

import cn.monkeyframework.common.data.vo.Result;

public interface IOrderSupportService {
    Result<String> newOrderId(String uid);
}
