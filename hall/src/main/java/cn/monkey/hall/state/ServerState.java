package cn.monkey.hall.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.pojo.ServerConfig;
import cn.monkey.hall.utils.HallCmdUtil;
import cn.monkey.proto.Command;
import cn.monkey.server.supported.user.User;
import cn.monkey.state.core.OncePerInitState;
import cn.monkey.state.core.StateGroup;
import cn.monkey.state.core.StateInfo;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Queue;

public class ServerState extends OncePerInitState {

    public static final String CODE = "EMPTY";

    public ServerState(Timer timer, StateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void onInit() {

    }


    @Override
    @SuppressWarnings("unchecked")
    public void fireEvent(Object event) throws Exception {
        Tuple2<User, Command.Package> pair = (Tuple2<User, Command.Package>) event;
        HallServerContext stateContext = getStateContext();
        User user = pair.getT1();
        stateContext.add(user);
    }

    @Override
    public HallServerContext getStateContext() {
        return (HallServerContext) super.getStateContext();
    }

    @Override
    public void update(StateInfo stateInfo) throws Exception {
        HallServerContext stateContext = this.getStateContext();
        List<ServerConfig> serverConfigs = stateContext.serverConfigs(this.stateGroup.id());
        Queue<User> users = stateContext.getUsers();
        for (ServerConfig serverConfig : serverConfigs) {
            int currentUserSize = serverConfig.getCurrentUserSize();
            int maxUserSize = serverConfig.getMaxUserSize();
            if (currentUserSize >= maxUserSize) {
                continue;
            }
            String url = serverConfig.getUrl();
            for (int i = 0; i < maxUserSize - currentUserSize; ) {
                User u = users.poll();
                if (u == null) {
                    return;
                }
                if (!u.isActive()) {
                    continue;
                }
                u.write(HallCmdUtil.chooseRoomResult(url));
                i++;
            }
        }
        if (users.size() > 0) {
            int i = 0;
            for (User u : users) {
                u.write(HallCmdUtil.chooseRoomResult(i));
                i++;
            }
        }
    }

    @Override
    public String code() {
        return CODE;
    }

    @Override
    public String finish() throws Exception {
        return null;
    }
}
