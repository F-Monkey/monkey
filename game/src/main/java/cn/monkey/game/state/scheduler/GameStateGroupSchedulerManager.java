package cn.monkey.game.state.scheduler;

import cn.monkey.game.data.UserCmdPair;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.game.utils.GameCmdUtil;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import cn.monkey.server.supported.user.User;
import cn.monkey.state.core.StateGroupPool;
import cn.monkey.state.scheduler.*;
import com.google.protobuf.InvalidProtocolBufferException;

public class GameStateGroupSchedulerManager extends SimpleSchedulerManager {

    protected final EventPublishScheduler createEventScheduler;

    public GameStateGroupSchedulerManager(StateGroupPool stateGroupPool, StateGroupSchedulerFactory stateGroupSchedulerFactory, EventPublishSchedulerFactory eventPublishSchedulerFactory, SchedulerManagerConfig managerConfig) {
        super(stateGroupPool, stateGroupSchedulerFactory, eventPublishSchedulerFactory, managerConfig);
        this.createEventScheduler = eventPublishSchedulerFactory.create(1L);
    }

    @Override
    public void addEvent(String groupId, Object event) {
        UserCmdPair userCmdPair = (UserCmdPair) event;
        Command.Package pkg = userCmdPair.getPkg();
        int cmdType = pkg.getCmdType();
        if (cmdType == GameCmdType.CREATE) {
            User user = userCmdPair.getUser();
            Game.Create create;
            try {
                create = Game.Create.parseFrom(userCmdPair.getPkg().getContent());
            } catch (InvalidProtocolBufferException e) {
                user.write(GameCmdUtil.error(e));
                return;
            }
            String id = create.getId();
            this.createEventScheduler.publish(() -> super.findBestGroupScheduler2AddStateGroup(groupId, userCmdPair, id));
            return;
        }
        super.addEvent(groupId, userCmdPair);
    }
}
