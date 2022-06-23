package cn.monkeyframework.account.controller;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.vo.Results;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<Result<?>> handlerMethodArgumentValidException(MethodArgumentNotValidException e) {
        return Mono.just(Results.fail(e.getMessage()));
    }
}