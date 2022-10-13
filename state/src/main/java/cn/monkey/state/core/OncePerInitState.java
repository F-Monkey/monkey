package cn.monkey.state.core;

import cn.monkey.commons.utils.Timer;

public abstract class OncePerInitState extends AbstractState {

    protected boolean hasInit = false;

    public OncePerInitState(String code, Timer timer, StateGroup stateGroup) {
        super(code, timer, stateGroup);
    }


    @Override
    public final void init() throws Exception {
        if (this.hasInit) {
            return;
        }
        this.onInit();
        this.hasInit = true;
    }

    protected abstract void onInit();
}
