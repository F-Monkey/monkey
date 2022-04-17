package cn.monkey.order.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "cn.monkey.order.model.pojo")
public class JpaAutoConfig {
}
