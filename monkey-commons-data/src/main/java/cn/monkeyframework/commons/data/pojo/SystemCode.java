package cn.monkeyframework.commons.data.pojo;

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
