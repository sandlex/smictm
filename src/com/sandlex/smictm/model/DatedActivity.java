package com.sandlex.smictm.model;

import java.util.Date;

/**
 * @author Alexey Peskov
 */
public class DatedActivity implements TaskEvent {

    private Activity activity;
    private Date date;

    public DatedActivity(Activity activity) {
        this.activity = activity;
        this.date = new Date(System.currentTimeMillis());
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return activity.name();
    }
}
