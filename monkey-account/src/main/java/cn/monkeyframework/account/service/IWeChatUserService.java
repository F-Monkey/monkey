package cn.monkeyframework.account.service;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.WechatLoginRequest;
import cn.monkeyframework.commons.data.vo.UserVo;
import reactor.core.publisher.Mono;

public interface IWeChatUserService {
    Mono<Result<UserVo>> getUser(WechatLoginRequest wechatLoginRequest);
}
