package cn.monkeyframework.account.service;

import cn.monkeyframework.commons.data.UserUtil;
import cn.monkeyframework.commons.data.pojo.User;
import cn.monkeyframework.commons.data.pojo.WechatUser;
import cn.monkeyframework.account.repository.mongo.WechatUserRepository;
import cn.monkeyframework.commons.data.ObjectStatus;
import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.vo.Results;
import cn.monkeyframework.commons.util.PageUtil;
import cn.monkeyframework.commons.data.dto.UserDto;
import cn.monkeyframework.commons.data.vo.UserVo;
import cn.monkeyframework.account.repository.mongo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
public class UserService implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final WechatUserRepository wechatUserRepository;

    public UserService(UserRepository userRepository,
                       WechatUserRepository wechatUserRepository) {
        this.userRepository = userRepository;
        this.wechatUserRepository = wechatUserRepository;
    }

    @Override
    public Mono<Result<UserVo>> select(String uid) {
        return this.userRepository.findById(uid)
                .map(u -> Results.ok(UserUtil.copy(u)))
                .defaultIfEmpty(Results.fail("invalid uid: " + uid))
                .doOnError(t -> log.error("#find(String) error:\n", t))
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }

    @Override
    public Mono<Result<UserVo>> login(String queryKey, String password) {
        return this.userRepository.findByQueryKeyAndPassword(queryKey, password)
                .map(u -> Results.ok(UserUtil.copy(u)))
                .defaultIfEmpty(Results.fail("invalid queryKey or password"))
                .doOnError(t -> log.error("#find(String,String) error:\n", t))
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }

    @Override
    public Mono<Result<Page<UserVo>>> select(@Nullable String queryKey, Pageable pageable) {
        return this.userRepository.findByQueryKey(queryKey, pageable)
                .map(p -> Results.ok(PageUtil.copy(p, UserUtil::copy)))
                .doOnError(t -> {
                    log.error("#find(String,Pageable) error:\n", t);
                })
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }

    @Override
    public Mono<Result<UserVo>> add(UserDto userDto) {
        User user = UserUtil.copy(userDto);
        user.setCreateTs(System.currentTimeMillis());
        user.setUpdateTs(System.currentTimeMillis());
        return this.userRepository.save(user)
                .flatMap(u -> this.wechatUserRepository.findByUid(u.getId())
                        .map(wechatUser -> Tuples.of(wechatUser, u)))
                .map(t -> {
                    WechatUser t1 = t.getT1();
                    User t2 = t.getT2();
                    UserVo copy = UserUtil.copy(t2);
                    copy.setAppKey(t1.getAppKey());
                    return Results.ok(copy);
                })
                .switchIfEmpty(Mono.just(Results.fail("user create fail...")))
                .doOnError(t -> {
                    log.error("#add(UserDto) error:\n", t);
                })
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }

    @Override
    public Mono<Result<UserVo>> update(String id, UserDto userDto) {
        User user = UserUtil.copy(userDto);
        user.setId(id);
        user.setUpdateTs(System.currentTimeMillis());
        user.setStatus(ObjectStatus.VALID);
        return this.userRepository.save(user)
                .flatMap(u -> this.wechatUserRepository.findByUid(u.getId())
                        .map(wechatUser -> Tuples.of(wechatUser, u)))
                .map(t -> {
                    WechatUser t1 = t.getT1();
                    User t2 = t.getT2();
                    UserVo copy = UserUtil.copy(t2);
                    copy.setAppKey(t1.getAppKey());
                    return Results.ok(copy);
                })
                .switchIfEmpty(Mono.just(Results.fail("user update fail...")))
                .doOnError(t -> {
                    log.error("#add(UserDto) error:\n", t);
                })
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }

    @Override
    public Mono<Result<Void>> delete(String id) {
        return Mono.just(id).flatMap(userRepository::findById)
                .map((u) -> {
                    if (u != null) {
                        log.info("delete user: {}", u);
                        return this.userRepository.delete(u);
                    }
                    log.debug("can not find user by id: " + id);
                    return Mono.empty();
                })
                .map(v -> Results.<Void>ok())
                .defaultIfEmpty(Results.fail("id: " + id + " is not exists"))
                .onErrorResume(e -> Mono.just(Results.error(e)));
    }
}
