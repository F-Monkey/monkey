package cn.monkey.gateway.util;

import cn.monkey.commons.data.pojo.UserSession;
import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.proto.Command;
import cn.monkey.proto.User;
import com.google.common.base.Strings;

public abstract class CommandUtil {

    static User.UserInfo copy(cn.monkey.gateway.data.User user) {
        return User.UserInfo.newBuilder()
                .setUid(user.getId())
                .setUsername(user.getUsername())
                .build();
    }


    public static Command.PackageGroup login(cn.monkey.gateway.data.User user, UserSession userSession) {
        User.Session.Builder builder = User.Session.newBuilder()
                .setUser(copy(user));
        String hallServerUrl = userSession.getHallServerUrl();
        if (Strings.isNullOrEmpty(hallServerUrl)) {
            builder.setHallServer(hallServerUrl);
        }
        String gameServerUrl = userSession.getGameServerUrl();
        if (Strings.isNullOrEmpty(gameServerUrl)) {
            builder.setRoomServer(gameServerUrl);
        }
        return cn.monkey.proto.CommandUtil.packageGroup(
                cn.monkey.proto.CommandUtil.pkg(ResultCode.OK, null, null,
                        builder.build().toByteString()));
    }

    public static Command.PackageGroup loginFail(String msg) {
        return cn.monkey.proto.CommandUtil.fail(ResultCode.FAIL, msg);
    }
}
