package cn.monkeyframework.commons.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class PageUtil {
    public static <R, T> Page<R> copy(Page<T> origin, Function<T, R> copy) {
        if (origin.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }
        List<T> content = origin.getContent();
        List<R> copyList = content.stream().map(copy).collect(Collectors.toList());
        return new PageImpl<>(copyList, origin.getPageable(), origin.getTotalElements());
    }

    public static <R> List<R> copy(Iterable<R> iterable){
        List<R> list = new ArrayList<>();
        for(R r: iterable){
            list.add(r);
        }
        return list;
    }
}
