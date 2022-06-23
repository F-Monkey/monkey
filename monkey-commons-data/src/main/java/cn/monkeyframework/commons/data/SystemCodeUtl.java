package cn.monkeyframework.commons.data;

import cn.monkeyframework.commons.data.pojo.SystemCode;

public abstract class SystemCodeUtl {

    public static KeyValuePair<String, String> copy(SystemCode systemCode) {
        if (systemCode == null) {
            return null;
        }
        return new KeyValuePair<>(systemCode.getCode(), systemCode.getName());
    }
}
