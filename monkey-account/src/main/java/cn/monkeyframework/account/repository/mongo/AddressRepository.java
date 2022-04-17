package cn.monkeyframework.account.repository.mongo;

import cn.monkeyframework.account.data.pojo.Address;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AddressRepository extends ReactiveMongoRepository<Address, String> {

}
