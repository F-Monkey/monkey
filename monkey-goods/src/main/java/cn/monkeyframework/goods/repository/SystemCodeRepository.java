package cn.monkeyframework.goods.repository;

import cn.monkeyframework.commons.data.pojo.SystemCode;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemCodeRepository extends MongoRepository<SystemCode, String> {
    default Optional<SystemCode> find(String systemId, String code) {
        SystemCode systemCode = new SystemCode();
        systemCode.setCode(code);
        systemCode.setSystemId(systemId);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("systemId", ExampleMatcher.GenericPropertyMatcher::exact)
                .withMatcher("code", ExampleMatcher.GenericPropertyMatcher::exact);
        Example<SystemCode> example = Example.of(systemCode, exampleMatcher);
        return findOne(example);
    }

    default List<SystemCode> find(String systemId) {
        SystemCode systemCode = new SystemCode();
        systemCode.setSystemId(systemId);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("systemId", ExampleMatcher.GenericPropertyMatcher::exact);
        Example<SystemCode> example = Example.of(systemCode, exampleMatcher);
        return findAll(example);
    }
}
