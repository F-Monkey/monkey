package cn.monkeyframework.account.handler;

import reactor.core.publisher.Mono;

public interface WechatRequestHandler {
    Mono<String> getOpenId(String code);
}
