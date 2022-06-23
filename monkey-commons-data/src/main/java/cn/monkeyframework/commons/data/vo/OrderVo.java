package cn.monkeyframework.commons.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderVo implements Serializable {
    private String id;
    private String uid;
    private List<OrderDetailVo> orderDetail;
    private String remarks;
    private Integer state;
    private Long createTs;
    private Long updateTs;
}
