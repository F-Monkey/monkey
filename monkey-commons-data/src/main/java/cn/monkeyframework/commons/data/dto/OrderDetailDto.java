package cn.monkeyframework.commons.data.dto;

import cn.monkeyframework.commons.data.KeyValuePair;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailDto implements Serializable {
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private String goodsImgSrc;
    private String goodsDesc;
    private Double quantity;
    private KeyValuePair<String,String> currency;
    private String unit;
    private Double price;
}
