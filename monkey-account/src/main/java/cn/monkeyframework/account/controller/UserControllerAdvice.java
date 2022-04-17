package cn.monkeyframework.account.controller;

import cn.monkeyframework.common.data.vo.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<Result<?>> handlerMethodArgumentValidException(MethodArgumentNotValidException e) {
        return Mono.just(Result.fail(e.getMessage()));
    }
}