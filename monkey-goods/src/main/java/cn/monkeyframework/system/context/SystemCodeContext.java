package cn.monkeyframework.system.context;

import cn.monkeyframework.commons.data.pojo.SystemCode;

import java.util.Map;

public interface SystemCodeContext {
    Map<String, SystemCode> get(String codeType);
}
