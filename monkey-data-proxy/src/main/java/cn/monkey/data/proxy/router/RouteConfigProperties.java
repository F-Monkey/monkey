package cn.monkey.data.proxy.router;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ResourceUtils;

@ConfigurationProperties(prefix = "route")
@Data
public class RouteConfigProperties {

    static final String DEFAULT_SCHEME_Dir = ResourceUtils.CLASSPATH_URL_PREFIX + "/schema";

    private String schemaDir = DEFAULT_SCHEME_Dir;
}
