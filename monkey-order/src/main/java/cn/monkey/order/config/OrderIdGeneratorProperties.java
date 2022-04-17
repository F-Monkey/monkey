package cn.monkey.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "monkey.order.id-generator")
@Data
public class OrderIdGeneratorProperties {

    static String DEFAULT_KEY = "order_id_key";
    static String DEFAULT_PREFIX = "O_";
    static int DEFAULT_LENGTH = 10;

    private String key = DEFAULT_KEY;
    private String prefix = DEFAULT_PREFIX;
    private int length =DEFAULT_LENGTH;
}
