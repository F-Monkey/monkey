package cn.monkeyframework.goods.data.pojo;

import cn.monkeyframework.common.data.pojo.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends BaseEntity<String> {
    private String name;
    private String type;
    private String[] imgSrc;
    private Double price;
    private String unit;
    private String currency;
    private String desc;
}
