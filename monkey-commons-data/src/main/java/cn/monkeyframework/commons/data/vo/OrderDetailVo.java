package cn.monkeyframework.commons.data.vo;

import cn.monkeyframework.commons.data.KeyValuePair;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailVo implements Serializable {
    private String id;
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private String goodsImgSrc;
    private String goodsDesc;
    private Double quantity;
    private KeyValuePair<String,String> currency;
    private Double price;
    private String unit;
}
