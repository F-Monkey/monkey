package cn.monkeyframework.account.service;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.account.data.dto.UserDto;
import cn.monkeyframework.account.data.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

public interface IUserService {

    Mono<Result<UserVo>> select(String uid);

    Mono<Result<UserVo>> login(String queryKey, String password);

    Mono<Result<Page<UserVo>>> select(@Nullable String queryKey, Pageable pageable);

    Mono<Result<UserVo>> add(UserDto userDto);

    Mono<Result<UserVo>> update(String id, UserDto userDto);

    Mono<Result<Void>> delete(String id);
}
