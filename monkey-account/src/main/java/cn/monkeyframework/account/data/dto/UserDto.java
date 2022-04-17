package cn.monkeyframework.account.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private String nickName;
    private String password;
    private String phone;
    private String email;
    private Integer status;
}
