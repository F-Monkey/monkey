package cn.monkey.order;

import cn.monkeyframework.commons.config.EnableCommonConfig;
import cn.monkeyframework.commons.data.repository.jpa.JpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCommonConfig
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class)
@EntityScan(basePackages = "cn.monkeyframework.commons.data.pojo")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
