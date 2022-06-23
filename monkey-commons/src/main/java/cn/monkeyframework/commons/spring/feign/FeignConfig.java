package cn.monkeyframework.commons.spring.feign;

import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.PageableSpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(FeignClientsConfiguration.class)
public class FeignConfig {
    @Bean
    PageableSpringEncoder encoder(Encoder encoder) {
        return new PageableSpringEncoder(encoder);
    }
}
