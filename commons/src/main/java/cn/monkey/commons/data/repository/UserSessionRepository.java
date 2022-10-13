package cn.monkey.commons.data.repository;

import cn.monkey.commons.data.pojo.UserSession;

public interface UserSessionRepository {
    UserSession findByToken(String uid);

    void save(UserSession userSession);
}
