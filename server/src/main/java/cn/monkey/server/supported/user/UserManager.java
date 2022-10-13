package cn.monkey.server.supported.user;

import cn.monkey.server.Session;

public interface UserManager {
    User findOrCreate(Session session, String token);
}
