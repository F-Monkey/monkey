package cn.monkey.commons.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Refresh {

    /**
     * {@link Refreshable#refresh()}
     * @return method name
     */
    String method() default "refresh";

    long delay() default 0;

    long timeInterval() default 1000;
}
