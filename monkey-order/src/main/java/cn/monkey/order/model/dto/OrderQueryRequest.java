package cn.monkey.order.model.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class OrderQueryRequest implements Serializable {
    private String uid;
    private String queryKey;
    private Integer state;

    public static OrderQueryRequest of(String uid,
                                       String queryKey,
                                       Integer state){
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.uid = uid;
        orderQueryRequest.queryKey = queryKey;
        orderQueryRequest.state = state;
        return orderQueryRequest;
    }
}
