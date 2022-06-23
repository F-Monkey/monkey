package cn.monkeyframework.account.controller;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.UserDto;
import cn.monkeyframework.commons.data.vo.UserVo;
import cn.monkeyframework.account.service.IUserService;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class SysUserController {

    private final IUserService userService;

    public SysUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/{id}", ""})
    Mono<? extends Result<?>> get(@PathVariable(required = false, name = "id") String id,
                                  @RequestParam(required = false, name = "query_key") String queryKey,
                                  @RequestParam(required = false,name = "username") String username,
                                  @RequestParam(required = false, name = "password") String password,
                                  @PageableDefault Pageable pageable) {
        if (!Strings.isNullOrEmpty(id)) {
            return this.userService.select(id);
        }
        if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
            return this.userService.login(username, password);
        }
        return this.userService.select(queryKey, pageable);
    }

    @PostMapping
    Mono<Result<UserVo>> create(@RequestBody @Validated UserDto userDto) {
        return this.userService.add(userDto);
    }

    @PutMapping("/{id}")
    Mono<Result<UserVo>> update(@PathVariable(name = "id") String id,
                                @RequestBody UserDto userDto) {
        return this.userService.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    Mono<Result<Void>> delete(@PathVariable(name = "id") String id) {
        return this.userService.delete(id);
    }

}
