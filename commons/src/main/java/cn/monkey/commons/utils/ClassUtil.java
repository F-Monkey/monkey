package cn.monkey.commons.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ClassUtil {

    public static Type[] getActualType(Class<?> target) {
        ParameterizedType superGenericSuperclass = (ParameterizedType) target.getGenericSuperclass();
        return superGenericSuperclass.getActualTypeArguments();
    }
}
