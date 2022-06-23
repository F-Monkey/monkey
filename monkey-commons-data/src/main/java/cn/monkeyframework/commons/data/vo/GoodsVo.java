package cn.monkeyframework.commons.data.vo;

import cn.monkeyframework.commons.data.KeyValuePair;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsVo implements Serializable {
    private String id;
    private String name;
    private KeyValuePair<String, String> type;
    private String[] imgSrc;
    private Double price;
    private String unit;
    private KeyValuePair<String, String> currency;
    private String desc;
}