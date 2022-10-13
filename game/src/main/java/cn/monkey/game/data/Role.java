package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private String name;
    private String briefIntroduction;
    private String desc;
    private String img;
}
