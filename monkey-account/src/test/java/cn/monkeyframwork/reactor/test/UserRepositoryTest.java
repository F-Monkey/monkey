package cn.monkeyframwork.reactor.test;

import cn.monkeyframework.account.AccountApplication;
import cn.monkeyframework.commons.data.pojo.User;
import cn.monkeyframework.commons.data.pojo.WechatUser;
import cn.monkeyframework.account.repository.mongo.UserRepository;
import cn.monkeyframework.account.repository.mongo.WechatUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = AccountApplication.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WechatUserRepository wechatUserRepository;

    @Test
    public void test01() {
        String id = "6247ef803584935a6c29815e";
        Mono<User> byId = this.userRepository.findById(id);
        User block = byId.block();
        System.out.println("user: " + block);
    }

    @Test
    public void test02() {
        String appKey = "app_0000000002";
        Mono<WechatUser> byAppKey = this.wechatUserRepository.findByAppKey(appKey);
        WechatUser block = byAppKey.block();
        System.out.println("wechatUser: " + block);
    }
}
