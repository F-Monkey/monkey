package cn.monkey.state.core;

public interface StateGroupPool {
    class FetchStateGroup {
        private final boolean isNew;
        private final StateGroup stateGroup;

        public FetchStateGroup(boolean isNew, StateGroup stateGroup) {
            this.isNew = isNew;
            this.stateGroup = stateGroup;
        }

        public boolean isNew() {
            return isNew;
        }

        public StateGroup getStateGroup() {
            return stateGroup;
        }
    }

    FetchStateGroup findOrCreate(String id, Object... args);
}
