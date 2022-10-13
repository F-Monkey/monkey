package cn.monkey.game.repository;

import cn.monkey.game.data.GameConfig;

public interface GameConfigRepository {
    GameConfig findById(String configId);
}
