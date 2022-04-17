package cn.monkeyframework.account.data.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddress implements Serializable {
    private String id;
    private String uid;
    private String addressId;
}
