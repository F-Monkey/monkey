package cn.monkeyframework.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Data
public class ExpandRedisProperties {

    private AppKeyProperties appKey = new AppKeyProperties();

    @Data
    public static class AppKeyProperties {
        static final String DEFAULT_KEY = "user_app";
        static final String DEFAULT_SUFFIX = "app_";
        static final int DEFAULT_LENGTH = 10;
        static final char DEFAULT_PADDING = '0';
        private String key = DEFAULT_KEY;
        private String suffix = DEFAULT_SUFFIX;
        private int length = DEFAULT_LENGTH;
        private char padding = DEFAULT_PADDING;
    }
}
