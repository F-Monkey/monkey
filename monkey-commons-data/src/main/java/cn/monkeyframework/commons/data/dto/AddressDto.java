package cn.monkeyframework.commons.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    private String uid;
    private String address;
    private Double latitude;
    private Double longitude;
    private String name;
    private String houseNumber;
    private String userName;
    private String phone;
}
