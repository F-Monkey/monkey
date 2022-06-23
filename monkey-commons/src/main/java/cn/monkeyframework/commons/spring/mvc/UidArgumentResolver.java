package cn.monkeyframework.commons.spring.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class UidArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Uid.class) != null && String.class.equals(parameter.getParameter().getType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        Uid uid = parameter.getParameterAnnotation(Uid.class);
        RequestHeader requestHeader = Objects.requireNonNull(uid).value();
        String header = webRequest.getHeader(requestHeader.name());
        return requestHeader.required() ? Objects.requireNonNull(header) : header;
    }
}
