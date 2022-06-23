package cn.monkeyframework.commons.data.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressVo implements Serializable {
    private String id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String name;
    private String houseNumber;
    private String userName;
    private String phone;
}
