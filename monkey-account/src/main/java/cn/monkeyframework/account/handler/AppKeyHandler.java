package cn.monkeyframework.account.handler;

import reactor.core.publisher.Mono;

public interface AppKeyHandler {
    Mono<String> newAppKey();
}
