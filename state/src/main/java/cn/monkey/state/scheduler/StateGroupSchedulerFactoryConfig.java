package cn.monkey.state.scheduler;

public class StateGroupSchedulerFactoryConfig {
    private static final int DEFAULT_MAX_SIZE = 50;
    private static final long DEFAULT_UPDATE_FREQUENCY = 50L;

    private int maxSize = DEFAULT_MAX_SIZE;
    private long updateFrequency = DEFAULT_UPDATE_FREQUENCY;


    public int getMaxSize() {
        return maxSize;
    }

    public long getUpdateFrequency() {
        return updateFrequency;
    }

    private StateGroupSchedulerFactoryConfig() {
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final StateGroupSchedulerFactoryConfig config;

        Builder() {
            this.config = new StateGroupSchedulerFactoryConfig();
        }

        public Builder maxSize(int maxSize) {
            this.config.maxSize = maxSize;
            return this;
        }

        public Builder updateFrequency(long updateFrequency) {
            this.config.updateFrequency = updateFrequency;
            return this;
        }

        public StateGroupSchedulerFactoryConfig build() {
            return this.config;
        }
    }
}
