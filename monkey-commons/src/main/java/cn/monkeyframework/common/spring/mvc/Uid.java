package cn.monkeyframework.common.spring.mvc;

import cn.monkeyframework.common.data.UserConstants;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Uid {
    String value() default UserConstants.USER_ID_KEY;

    boolean required() default true;
}
