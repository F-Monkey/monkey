package cn.monkeyframework.account.data.pojo;

import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WechatUser extends BaseEntity<String> {
    private String id;
    private String openId;
    private String sessionId;
    private String userId;
    private String appKey;
}
