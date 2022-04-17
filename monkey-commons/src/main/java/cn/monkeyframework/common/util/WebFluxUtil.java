package cn.monkeyframework.common.util;

import com.google.gson.Gson;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebFluxUtil {
    private WebFluxUtil() {
    }

    private static final Gson gson = new Gson();

    public static Mono<Void> writeAndFlush(ServerHttpResponse response, Object data) {
        return response.writeAndFlushWith(Flux.just(
                Flux.just(response.bufferFactory().wrap(getBytes(data))))
        );
    }

    public static byte[] getBytes(Object data) {
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }

}
