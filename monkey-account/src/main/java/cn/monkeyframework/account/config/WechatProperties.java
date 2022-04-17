package cn.monkeyframework.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "monkey.wechat")
@Data
public class WechatProperties {
    private String appId;
    private String secret;
}
