package cn.monkeyframework.commons.spring.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfig {

    @Bean
    @ConditionalOnMissingBean
    MessageConfigurationProperties messageConfigurationProperties() {
        return new MessageConfigurationProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    MessageSource messageSource(MessageConfigurationProperties messageConfigurationProperties) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        String filePath = messageConfigurationProperties.getFilePath();
        messageSource.setBasename(filePath);
        messageSource.setDefaultEncoding(messageConfigurationProperties.getEncoding());
        return messageSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public static LocalValidatorFactoryBean defaultValidator(ApplicationContext applicationContext) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory(applicationContext);
        //factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        return factoryBean;
    }
}
