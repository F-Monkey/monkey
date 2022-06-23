package cn.monkeyframework.goods.repository;

import cn.monkeyframework.commons.data.ObjectStatus;
import cn.monkeyframework.commons.data.pojo.HotGoodsInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotGoodsRepository extends MongoRepository<HotGoodsInfo, String> {
    default Page<HotGoodsInfo> findValidHotGoods(Pageable pageable) {
        HotGoodsInfo hotGoodsInfo = new HotGoodsInfo();
        Example<HotGoodsInfo> example = Example.of(hotGoodsInfo);
        return findAll(example, pageable);
    }
}
