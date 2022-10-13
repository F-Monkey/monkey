package cn.monkey.game.data;

import cn.monkey.proto.Command;
import cn.monkey.server.supported.user.User;

public class Player {
    private User user;
    private boolean isReady;
    private Scene scene;
    private Role role;

    private int itemIndex = 0;
    private final Item[] items;
    private Item currentItem;

    public Player(User user) {
        this.user = user;
        this.isReady = true;
        this.items = new Item[6];
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public String getUid() {
        return this.user.getUid();
    }

    public void write(Command.PackageGroup packageGroup) {
        this.user.write(packageGroup);
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }

    public Scene getScene() {
        return scene;
    }

    public Item getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public Item[] getItems() {
        return items;
    }

    public boolean tryAddItem() {
        if (this.itemIndex >= this.items.length) {
            return false;
        }
        this.items[++itemIndex] = this.currentItem;
        return true;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
