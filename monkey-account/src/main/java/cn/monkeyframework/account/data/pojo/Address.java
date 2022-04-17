package cn.monkeyframework.account.data.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private String id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String name;
    private String houseNumber;
    private String userName;
    private String phone;
}
