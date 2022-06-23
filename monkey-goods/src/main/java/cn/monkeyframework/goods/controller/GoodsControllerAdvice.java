package cn.monkeyframework.goods.controller;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.vo.Results;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class GoodsControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result<?> onValidationException(ValidationException e) {
        return Results.fail(e.getMessage());
    }
}
