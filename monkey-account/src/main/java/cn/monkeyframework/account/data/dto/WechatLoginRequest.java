package cn.monkeyframework.account.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WechatLoginRequest implements Serializable {
    private String code;
    private String userAppKey;
}
