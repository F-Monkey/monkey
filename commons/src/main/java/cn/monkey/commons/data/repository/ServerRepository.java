package cn.monkey.commons.data.repository;


import cn.monkey.commons.data.pojo.ServerConfig;

import java.util.List;

public interface ServerRepository {
    List<ServerConfig> getServerConfig(String name);

    void increaseCurrentUserCount(String id);

    void setCurrentUserCount(String serverId, long count);
}
