package cn.monkeyframework.goods;

import cn.monkeyframework.common.config.EnableCommonConfig;
import cn.monkeyframework.common.spring.feign.FeignConfig;
import cn.monkeyframework.common.spring.mvc.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonConfig(value = {WebMvcConfig.class, FeignConfig.class,})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
