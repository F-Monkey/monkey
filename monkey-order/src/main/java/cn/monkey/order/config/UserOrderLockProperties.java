package cn.monkey.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "monkey.order.lock")
public class UserOrderLockProperties {
    static final long DEFAULT_EXPIRE_MS = 10000L;
    private long expireMs = DEFAULT_EXPIRE_MS;
}
