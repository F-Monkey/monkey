package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.game.data.Player;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.game.utils.GameCmdUtil;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import com.google.protobuf.InvalidProtocolBufferException;

public class DiscussionState extends GameState {

    public static final String CODE = "discussion";

    public DiscussionState(Timer timer, GameStateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void onInit() {
        //
    }

    @Override
    protected void handleCmd(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        int cmdType = pkg.getCmdType();
        switch (cmdType) {
            case GameCmdType.DRAW:
                this.draw(player, pkg);
                return;
            case GameCmdType.REMOVE_DRAWER:
                this.removeDrawer(player, pkg);
                return;
        }
    }

    private void removeDrawer(Player player, Command.Package pkg) {
        GameStateContext stateContext = getStateContext();
        String master = stateContext.getMaster();
        if (!player.getUid().equals(master)) {
            player.write(GameCmdUtil.drawResult(ResultCode.FAIL, "you are not allowed", null));
            return;
        }
        stateContext.setCurrentCanvasEditor(null);
        this.broadCastGameData(GameCmdType.REMOVE_DRAWER, null);
    }

    private void draw(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        GameStateContext stateContext = this.getStateContext();
        String currentCanvasEditor = stateContext.getCurrentCanvasEditor();
        String uid = player.getUid();
        Game.Content content = Game.Content.parseFrom(pkg.getContent());
        stateContext.setCanvas(content);
        if (currentCanvasEditor == null) {
            stateContext.setCurrentCanvasEditor(uid);
        } else if (!currentCanvasEditor.equals(uid)) {
            player.write(GameCmdUtil.drawResult(ResultCode.FAIL, "you can not draw", null));
            return;
        }
        this.broadCastGameData(GameCmdType.DRAW_RESULT, null);
    }

    @Override
    public String finish() throws Exception {
        return EndState.CODE;
    }
}
