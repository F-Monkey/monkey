package cn.monkeyframework.common.spring.message;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.message")
public class MessageConfigurationProperties {
    static final String DEFAULT_ENCODING = "UTF-8";
    static final String DEFAULT_LANG = "ch";
    static final String DEFAULT_FILE_PATH_PREFIX = "message_";

    private String encoding = DEFAULT_ENCODING;
    private String language = DEFAULT_LANG;
    private String filePath = DEFAULT_FILE_PATH_PREFIX + language;
}
