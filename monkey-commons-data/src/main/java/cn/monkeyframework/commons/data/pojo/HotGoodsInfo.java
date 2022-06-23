package cn.monkeyframework.commons.data.pojo;

import cn.monkeyframework.commons.data.ObjectStatus;
import cn.monkeyframework.commons.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotGoodsInfo extends BaseEntity<String> {
    private String goodsId;
    private Integer status = ObjectStatus.VALID;
}
