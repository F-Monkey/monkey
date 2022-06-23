package cn.monkeyframework.commons.data.pojo;

import cn.monkeyframework.commons.data.OrderState;
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
    protected String id;

    private String uid;

    private String remarks;

    private Integer state = OrderState.NEW.getCode();
}
