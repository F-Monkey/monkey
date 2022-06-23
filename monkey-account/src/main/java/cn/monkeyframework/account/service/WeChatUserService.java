package cn.monkeyframework.account.service;

import cn.monkeyframework.commons.data.ObjectStatus;
import cn.monkeyframework.commons.data.UserUtil;
import cn.monkeyframework.commons.data.pojo.User;
import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.account.cache.WechatCache;
import cn.monkeyframework.commons.data.dto.UserDto;
import cn.monkeyframework.commons.data.dto.WechatLoginRequest;
import cn.monkeyframework.commons.data.pojo.WechatUser;
import cn.monkeyframework.commons.data.vo.UserVo;
import cn.monkeyframework.account.handler.AppKeyHandler;
import cn.monkeyframework.account.repository.mongo.UserRepository;
import cn.monkeyframework.account.repository.mongo.WechatUserRepository;
import cn.monkeyframework.commons.data.vo.Results;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class WeChatUserService implements IWeChatUserService {

    private static final Logger log = LoggerFactory.getLogger(WeChatUserService.class);


    private final Scheduler scheduler;

    private final WechatCache wechatCache;

    private final WechatUserRepository wechatUserRepository;

    private final AppKeyHandler appKeyHandler;

    private final UserRepository userRepository;

    public WeChatUserService(WechatCache wechatCache,
                             WechatUserRepository wechatUserRepository,
                             AppKeyHandler appKeyHandler,
                             UserRepository userRepository) {
        this.wechatCache = wechatCache;
        this.userRepository = userRepository;
        this.appKeyHandler = appKeyHandler;
        this.wechatUserRepository = wechatUserRepository;
        this.scheduler = Schedulers.newSingle("wechat");
    }

    @Override
    @Transactional
    public Mono<Result<UserVo>> getUser(WechatLoginRequest wechatLoginRequest) {
        return Mono.just(wechatLoginRequest)
                .doOnNext(request -> log.info("request info: {}", request))
                .flatMap(request -> {
                    String userAppKey = request.getUserAppKey();
                    Mono<WechatUser> mono;
                    if (!Strings.isNullOrEmpty(userAppKey)) {
                        mono = this.wechatUserRepository.findByAppKey(userAppKey)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("can not find wechat user info, bad user app key: " + userAppKey)));
                    } else {
                        mono = this.wechatCache.getOpenId(request.getCode())
                                .flatMap(openId -> this.wechatUserRepository.findByOpenId(openId)
                                        .switchIfEmpty(handlerIfOpenIdCanNotFind(openId)));
                    }
                    return mono.flatMap(wechatUser -> {
                        String userId = wechatUser.getUserId();
                        if (Strings.isNullOrEmpty(userId)) {
                            return this.createEmptyUser(wechatUser);
                        }
                        return Mono.just(wechatUser);
                    });
                })
                .flatMap(wechatUser -> {
                    String userId = wechatUser.getUserId();
                    if (Strings.isNullOrEmpty(userId)) {
                        return Mono.error(new IllegalArgumentException("bad wechat user info, userId is empty"));
                    }

                    return this.userRepository.findById(userId).map(user -> {
                        UserVo vo = UserUtil.copy(user);
                        vo.setAppKey(wechatUser.getAppKey());
                        return vo;
                    }).switchIfEmpty(Mono.error(new IllegalArgumentException("bad wechat user info, can not find user by uid: " + userId)));
                }).map(Results::ok)
                .doOnError(e -> log.error("wechat login error:\n", e))
                .onErrorResume(e -> Mono.just(Results.fail(e.getMessage())))
                .doOnNext(userVoResult -> log.info("user result: {}", userVoResult))
                .subscribeOn(this.scheduler);
    }

    protected Mono<WechatUser> handlerIfOpenIdCanNotFind(String openId) {
        return this.appKeyHandler.newAppKey().flatMap((appKey -> {
            WechatUser wechatUser = new WechatUser();
            wechatUser.setAppKey(appKey);
            wechatUser.setOpenId(openId);
            return this.wechatUserRepository.save(wechatUser);
        }));
    }

    protected Mono<WechatUser> createEmptyUser(WechatUser wechatUser) {
        User user = UserUtil.copy(new UserDto());
        user.setStatus(ObjectStatus.INVALID);
        long time = System.currentTimeMillis();
        user.setCreateTs(time);
        user.setUpdateTs(time);
        return this.userRepository.save(user).flatMap(u -> {
            wechatUser.setUserId(u.getId());
            return this.wechatUserRepository.save(wechatUser);
        });
    }
}
