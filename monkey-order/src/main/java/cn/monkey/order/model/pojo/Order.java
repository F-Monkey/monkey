package cn.monkey.order.model.pojo;

import cn.monkey.order.model.OrderState;
import cn.monkeyframework.common.data.jpa.Key;
import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Order.TABLE_NAME)
@Entity
public class Order extends BaseEntity<String> {
    public static final String TABLE_NAME = "`order`";

    @Id
    @Key(key = "order_id", prefix = "O_")
    protected String id;

    private String uid;

    private String remarks;

    private Integer state = OrderState.NEW.getCode();
}
