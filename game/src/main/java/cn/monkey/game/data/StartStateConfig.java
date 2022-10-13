package cn.monkey.game.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class StartStateConfig implements Serializable {
    public static final long DEFAULT_MAX_COUNT_DOWN_TS = 30 * 10;

    private long maxCountDownTs = DEFAULT_MAX_COUNT_DOWN_TS;
}
