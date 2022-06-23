package cn.monkeyframework.commons.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurityAutoConfig {

    @Bean
    WebSecurityProperties webSecurityProperties() {
        return new WebSecurityProperties();
    }


    @Bean
    WebSecurityConfigurerAdapter webSecurityConfigurer(WebSecurityProperties webSecurityProperties) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(new UrlBasedCorsConfigurationSource()));
            }
        };
    }
}
