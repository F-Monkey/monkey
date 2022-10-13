package cn.monkey.state.supported.data;

import java.util.List;

public class StateConfig {

    private String code;

    private Event initEvent;

    private Event updateEvent;

    private Event finishEvent;

    private List<Event> eventList;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Event getInitEvent() {
        return initEvent;
    }

    public void setInitEvent(Event initEvent) {
        this.initEvent = initEvent;
    }

    public Event getUpdateEvent() {
        return updateEvent;
    }

    public void setUpdateEvent(Event updateEvent) {
        this.updateEvent = updateEvent;
    }

    public Event getFinishEvent() {
        return finishEvent;
    }

    public void setFinishEvent(Event finishEvent) {
        this.finishEvent = finishEvent;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
