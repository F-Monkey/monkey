package cn.monkeyframework.account.repository.mongo;

import cn.monkeyframework.commons.data.pojo.WechatUser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface WechatUserRepository extends ReactiveMongoRepository<WechatUser, String> {
    default Mono<WechatUser> findByOpenId(String openId) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(openId);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("openId", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<WechatUser> example = Example.of(wechatUser, exampleMatcher);
        return findOne(example);
    }

    default Mono<WechatUser> findByAppKey(String appKey) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setAppKey(appKey);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("appKey", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<WechatUser> example = Example.of(wechatUser, exampleMatcher);
        return findOne(example);
    }

    default Mono<WechatUser> findByUid(String uid) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setUserId(uid);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<WechatUser> example = Example.of(wechatUser, exampleMatcher);
        return findOne(example);
    }
}
