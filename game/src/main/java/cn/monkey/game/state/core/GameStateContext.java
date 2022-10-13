package cn.monkey.game.state.core;

import cn.monkey.game.data.*;
import cn.monkey.game.event.HallServerBroadCastHandler;
import cn.monkey.proto.Game;
import cn.monkey.server.supported.user.User;
import cn.monkey.state.core.StateContext;

import java.util.*;

public class GameStateContext implements StateContext {

    private final HallServerBroadCastHandler broadCastHandler;

    private final Map<String, Player> players;

    private final Map<String, Scene> scenes;

    private final GameConfig gameConfig;
    private String master;

    private String currentCanvasEditor;

    private Game.Content canvas;

    private Player dm;

    private final List<DMInfo> applyDmList;

    private String password;

    public GameStateContext(GameConfig gameConfig,
                            HallServerBroadCastHandler broadCastHandler) {
        this.players = new HashMap<>(4);
        this.scenes = this.initScenes(gameConfig);
        this.applyDmList = new ArrayList<>();
        this.gameConfig = gameConfig;
        this.broadCastHandler = broadCastHandler;
    }

    private Map<String, Scene> initScenes(GameConfig gameConfig) {
        HashMap<String, Scene> scenes = new HashMap<>();
        for (Scene scene : gameConfig.getScenes()) {
            scenes.put(scene.getName(), scene);
        }
        return scenes;
    }

    /**
     * @param user
     * @return true: new false: old
     */
    public boolean addPlayer(User user) {
        String uid = user.getUid();
        if (this.players.containsKey(uid)) {
            Player player = this.players.get(uid);
            player.setUser(user);
            return false;
        }
        this.players.put(uid, new Player(user));
        return true;
    }

    public Scene getScene(String name) {
        return this.scenes.get(name);
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getMaster() {
        return master;
    }

    public Collection<Player> getPlayers() {
        return this.players.values();
    }

    public Player getPlayer(String uid) {
        return this.players.get(uid);
    }

    public void removePlayer(String uid) {
        this.players.remove(uid);
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return this.gameConfig.getRoles();
    }

    public long getWaitingStateCountDownTs() {
        return this.gameConfig.getWaitingStateConfig().getMaxCountDownTs();
    }

    public long getStartStateCountDownTs() {
        return this.gameConfig.getStartStateConfig().getMaxCountDownTs();
    }

    public void addApplyDm(DMInfo dmInfo) {
        this.applyDmList.add(dmInfo);
    }

    public List<DMInfo> getApplyDmList() {
        return this.applyDmList;
    }

    public Player getDm() {
        return dm;
    }

    public void setDm(Player dm) {
        this.dm = dm;
    }

    public void setCurrentCanvasEditor(String currentCanvasEditor) {
        this.currentCanvasEditor = currentCanvasEditor;
    }

    public String getCurrentCanvasEditor() {
        return currentCanvasEditor;
    }

    public void setCanvas(Game.Content canvas) {
        this.canvas = canvas;
    }

    public Game.Content getCanvas() {
        return this.canvas;
    }

    public boolean isFull() {
        return this.players.size() >= this.gameConfig.getRoles().size() && Boolean.TRUE.equals(this.gameConfig.getNeedDM()) == (this.dm != null);
    }

    public GameConfig getGameConfig() {
        return this.gameConfig;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addServerBroadCastEvent(Object event) {
        this.broadCastHandler.accept(event);
    }
}
