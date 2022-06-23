package cn.monkeyframework.commons.data;

public interface QueryRequest {


    default boolean isEmpty() {
        return false;
    }
}
