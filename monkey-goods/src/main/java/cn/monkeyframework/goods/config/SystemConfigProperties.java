package cn.monkeyframework.goods.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "system")
public class SystemConfigProperties {
    private List<String> codeTypes;
}
