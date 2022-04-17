package cn.monkeyframework.account.service;

import cn.monkeyframework.common.data.vo.Result;
import cn.monkeyframework.account.data.dto.WechatLoginRequest;
import cn.monkeyframework.account.data.vo.UserVo;
import reactor.core.publisher.Mono;

public interface IWeChatUserService {
    Mono<Result<UserVo>> getUser(WechatLoginRequest wechatLoginRequest);
}
