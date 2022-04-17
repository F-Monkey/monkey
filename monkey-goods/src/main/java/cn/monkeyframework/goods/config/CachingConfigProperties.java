package cn.monkeyframework.goods.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cache")
@Data
public class CachingConfigProperties {
    private long ttlTimeMs;
}
