package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.GameConfig;
import cn.monkey.game.data.Player;
import cn.monkey.game.data.Role;
import cn.monkey.game.data.Scene;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import cn.monkey.state.core.StateInfo;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public class StartState extends GameState {

    public static final String CODE = "start";

    private long initTime;

    public StartState(Timer timer, GameStateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void onInit() {
        this.initTime = super.timer.getCurrentTimeMs();
        GameStateContext stateContext = getStateContext();
        GameConfig gameConfig = stateContext.getGameConfig();
        Scene mainScene = gameConfig.getMainScene();
        List<Role> roles = gameConfig.getRoles();
        int i = 0;
        for (Player player : stateContext.getPlayers()) {
            player.setScene(mainScene);
            player.setRole(roles.get(i));
            i++;
        }
        super.broadCastGameData(0, null);
    }

    @Override
    protected void handleCmd(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        int cmdType = pkg.getCmdType();
        switch (cmdType) {
            case GameCmdType.APPLY_SWITCH_ROLE:
                this.applySwitchRole(player, pkg);
                return;
            case GameCmdType.AGREE_SWITCH_ROLE:
                this.agreeSwitchRole(player, pkg);
        }
    }

    private void agreeSwitchRole(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        ByteString content = pkg.getContent();
        Game.AgreeSwitchRole agreeSwitchRole = Game.AgreeSwitchRole.parseFrom(content);
        String target = agreeSwitchRole.getTarget();
        GameStateContext stateContext = this.getStateContext();
        Player targetPlayer = stateContext.getPlayer(target);
        Role role = player.getRole();
        Role targetPlayerRole = targetPlayer.getRole();
        player.setRole(targetPlayerRole);
        targetPlayer.setRole(role);
        this.broadCastGameData(0, null);
    }

    private void applySwitchRole(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        ByteString content = pkg.getContent();
        Game.ApplySwitchRole applySwitchRole = Game.ApplySwitchRole.parseFrom(content);
        String target = applySwitchRole.getTarget();
        GameStateContext stateContext = this.getStateContext();
        Player targetPlayer = stateContext.getPlayer(target);
        // TODO
    }

    @Override
    public void update(StateInfo stateInfo) throws Exception {
        GameStateContext stateContext = this.getStateContext();
        if (super.timer.getCurrentTimeMs() - this.initTime >= stateContext.getStartStateCountDownTs()) {
            stateInfo.isFinish = true;
        }
    }

    @Override
    public String finish() throws Exception {
        return PlayingState.CODE;
    }
}
