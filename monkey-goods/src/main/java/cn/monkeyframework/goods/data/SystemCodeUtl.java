package cn.monkeyframework.goods.data;

import cn.monkeyframework.common.data.KeyValuePair;
import cn.monkeyframework.goods.data.pojo.SystemCode;

public abstract class SystemCodeUtl {

    public static KeyValuePair<String, String> copy(SystemCode systemCode) {
        if(systemCode == null){
            return null;
        }
        return new KeyValuePair<>(systemCode.getCode(), systemCode.getName());
    }
}
