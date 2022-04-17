package cn.monkeyframework.account.cache;

import reactor.core.publisher.Mono;

public interface WechatCache {
    Mono<String> getOpenId(String code);
}
