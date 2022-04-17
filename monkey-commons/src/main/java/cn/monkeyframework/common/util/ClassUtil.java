package cn.monkeyframework.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {
    private ClassUtil() {
    }

    public static Type[] getActualTypeTypeArguments(Class<?> clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        return parameterizedType.getActualTypeArguments();
    }

    public static Class<?> getActualType(Class<?> clazz) {
        Type[] types = getActualTypeTypeArguments(clazz);
        return (Class<?>) types[0];
    }
}
