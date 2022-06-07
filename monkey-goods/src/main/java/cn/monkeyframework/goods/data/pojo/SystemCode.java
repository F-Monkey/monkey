package cn.monkeyframework.goods.data.pojo;

import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemCode extends BaseEntity<String> {
    private String systemId;
    private String type;
    private String code;
    private String name;
}
