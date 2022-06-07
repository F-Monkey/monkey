package cn.monkeyframework.common.data.jpa;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
/**
 * @see RedisIdentifierGenerator
 */
public @interface Key {

    String key();

    String prefix();

    int length() default 10;
}
