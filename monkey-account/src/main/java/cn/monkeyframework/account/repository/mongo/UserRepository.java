package cn.monkeyframework.account.repository.mongo;

import cn.monkeyframework.commons.data.pojo.User;
import com.google.common.base.Strings;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    @Query(value = "{$and:[{$or:[{'nickName':?0},{'phone':?0},{'email':?0}]},{'password':?1}]}")
    Mono<User> findByQueryKeyAndPassword(String queryKey, String password);

    default Mono<Page<User>> findByQueryKey(String queryKey, Pageable pageable) {
        if (Strings.isNullOrEmpty(queryKey)) {
            Example<User> example = Example.of(new User());
            return findBy(example, userReactiveFluentQuery -> userReactiveFluentQuery.page(pageable));
        }
        return Mono.just(Tuples.of(queryKey, pageable))
                .flatMap(tuple -> {
                    String t1 = tuple.getT1();
                    Pageable t2 = tuple.getT2();
                    User user = new User();
                    user.setNickName(t1);
                    user.setEmail(t1);
                    user.setPhone(t1);
                    ExampleMatcher matcher = ExampleMatcher.matchingAny()
                            .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                            .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains())
                            .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains());
                    Example<User> example = Example.of(user, matcher);
                    return findBy(example, userReactiveFluentQuery -> userReactiveFluentQuery.page(t2));
                });
    }
}
