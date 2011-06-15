package com.sandlex.smictm.model;

import java.util.Vector;

/**
 * @author Alexey Peskov
 */
public enum State {

    New("New"), InProgress("In progress"), OnHold("On hold"), Completed("Completed"), Cancelled("Cancelled");

    String name;
    State[] nextStates;

    State(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static State fromValue(String v) {
        for (State state : State.values()) {
            if (state.name.equals(v)) {
                return state;
            }
        }
        
        throw new IllegalArgumentException(v);
    }



    public Vector<State> getNextStates() {

        Vector<State> nextStates = new Vector<State>();

        switch (this) {
            case New:
            	nextStates.add(New);
                nextStates.add(InProgress);
                nextStates.add(Cancelled);
                break;
            case InProgress:
            	nextStates.add(InProgress);
                nextStates.add(Completed);
                nextStates.add(OnHold);
                nextStates.add(Cancelled);
                break;
            case OnHold:
            	nextStates.add(OnHold);
                nextStates.add(InProgress);
                nextStates.add(Completed);
                nextStates.add(Cancelled);
                break;
            case Completed:
                break;
            case Cancelled:
                break;
        }

        return nextStates;
    }
}
