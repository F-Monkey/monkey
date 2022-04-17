package cn.monkeyframework.common.spring.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.SyncHandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

public class ReactiveUidArgumentResolver implements SyncHandlerMethodArgumentResolver {

    @Override
    public Object resolveArgumentValue(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Uid uid = parameter.getParameterAnnotation(Uid.class);
        String value = Objects.requireNonNull(uid).value();
        String header = headers.getFirst(value);
        return uid.required()? header: Objects.requireNonNull(header);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Uid.class) != null && String.class.equals(parameter.getParameter().getType());
    }
}
