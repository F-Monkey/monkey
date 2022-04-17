package cn.monkeyframework.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monkeyframework.common.config.EnableCommonConfig;
import cn.monkeyframework.common.spring.mvc.ReactiveWebMVCConfig;

@SpringBootApplication
@EnableCommonConfig(value = ReactiveWebMVCConfig.class)
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
