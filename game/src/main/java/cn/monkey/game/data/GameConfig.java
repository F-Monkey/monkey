package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GameConfig implements Serializable {

    private String id;

    private String title;

    private WaitingStateConfig waitingStateConfig;

    private StartStateConfig startStateConfig;

    private List<Role> roles;

    private Scene mainScene;

    private Boolean needDM;

    private List<Scene> scenes;
}
