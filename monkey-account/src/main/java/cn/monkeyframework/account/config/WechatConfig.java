package cn.monkeyframework.account.config;

import cn.monkeyframework.account.handler.http.SimpleWechatRequestHandler;
import cn.monkeyframework.account.handler.http.WechatRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatConfig {
    @Bean
    WechatProperties wechatProperties() {
        return new WechatProperties();
    }

    @Bean
    WechatRequestHandler wechatRequestHandler(WechatProperties wechatProperties) {
        return new SimpleWechatRequestHandler(wechatProperties);
    }
}
