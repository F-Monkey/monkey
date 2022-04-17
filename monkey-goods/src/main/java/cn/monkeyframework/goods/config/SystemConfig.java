package cn.monkeyframework.goods.config;

import cn.monkeyframework.goods.repository.SystemCodeRepository;
import cn.monkeyframework.goods.context.GoodsSystemCodeContext;
import cn.monkeyframework.system.context.SystemCodeContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
    @Bean
    SystemConfigProperties systemConfigProperties() {
        return new SystemConfigProperties();
    }

    @Bean
    SystemCodeContext systemCodeContext(SystemConfigProperties systemConfigProperties,
                                        SystemCodeRepository systemCodeRepository) {
        return new GoodsSystemCodeContext(systemConfigProperties, systemCodeRepository);
    }
}
