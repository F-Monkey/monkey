package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.data.GameConfig;
import cn.monkey.game.data.Player;
import cn.monkey.proto.Command;

public class EndState extends GameState {

    public static final String CODE = "end";

    public EndState(Timer timer, GameStateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void onInit() {
        GameStateContext stateContext = this.getStateContext();
        GameConfig gameConfig = stateContext.getGameConfig();
    }

    @Override
    protected void handleCmd(Player player, Command.Package pkg) {
        // DM发言
        // 玩家之间打分
    }

    @Override
    public String finish() throws Exception {
        return null;
    }
}
