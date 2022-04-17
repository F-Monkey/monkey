package cn.monkeyframework.common.util;

public interface Timer {
    default long getCurrentTimeMiles() {
        return System.currentTimeMillis();
    }
}
