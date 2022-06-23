package cn.monkeyframework.commons.data.pojo;

import cn.monkeyframework.commons.data.KeyValuePair;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = OrderDetail.TABLE_NAME)
@Entity
public class OrderDetail extends BaseEntity<String> {
    public static final String TABLE_NAME = "order_detail";

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
    private KeyValuePair<String, String> currency;
    private String unit;
}