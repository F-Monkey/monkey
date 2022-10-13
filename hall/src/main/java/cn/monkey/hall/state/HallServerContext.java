package cn.monkey.hall.state;

import cn.monkey.commons.data.pojo.ServerConfig;
import cn.monkey.commons.data.repository.ServerRepository;
import cn.monkey.server.supported.user.User;
import cn.monkey.state.core.StateContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HallServerContext implements StateContext {
    private final Queue<User> userQueue;

    private final ServerRepository roomServerRepository;

    public HallServerContext(ServerRepository roomServerRepository) {
        this.userQueue = new LinkedList<>();
        this.roomServerRepository = roomServerRepository;
    }

    public boolean add(User user) {
        return this.userQueue.offer(user);
    }

    public Queue<User> getUsers() {
        return this.userQueue;
    }

    public List<ServerConfig> serverConfigs(String groupId) {
        return this.roomServerRepository.getServerConfig(groupId);
    }
}
