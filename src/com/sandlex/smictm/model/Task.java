package com.sandlex.smictm.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexey Peskov
 */
public class Task {

    private String name;
    private List<TaskEvent> activities = new ArrayList<TaskEvent>();

    public Task(String name) {
        this.name = name;
        activities.add(new StateChange(State.New));   
    }

    public void addActivity(Activity activity) {
        activities.add(new DatedActivity(activity));
    }

    public int getActivitiesNumber() {
        return activities.size();
    }

    public List<TaskEvent> getActivities() {
        return activities;
    }

    @Override
    public String toString() {

        StateChange stChange = null;

        for (int i = activities.size() - 1; i > -1; i--) {
            if (activities.get(i) instanceof StateChange) {
                stChange = (StateChange) activities.get(i);
                break;
            }
        }

        return stChange + ": " + name;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        int pos = name.indexOf("\n");
        if (pos != -1) {
            return name.substring(0, pos);
        } else {
            return name.substring(0, name.length());
        }
    }

    public State getState() {
        StateChange stChange;

        for (int i = activities.size() - 1; i > -1; i--) {
            if (activities.get(i) instanceof StateChange) {
                stChange = (StateChange) activities.get(i);
                return stChange.getState();
            }
        }

        throw new IllegalStateException();
    }

    public void addState(String state) {
        activities.add(new StateChange(State.fromValue(state)));
    }

    public void addState(State state) {
        activities.add(new StateChange(state));
    }

    public boolean isActivityNew(Activity checkedActivity) {

        boolean isNew = true;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        for (TaskEvent activity : activities) {
            if (df.format(activity.getDate()).equals(df.format(new Date(System.currentTimeMillis())))
                    && activity.getName().equals(checkedActivity.name())) {
                isNew = false;
                break;
            }
        }

        return isNew;
    }
}
