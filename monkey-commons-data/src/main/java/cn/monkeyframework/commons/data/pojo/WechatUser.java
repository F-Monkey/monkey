package cn.monkeyframework.commons.data.pojo;

import cn.monkeyframework.commons.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WechatUser extends BaseEntity<String> {
    private String openId;
    private String sessionId;
    private String userId;
    private String appKey;
}
