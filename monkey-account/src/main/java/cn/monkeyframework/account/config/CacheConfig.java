package cn.monkeyframework.account.config;

import cn.monkeyframework.account.cache.InMemoryWechatCache;
import cn.monkeyframework.account.cache.WechatCache;
import cn.monkeyframework.account.handler.WechatRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    WechatCache wechatCache(WechatRequestHandler wechatRequestHandler) {
        return new InMemoryWechatCache(wechatRequestHandler);
    }
}
