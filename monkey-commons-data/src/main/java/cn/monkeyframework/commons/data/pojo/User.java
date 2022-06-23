package cn.monkeyframework.commons.data.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity<String> {
    private String nickName;
    private String password;
    private String phone;
    private String email;
    private Integer status;
}