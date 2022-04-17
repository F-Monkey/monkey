package cn.monkeyframework.goods.data;

import cn.monkeyframework.goods.data.dto.GoodsQueryRequest;
import cn.monkeyframework.goods.data.pojo.Goods;
import com.google.common.base.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public abstract class GoodsQueryRequestUtil {
    public static Example<Goods> build(GoodsQueryRequest queryRequest) {
        Goods goods = new Goods();
        if (queryRequest.isEmpty()) {
            return Example.of(goods);
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        String name = queryRequest.getName();
        if (!Strings.isNullOrEmpty(name)) {
            goods.setName(name);
            exampleMatcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        String type = queryRequest.getType();
        if (!Strings.isNullOrEmpty(type)) {
            exampleMatcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact());
            goods.setType(type);
        }
        return Example.of(goods, exampleMatcher);
    }
}
