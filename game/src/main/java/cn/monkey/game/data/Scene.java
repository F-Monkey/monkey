package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Scene implements Serializable {
    private String name;
    private String img;
    private String desc;
    private List<Item> items = new ArrayList<>();


    public void returnItem(Item currentItem) {
        this.items.add(currentItem);
    }
}
