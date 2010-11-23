package com.sandlex.smictm.model;

/**
 * @author Alexey Peskov
 */
public class TaskEventBean {

    private Task task;
    private TaskEvent taskEvent;

    public TaskEventBean(Task task, TaskEvent taskEvent) {
        this.task = task;
        this.taskEvent = taskEvent;
    }

    public Task getTask() {
        return task;
    }

    public TaskEvent getTaskEvent() {
        return taskEvent;
    }
}
