package cn.monkey.server.supported.user;

import cn.monkey.commons.bean.Refreshable;
import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.pojo.UserSession;
import cn.monkey.commons.data.repository.ServerRepository;
import cn.monkey.commons.data.repository.UserSessionRepository;
import cn.monkey.server.Session;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleUserManager implements UserManager, Refreshable {

    protected volatile ConcurrentHashMap<String, User> userMap;

    private final UserSessionRepository userSessionRepository;

    private final ServerRepository serverRepository;

    private final Timer timer;

    private final String serverId;

    public SimpleUserManager(Timer timer,
                             String serverId,
                             UserSessionRepository userSessionRepository,
                             ServerRepository serverRepository) {
        this.userMap = new ConcurrentHashMap<>();
        this.userSessionRepository = userSessionRepository;
        this.serverRepository = serverRepository;
        this.timer = timer;
        this.serverId = serverId;
    }

    @Override
    public User findOrCreate(Session session, String token) {
        ConcurrentHashMap<String, User> userMap = this.userMap;
        User user = userMap.compute(token, (k, v) -> {
            if (v == null) {
                UserSession userSession = this.userSessionRepository.findByToken(k);
                v = this.wrapper(userSession);
                this.serverRepository.increaseCurrentUserCount(userSession.getHallServerId());
                this.userSessionRepository.save(userSession);
            }
            if (v == null) {
                throw new IllegalArgumentException("can not find userSession by token: " + k);
            }
            v.setSession(session);
            return v;
        });
        user.refreshLastOperateTime();
        this.userMap = userMap;
        return user;
    }

    protected User wrapper(UserSession userSession) {
        if (null == userSession) {
            return null;
        }
        SimpleUser simpleUser = new SimpleUser(userSession.getUid());
        simpleUser.setMaxActiveInterval(60 * 1000);
        simpleUser.setTimer(this.timer);
        return simpleUser;
    }

    @Override
    public void refresh() {
        final ConcurrentHashMap<String, User> userMap = this.userMap;
        Iterator<Map.Entry<String, User>> iterator = userMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> next = iterator.next();
            User value = next.getValue();
            if (!value.isActive()) {
                iterator.remove();
            }
        }
        this.serverRepository.setCurrentUserCount(this.serverId, userMap.size());
    }
}
