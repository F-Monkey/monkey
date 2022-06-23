package cn.monkeyframework.commons.data.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class WechatLoginRequest implements Serializable {
    private WechatLoginRequest(){}
    private String code;
    private String userAppKey;

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder {
        private final WechatLoginRequest loginRequest;

        Builder() {
            this.loginRequest = new WechatLoginRequest();
        }

        public Builder code(String code){
            this.loginRequest.code = code;
            return this;
        }

        public Builder userAppkey(String appKey){
            this.loginRequest.userAppKey = appKey;
            return this;
        }

        public WechatLoginRequest build(){
            return this.loginRequest;
        }
    }
}
