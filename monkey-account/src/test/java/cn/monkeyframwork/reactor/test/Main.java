package cn.monkeyframwork.reactor.test;

import cn.monkeyframework.account.data.pojo.User;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        Function<ContextView, Mono<User>> function = (u) -> Mono.just(user);
        Mono.deferContextual(function)
                .transformDeferredContextual((userMono, contextView) -> userMono.flatMap(u -> {
                    String nickName = u.getNickName();
                    return Mono.just(u);
                }))
                .transformDeferredContextual((userMono, contextView) -> userMono.flatMap(u -> {
                    String password = u.getPassword();
                    return Mono.just(u);
                }))
                .subscribe();

    }
}
