package cn.monkeyframework.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.monkeyframework.commons.config.EnableCommonConfig;
import cn.monkeyframework.commons.spring.mvc.ReactiveWebMVCConfig;

@SpringBootApplication
@EnableCommonConfig(value = ReactiveWebMVCConfig.class)
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
