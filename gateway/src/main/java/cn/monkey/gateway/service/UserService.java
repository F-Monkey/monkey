package cn.monkey.gateway.service;

import cn.monkey.proto.Command;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Command.PackageGroup> login(Mono<Command.Package> requestEntity);

    Mono<Command.PackageGroup> register(Mono<Command.Package> request);
}
