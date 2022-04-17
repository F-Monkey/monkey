package cn.monkey.order.model.pojo;

import cn.monkeyframework.common.data.KeyValuePair;
import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_detail")
@Entity
public class OrderDetail extends BaseEntity {
    @Id
    protected String id;
    private String orderId;
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private String goodsImgSrc;
    private String goodsDesc;
    private Double quantity;
    private Double price;
    private KeyValuePair<String,String> currency;
    private String unit;
}
