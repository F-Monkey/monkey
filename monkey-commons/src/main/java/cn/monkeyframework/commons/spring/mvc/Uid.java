package cn.monkeyframework.commons.spring.mvc;

import cn.monkeyframework.commons.data.UserConstants;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Uid {

    RequestHeader value() default @RequestHeader(name = UserConstants.USER_ID_KEY);
}
