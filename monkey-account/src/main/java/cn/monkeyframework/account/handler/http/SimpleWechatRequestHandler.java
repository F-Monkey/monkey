package cn.monkeyframework.account.handler.http;

import cn.monkeyframework.account.config.WechatProperties;
import cn.monkeyframework.account.data.vo.WeChatSessionResp;
import cn.monkeyframework.account.handler.WechatRequestHandler;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SimpleWechatRequestHandler implements WechatRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(SimpleWechatRequestHandler.class);

    private static final String REQUEST_TEMPLATE = "https://api.weixin.qq.com/sns/jscode2session";

    private final WechatProperties wechatProperties;

    private final WebClient webClient;

    private final Gson gson;

    public SimpleWechatRequestHandler(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
        this.webClient = WebClient.create();
        this.gson = new Gson();
    }

    @Override
    public Mono<String> getOpenId(String code) {
        return Mono.just(code)
                .map((c) -> REQUEST_TEMPLATE + "?" + "appid=" + this.wechatProperties.getAppId() +
                        "&secret=" + this.wechatProperties.getSecret() +
                        "&js_code=" + c +
                        "&grant_type=" + "authorization_code")
                .flatMap(url -> webClient.get()
                        .uri(url)
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToMono(String.class))
                .flatMap(s -> Mono.just(this.gson.fromJson(s, WeChatSessionResp.class)))
                .flatMap(weChatSessionResp -> {
                    Integer errCode = weChatSessionResp.getErrCode();
                    if (errCode == null || errCode == 0) {
                        return Mono.just(weChatSessionResp.getOpenId());
                    } else {
                        return Mono.error(new IllegalArgumentException("bad request"));
                    }
                }).doOnError(e -> log.error("can not find openId by code: {}, error info:\n", code, e));
    }
}
