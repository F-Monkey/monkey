package cn.monkeyframework.goods;

import cn.monkeyframework.commons.config.EnableCommonConfig;
import cn.monkeyframework.commons.data.repository.mongo.MonkeyMongoConfig;
import cn.monkeyframework.commons.spring.feign.FeignConfig;
import cn.monkeyframework.commons.spring.mvc.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonConfig(value = {WebMvcConfig.class, FeignConfig.class, MonkeyMongoConfig.class})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
