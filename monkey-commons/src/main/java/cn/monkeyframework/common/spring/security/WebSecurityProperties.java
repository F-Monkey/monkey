package cn.monkeyframework.common.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security")
@Data
public class WebSecurityProperties {
    static final String[] DEFAULT_CORS = {"*"};

    private String[] cors;
}
