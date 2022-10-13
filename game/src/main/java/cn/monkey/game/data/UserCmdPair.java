package cn.monkey.game.data;

import cn.monkey.proto.Command;
import cn.monkey.server.supported.user.User;
import com.google.common.base.Preconditions;

public class UserCmdPair {

    private final User user;
    private final Command.Package pkg;

    public UserCmdPair(User user, Command.Package pkg) {
        Preconditions.checkNotNull(pkg);
        this.user = user;
        this.pkg = pkg;
    }

    public Command.Package getPkg() {
        return pkg;
    }

    public User getUser() {
        return this.user;
    }
}
