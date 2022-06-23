package cn.monkeyframework.account.controller;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.WechatLoginRequest;
import cn.monkeyframework.commons.data.vo.Results;
import cn.monkeyframework.commons.data.vo.UserVo;
import cn.monkeyframework.account.service.IWeChatUserService;
import com.google.common.base.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wechat")
public class SysWeChatController {

    private final IWeChatUserService weChatUserService;

    public SysWeChatController(IWeChatUserService weChatUserService) {
        this.weChatUserService = weChatUserService;
    }

    @GetMapping
    Mono<Result<UserVo>> getLoginSession(@RequestParam(value = "code", required = false) String code,
                                         @RequestParam(value = "user_app_key", required = false) String userAppKey) {
        if (Strings.isNullOrEmpty(code) && Strings.isNullOrEmpty(userAppKey)) {
            return Mono.just(Results.fail("empty request"));
        }
        WechatLoginRequest wechatLoginRequest = WechatLoginRequest.newBuilder().code(code)
                .userAppkey(userAppKey).build();
        return this.weChatUserService.getUser(wechatLoginRequest);
    }
}
