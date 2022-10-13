package cn.monkey.state.core;

import java.io.Closeable;
import java.io.Flushable;

public interface StateGroup extends Closeable, Flushable {

    String id();

    void addState(State state);

    void setStartState(String stateCode);

    StateContext getStateContext();

    void addEvent(Object event);

    void update();

    boolean canClose();

    void close();

    void flush();
}
