package cn.monkeyframework.common.data;

public interface QueryRequest {
    default boolean isEmpty() {
        return false;
    }
}
