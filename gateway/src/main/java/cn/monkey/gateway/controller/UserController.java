package cn.monkey.gateway.controller;

import cn.monkey.gateway.service.UserService;
import cn.monkey.proto.Command;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    Mono<Command.PackageGroup> login(Mono<Command.Package> requestEntity) {
        return this.userService.login(requestEntity);
    }

    @RequestMapping("/register")
    @PostMapping
    Mono<Command.PackageGroup> register(Mono<Command.Package> request) {
        return this.userService.register(request);
    }
}
