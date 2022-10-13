package cn.monkey.game.event;

import java.util.function.Consumer;

public interface EventHandler<E> extends Consumer<E> {
}
