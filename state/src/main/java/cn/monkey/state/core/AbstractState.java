package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class AbstractState implements State {

    private final String code;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final Timer timer;

    protected final StateGroup stateGroup;

    public AbstractState(String code,
                         Timer timer,
                         StateGroup stateGroup) {
        Objects.requireNonNull(code, "code can not be null");
        Objects.requireNonNull(timer, "timer can not be null");
        Objects.requireNonNull(stateGroup, "stateGroup can not be null");
        this.code = code;
        this.timer = timer;
        this.stateGroup = stateGroup;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public StateGroup getStateGroup() {
        return this.stateGroup;
    }

    @Override
    public void initOnError(Exception e) {
        log.error("stateGroup: {} state: {} init error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public void fireEvent(Object event) throws Exception {

    }

    @Override
    public void fireEventOnError(Object event, Exception e) {
        log.error("stateGroup: {} state: {} fireEvent error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public void update(StateInfo stateInfo) throws Exception {

    }

    @Override
    public void updateOnError(StateInfo stateInfo, Exception e) {
        log.error("stateGroup: {} state: {} update error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
    }

    @Override
    public String finishOnError(Exception e) {
        log.error("stateGroup: {} state: {} finish error on time: {} \n", this.getStateGroup().id(), this.code(), this.timer.getCurrentTimeMs(), e);
        return null;
    }
}
