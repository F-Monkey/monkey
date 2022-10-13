package cn.monkey.state.core;

public interface StateGroupFactory {
    StateGroup create(String id, Object ...args);
}
