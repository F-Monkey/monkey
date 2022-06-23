package cn.monkeyframework.commons.data.dto;

import cn.monkeyframework.commons.data.QueryRequest;
import com.google.common.base.Strings;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsQueryRequest implements QueryRequest, Serializable {

    public interface Action {
        String GET_HOT_GOODS = "getHotGoods";
    }

    private String name;
    private String type;
    private String action;

    @Override
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(type);
    }
}
