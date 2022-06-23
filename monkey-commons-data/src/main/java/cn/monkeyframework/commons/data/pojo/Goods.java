package cn.monkeyframework.commons.data.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
