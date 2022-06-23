package cn.monkeyframework.goods.context;

import cn.monkeyframework.commons.data.pojo.SystemCode;
import cn.monkeyframework.goods.config.SystemConfigProperties;
import cn.monkeyframework.goods.repository.SystemCodeRepository;
import cn.monkeyframework.system.context.SystemCodeContext;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoodsSystemCodeContext implements SystemCodeContext, InitializingBean {

    public static final String SYSTEM_ID = "goods";

    private final SystemConfigProperties systemConfigProperties;

    private final SystemCodeRepository systemCodeRepository;

    private volatile Map<String, List<SystemCode>> systemCodeMap;

    public GoodsSystemCodeContext(SystemConfigProperties systemConfigProperties,
                                  SystemCodeRepository systemCodeRepository) {
        this.systemCodeRepository = systemCodeRepository;
        this.systemConfigProperties = systemConfigProperties;
        this.systemCodeMap = Collections.emptyMap();
    }


    @Override
    public Map<String, SystemCode> get(String codeType) {
        List<SystemCode> systemCodes = this.systemCodeMap.get(codeType);
        return CollectionUtils.isEmpty(systemCodes)? Collections.emptyMap(): systemCodes.stream().collect(Collectors.toMap(SystemCode::getCode, s->s,(s1,s2) -> s1));
    }

    @Override
    public void afterPropertiesSet() {
        List<SystemCode> systemCodes = this.systemCodeRepository.find(SYSTEM_ID);
        Map<String, List<SystemCode>> map = systemCodes.parallelStream().collect(Collectors.groupingBy(SystemCode::getType));
        this.systemCodeMap = ImmutableMap.copyOf(map);
    }
}
