package cn.monkey.gateway.repository;

import cn.monkey.gateway.data.User;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    default Mono<User> findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        Example<User> example = Example.of(user);
        return findOne(example);
    }
}
