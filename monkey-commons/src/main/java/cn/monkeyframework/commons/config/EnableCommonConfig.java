package cn.monkeyframework.commons.config;

import cn.monkeyframework.commons.data.jpa.JpaAutoConfig;
import cn.monkeyframework.commons.spring.feign.FeignConfig;
import cn.monkeyframework.commons.spring.mvc.WebMvcConfig;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Inherited
@Import(EnableCommonConfig.CommonConfigImportSelector.class)
public @interface EnableCommonConfig {

    Class<?>[] value() default {WebMvcConfig.class, FeignConfig.class, JpaAutoConfig.class};

    class CommonConfigImportSelector implements ImportSelector {

        @Override
        @NonNull
        public String[] selectImports(@NonNull AnnotationMetadata importingClassMetadata) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableCommonConfig.class.getName(), true));
            if (attributes == null) {
                return new String[]{};
            }
            return attributes.getStringArray("value");
        }
    }
}
