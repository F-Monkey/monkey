package cn.monkeyframework.commons.data;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum OrderState implements Serializable {

    NEW(0),
    CONFIRMED(1),
    PAYED(2),
    CANCELED(3),
    RECEIVED(4);

    private final int code;

    OrderState(int code) {
        this.code = code;
    }

    public static OrderState of(int code) {
        for (OrderState orderStatus : values()) {
            if (orderStatus.code == code) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("invalid code:" + code);
    }
}
