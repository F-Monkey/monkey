package cn.monkey.commons.data.repository;

import cn.monkey.commons.data.pojo.ServerConfig;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveServerRepository {
    Mono<List<ServerConfig>> getServerConfig(String type);
}
