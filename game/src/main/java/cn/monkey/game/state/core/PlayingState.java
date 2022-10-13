package cn.monkey.game.state.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.game.data.GameConfig;
import cn.monkey.game.data.Item;
import cn.monkey.game.data.Player;
import cn.monkey.game.data.Scene;
import cn.monkey.game.utils.GameCmdType;
import cn.monkey.game.utils.GameCmdUtil;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public class PlayingState extends GameState {

    public static final String CODE = "playing";

    public PlayingState(Timer timer, GameStateGroup stateGroup) {
        super(CODE, timer, stateGroup);
    }

    @Override
    protected void onInit() {
        GameStateContext stateContext = this.getStateContext();
        GameConfig gameConfig = stateContext.getGameConfig();
        Scene mainScene = gameConfig.getMainScene();
        for (Player player : stateContext.getPlayers()) {
            player.setScene(mainScene);
        }
    }

    @Override
    protected void handleCmd(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        int cmdType = pkg.getCmdType();
        switch (cmdType) {
            case GameCmdType.ENTER_SCENE:
                this.enterScene(player, pkg);
                return;
            case GameCmdType.EXIT_SCENE:
                this.exitScene(player, pkg);
                return;
            case GameCmdType.SEARCH_ITEM:
                this.searchItem(player, pkg);
                return;
            case GameCmdType.RETURN_ITEM:
                this.returnItem(player, pkg);
                return;
            case GameCmdType.ADD_ITEM:
                this.addItem(player, pkg);
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void addItem(Player player, Command.Package pkg) {
        boolean b = player.tryAddItem();
        GameStateContext stateContext = this.getStateContext();
        if (b) {
            player.write(GameCmdUtil.buildAddItemResult(true, stateContext));
            return;
        }
        Scene scene = player.getScene();
        scene.returnItem(player.getCurrentItem());
        player.write(GameCmdUtil.buildAddItemResult(false, stateContext));
    }

    private void returnItem(Player player, Command.Package pkg) {
        Scene scene = player.getScene();
        if (scene != null) {
            scene.returnItem(player.getCurrentItem());
        }
        GameStateContext stateContext = this.getStateContext();
        player.write(GameCmdUtil.buildReturnItemResult(stateContext));
    }

    private void exitScene(Player player, Command.Package pkg) {
        GameStateContext stateContext = this.getStateContext();
        Scene scene = player.getScene();
        Command.PackageGroup packageGroup = GameCmdUtil.buildGameStateContextData(GameCmdType.EXIT_SCENE_RESULT, stateContext, player);
        player.write(packageGroup);
        GameConfig gameConfig = stateContext.getGameConfig();
        Scene mainScene = gameConfig.getMainScene();
        player.setScene(mainScene);
        for (Player p : stateContext.getPlayers()) {
            if (scene.equals(p.getScene())) {
                p.write(GameCmdUtil.msg("player: " + player.getUid() + " is exists from scene: " + scene));
            } else if (mainScene.getName().equals(p.getScene().getName())) {
                p.write(GameCmdUtil.msg("player: " + player.getUid() + " is enter into scene: " + scene));
            }
        }
    }

    private void searchItem(Player player, Command.Package pkg) {
        Scene scene = player.getScene();
        if (scene == null) {
            return;
        }
        List<Item> items = scene.getItems();
        if (items.size() == 0) {
            player.write(GameCmdUtil.searchItemResult(null));
            return;
        }
        Item remove = items.remove((int) (Math.random() * items.size()));
        player.setCurrentItem(remove);
        player.write(GameCmdUtil.searchItemResult(remove));
    }

    private void enterScene(Player player, Command.Package pkg) throws InvalidProtocolBufferException {
        Game.EnterScene enterScene = Game.EnterScene.parseFrom(pkg.getContent());
        String name = enterScene.getName();
        Scene currentScene = player.getScene();
        GameStateContext stateContext = this.getStateContext();
        Scene scene = stateContext.getScene(name);
        if (scene == null) {
            log.error("can not find scene by name: " + name);
            player.write(GameCmdUtil.buildEnterSceneResult(ResultCode.FAIL, "can not find scene by name: " + name, null));
            return;
        }
        player.setScene(scene);
        for (Player p : getStateContext().getPlayers()) {
            Scene otherScene = p.getScene();
            if (currentScene.getName().equals(otherScene.getName())) {
                p.write(GameCmdUtil.buildPlayerExistSceneMsg("role: " + player.getRole().getName() + " exist", stateContext));
                continue;
            }
            if (scene.getName().equals(otherScene.getName())) {
                p.write(GameCmdUtil.buildPlayerEnterSceneMsg("role: " + player.getRole().getName() + "enter", stateContext));
            }
        }
    }

    @Override
    public String finish() throws Exception {
        return DiscussionState.CODE;
    }
}
