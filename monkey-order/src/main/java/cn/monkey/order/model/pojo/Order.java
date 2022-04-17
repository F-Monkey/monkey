package cn.monkey.order.model.pojo;

import cn.monkey.order.model.OrderState;
import cn.monkeyframework.common.data.jpa.Key;
import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "`order`")
@Entity
public class Order extends BaseEntity {
    @Id
    @Key(key = "order_id", prefix = "O_")
    protected String id;

    private String uid;

    private String remarks;

    private Integer state = OrderState.NEW.getCode();
}
