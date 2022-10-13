package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class Item implements Serializable {
    private String name;
    private String desc;
    private String img;
}
