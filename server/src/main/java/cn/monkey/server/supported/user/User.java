package cn.monkey.server.supported.user;

import cn.monkey.commons.utils.Timer;
import cn.monkey.proto.Command;
import cn.monkey.server.Session;

public interface User {

    String getUid();

    Session getSession();

    String getUsername();

    void setSession(Session session);

    default void write(Command.PackageGroup msg) {
        Session session = this.getSession();
        session.write(msg);
    }

    void setTimer(Timer timer);

    default boolean isActive() {
        Session session = getSession();
        return session.isActive();
    }

    void refreshLastOperateTime();
}
