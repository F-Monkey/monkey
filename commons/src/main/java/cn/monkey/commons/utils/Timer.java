package cn.monkey.commons.utils;

public interface Timer {

    default long getCurrentTimeMs() {
        return System.currentTimeMillis();
    }
}
