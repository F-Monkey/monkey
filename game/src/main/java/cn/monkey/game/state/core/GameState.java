package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.Player;
import cn.monkey.game.data.UserCmdPair;
import cn.monkey.game.utils.GameCmdUtil;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import cn.monkey.server.supported.user.User;
import cn.monkey.state.core.OncePerInitState;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ProtocolStringList;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public abstract class GameState extends OncePerInitState {

    public GameState(String code, Timer timer, GameStateGroup stateGroup) {
        super(code, timer, stateGroup);
    }

    @Override
    public void fireEvent(Object event) throws Exception {
        UserCmdPair playerCmdPair = (UserCmdPair) event;
        Command.Package pkg = playerCmdPair.getPkg();
        User user = playerCmdPair.getUser();
        int cmdType = pkg.getCmdType();
        switch (cmdType) {
            case GameCmdType.CREATE:
                this.create(user, pkg);
                return;
            case GameCmdType.ENTER:
                this.enter(user, pkg);
                return;
            default:
                GameStateContext stateContext = this.getStateContext();
                Player player = stateContext.getPlayer(user.getUid());
                if (player == null) {
                    throw new UnsupportedOperationException("bad command: " + cmdType);
                }
                if (cmdType == GameCmdType.CHAT) {
                    this.chat(player, pkg);
                    return;
                }
                this.handleCmd(player, pkg);
        }
    }

    private void chat(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        ByteString content = pkg.getContent();
        Game.ChatMsg chatMsg = Game.ChatMsg.parseFrom(content);
        GameStateContext stateContext = this.getStateContext();
        Collection<Player> players = stateContext.getPlayers();
        ProtocolStringList targetList = chatMsg.getTargetList();
        Command.PackageGroup packageGroup = GameCmdUtil.buildChatResult(player.getUid(), chatMsg);
        for (Player p : players) {
            if (!CollectionUtils.isEmpty(targetList)) {
                for (String target : targetList) {
                    if (p.getUid().equals(target)) {
                        p.write(packageGroup);
                    }
                }
            } else {
                p.write(packageGroup);
            }
        }
    }

    protected void create(User user, Command.Package pkg) throws InvalidProtocolBufferException {
        if (this.enter(user, pkg)) {
            GameStateContext stateContext = this.getStateContext();
            stateContext.setMaster(user.getUid());
        }
    }

    @Override
    public void fireEventOnError(Object event, Exception e) {
        UserCmdPair userCmdPair = (UserCmdPair) event;
        User user = userCmdPair.getUser();
        if (e instanceof InvalidProtocolBufferException) {
            user.write(GameCmdUtil.error(new IllegalArgumentException("bad package")));
            return;
        }
        if (e instanceof UnsupportedOperationException) {
            user.write(GameCmdUtil.error(e));
            return;
        }
        user.write(GameCmdUtil.error(e));
    }

    @Override
    protected void onInit() {
        // do nothing here
    }


    @Override
    public GameStateGroup getStateGroup() {
        return (GameStateGroup) super.getStateGroup();
    }

    @Override
    public GameStateContext getStateContext() {
        return this.getStateGroup().getStateContext();
    }

    protected boolean enter(User user, Command.Package pkg) throws InvalidProtocolBufferException {
        GameStateContext stateContext = this.getStateContext();
        String password = stateContext.getPassword();
        if (stateContext.isFull()) {
            user.write(GameCmdUtil.enterFail("room is full"));
            return false;
        }
        if (!Strings.isNullOrEmpty(password)) {
            Game.Enter enter = Game.Enter.parseFrom(pkg.getContent());
            String enterPassword = enter.getPassword();
            if (!enterPassword.equals(password)) {
                log.error("invalid password enter");
                user.write(GameCmdUtil.enterFail("bad password"));
                return false;
            }
        }
        stateContext.addPlayer(user);
        user.write(GameCmdUtil.enterSuccess(stateContext));
        return true;
    }

    protected abstract void handleCmd(Player user, Command.Package pkg) throws InvalidProtocolBufferException;

    /**
     * @param exclude
     */
    protected void broadCastGameData(int cmdType, Player exclude) {
        GameStateContext stateContext = this.getStateContext();
        Collection<Player> players = stateContext.getPlayers();
        for (Player p : players) {
            if (exclude != null && exclude.getUid().equals(p.getUid())) {
                continue;
            }
            p.write(GameCmdUtil.buildGameStateContextData(cmdType, stateContext, p));
        }
    }
}
