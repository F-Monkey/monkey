package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class DMInfo implements Serializable {
    private String uid;
    private String username;
}
