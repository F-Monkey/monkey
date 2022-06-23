package cn.monkeyframework.commons.util;

public interface Timer {
    default long getCurrentTimeMiles() {
        return System.currentTimeMillis();
    }
}
