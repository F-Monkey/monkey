package cn.monkeyframework.commons.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private transient String uid;
    private String id;
    private List<OrderDetailDto> orderDetail;
    private String remarks;
}
