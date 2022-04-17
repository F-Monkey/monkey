package cn.monkeyframework.goods.repository;

import cn.monkeyframework.goods.data.pojo.Goods;
import com.google.common.base.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


@Repository
public interface GoodsRepository extends MongoRepository<Goods, String> {

    default Page<Goods> findBy(@Nullable String name, Pageable pageable) {
        Goods goods = new Goods();
        Example<Goods> example;
        if (!Strings.isNullOrEmpty(name)) {
            goods.setName(name);
            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
            example = Example.of(goods, matcher);
        } else {
            example = Example.of(goods);
        }
        return findAll(example, pageable);
    }
}
