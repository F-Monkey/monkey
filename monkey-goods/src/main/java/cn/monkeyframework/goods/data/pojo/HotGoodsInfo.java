package cn.monkeyframework.goods.data.pojo;

import cn.monkeyframework.common.data.ObjectStatus;
import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotGoodsInfo extends BaseEntity {
    private String id;
    private String goodsId;
    private Integer status = ObjectStatus.VALID;
}
