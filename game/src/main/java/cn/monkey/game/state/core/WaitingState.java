package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.Player;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.proto.Command;
import cn.monkey.state.core.StateInfo;

import java.util.Collection;

public class WaitingState extends GameState {

    public static final String CODE = "waiting";

    private Long startCountDownTime;

    public WaitingState(Timer timer, GameStateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void handleCmd(Player player, Command.Package pkg) {
        int cmdType = pkg.getCmdType();
        switch (cmdType) {
            case GameCmdType.READY:
                this.ready(player, pkg);
                return;
            default:
                throw new UnsupportedOperationException("invalid cmdType:" + cmdType);
        }
    }

    private void ready(Player player, Command.Package pkg) {
        player.setReady(true);
        super.broadCastGameData(GameCmdType.READY_RESULT, player);
    }

    @Override
    public void update(StateInfo stateInfo) throws Exception {
        GameStateContext stateContext = getStateContext();
        if (!stateContext.isFull()) {
            return;
        }
        if (this.startCountDownTime == null) {
            this.startCountDownTime = super.timer.getCurrentTimeMs();
        }
        Collection<Player> players = stateContext.getPlayers();
        for (Player player : players) {
            if (!player.isReady()) {
                if (super.timer.getCurrentTimeMs() - this.startCountDownTime >= stateContext.getWaitingStateCountDownTs()) {
                    this.removeUnReadyPlayer();
                    this.startCountDownTime = null;
                }
                return;
            }
        }
        stateInfo.isFinish = true;
    }

    private void removeUnReadyPlayer() {
        GameStateContext stateContext = this.getStateContext();
        Collection<Player> players = stateContext.getPlayers();
        for (Player p : players) {
            if (!p.isReady()) {
                stateContext.removePlayer(p.getUid());
            }
        }
    }

    @Override
    public String finish() throws Exception {
        return StartState.CODE;
    }
}
