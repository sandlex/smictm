package com.sandlex.smictm.model;

/**
 * @author Alexey Peskov
 */
public class TaskEventBean {

    private Task task;
    private String stateChanges;

    public TaskEventBean(Task task, String stateChanges) {
        this.task = task;
        this.stateChanges = stateChanges;
    }

    public Task getTask() {
        return task;
    }

    public String getStateChanges() {
        return stateChanges;
    }
}
