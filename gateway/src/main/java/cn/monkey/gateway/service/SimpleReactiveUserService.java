package cn.monkey.gateway.service;


import cn.monkey.commons.data.ServerType;
import cn.monkey.commons.data.pojo.ServerConfig;
import cn.monkey.commons.data.pojo.UserSession;
import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.commons.data.repository.ReactiveServerRepository;
import cn.monkey.commons.data.repository.ReactiveUserSessionRepository;
import cn.monkey.gateway.repository.UserRepository;
import cn.monkey.gateway.util.CommandUtil;
import cn.monkey.proto.Command;
import cn.monkey.proto.User;
import com.google.common.base.Strings;
import com.google.protobuf.InvalidProtocolBufferException;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.function.Predicate;

public class SimpleReactiveUserService implements UserService {

    private final UserRepository userRepository;

    private final ReactiveUserSessionRepository userSessionRepository;

    private final ReactiveServerRepository serverRepository;

    public SimpleReactiveUserService(UserRepository userRepository,
                                     ReactiveUserSessionRepository userSessionRepository,
                                     ReactiveServerRepository serverRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.serverRepository = serverRepository;
    }

    @Override
    public Mono<Command.PackageGroup> login(Mono<Command.Package> pkg) {
        return pkg.flatMap(p -> {
                    try {
                        return Mono.just(User.Login.parseFrom(p.getContent()));
                    } catch (InvalidProtocolBufferException e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(login -> {
                    String uid = login.getUid();
                    if (!Strings.isNullOrEmpty(uid)) {
                        return this.loginByUid(uid);
                    } else {
                        return this.loginByUsernameAndPassword(login.getUsername(), login.getPassword());
                    }
                });
    }

    @Override
    public Mono<Command.PackageGroup> register(Mono<Command.Package> request) {
        return request.flatMap(p -> {
            try {
                User.Register register = User.Register.parseFrom(p.getContent());
                return this.register(register.getUsername(), register.getPassword());
            } catch (InvalidProtocolBufferException e) {
                return Mono.error(e);
            }
        });
    }

    private Mono<Command.PackageGroup> register(String username, String password) {
        return this.userRepository.findByUsername(username)
                .flatMap(user -> {
                    if (user != null) {
                        throw new IllegalArgumentException("username: " + username + " is already exists");
                    }
                    user = new cn.monkey.gateway.data.User();
                    user.setUsername(username);
                    user.setPassword(password);
                    return this.userRepository.save(user);
                })
                .map(user -> {
                    String id = user.getId();
                    String username1 = user.getUsername();
                    User.UserInfo.Builder builder = User.UserInfo.newBuilder();
                    builder.setUid(id);
                    builder.setUsername(username1);
                    return cn.monkey.proto.CommandUtil.packageGroup(cn.monkey.proto.CommandUtil.pkg(ResultCode.OK, null, null, builder.build().toByteString()));
                })
                .onErrorResume(e -> Mono.just(cn.monkey.proto.CommandUtil.error(ResultCode.ERROR, e)));
    }

    private Mono<? extends Command.PackageGroup> loginByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsername(username)
                .flatMap(user -> {
                    String password1 = user.getPassword();
                    if (!this.checkPassword(password, password1)) {
                        return Mono.empty();
                    }
                    return Mono.just(user);
                })
                .flatMap(user -> findBestHallServer(user).map(us -> Tuples.of(user, us)))
                .map(t -> CommandUtil.login(t.getT1(), t.getT2()))
                .switchIfEmpty(Mono.just(CommandUtil.loginFail("bad username or password")))
                .onErrorResume(e -> Mono.just(cn.monkey.proto.CommandUtil.error(ResultCode.ERROR, e)));
    }

    private Mono<? extends Command.PackageGroup> loginByUid(String uid) {
        return this.userRepository.findById(uid)
                .flatMap(user -> this.userSessionRepository.findById(uid)
                        .map(userSession -> Tuples.of(user, userSession)))
                .map(t -> CommandUtil.login(t.getT1(), t.getT2()))
                .switchIfEmpty(Mono.just(CommandUtil.loginFail("bad uid: " + uid)))
                .onErrorResume(e -> Mono.just(cn.monkey.proto.CommandUtil.error(ResultCode.ERROR, e)));
    }

    private Mono<UserSession> findBestHallServer(cn.monkey.gateway.data.User user) {
        Mono<List<ServerConfig>> serverConfigs = this.serverRepository.getServerConfig(ServerType.HALL);
        Predicate<ServerConfig> p = serverConfig -> serverConfig.getCurrentUserSize() < serverConfig.getMaxUserSize();

        return serverConfigs.flatMap(list -> {
            for (ServerConfig sc : list) {
                if (p.test(sc)) {
                    UserSession userSession = new UserSession();
                    userSession.setUid(user.getId());
                    userSession.setHallServerId(sc.getId());
                    userSession.setHallServerUrl(sc.getUrl());
                    return Mono.just(userSession);
                }
            }
            return Mono.error(new IllegalArgumentException("empty server"));
        });
    }

    private boolean checkPassword(String password, String password1) {
        return true;
    }
}
