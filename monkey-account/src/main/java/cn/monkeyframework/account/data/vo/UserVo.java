package cn.monkeyframework.account.data.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserVo implements Serializable {
    private String id;
    private String nickName;
    private String password;
    private String phone;
    private String email;
    private Integer status;
    private String appKey;
    private Long createTs;
    private Long updateTs;
}
