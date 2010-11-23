package com.sandlex.smictm.model;

import java.util.Date;

/**
 * @author Alexey Peskov
 */
public class StateChange implements TaskEvent {

    private State state;
    private Date date;

    public StateChange(State state) {
        this.state = state;
        this.date = new Date(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public State getState() {
        return state;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return state.name();
    }
}
