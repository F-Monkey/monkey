package cn.monkeyframework.account.repository.mongo;

import cn.monkeyframework.commons.data.pojo.UserAddress;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

public interface UserAddressRepository extends ReactiveMongoRepository<UserAddress, String> {
    default Mono<Page<UserAddress>> findByUid(String uid, Pageable pageable) {
        return Mono.just(Tuples.of(uid, pageable))
                .map(t -> {
                    UserAddress userAddress = new UserAddress();
                    userAddress.setUid(t.getT1());
                    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("uid", ExampleMatcher.GenericPropertyMatchers.exact());
                    Example<UserAddress> example = Example.of(userAddress, exampleMatcher);
                    return Tuples.of(example, t.getT2());
                }).flatMap(t -> findBy(t.getT1(), userReactiveFluentQuery -> userReactiveFluentQuery.page(t.getT2())));
    }
}
