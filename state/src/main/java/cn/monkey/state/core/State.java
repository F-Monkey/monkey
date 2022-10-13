package cn.monkey.state.core;

public interface State {

    String code();

    StateGroup getStateGroup();

    default StateContext getStateContext() {
        return this.getStateGroup().getStateContext();
    }

    void init() throws Exception;

    void initOnError(Exception e);

    void fireEvent(Object event) throws Exception;

    void fireEventOnError(Object event, Exception e);

    void update(StateInfo stateInfo) throws Exception;

    void updateOnError(StateInfo stateInfo, Exception e);

    String finish() throws Exception;

    String finishOnError(Exception e);
}
