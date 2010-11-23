package com.sandlex.smictm;

import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.view.CalendarViewPanel;
import com.sandlex.smictm.view.TaskViewPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Alexey Peskov
 */
public class Smictm
{

    private Model model;
    private TaskViewPanel taskPanel;
    private CalendarViewPanel calendarPanel;
    private JFrame frame;

    public static void main(String[] arg) {

        String path = "tasks";

        if (arg.length == 1) {
            path = arg[0];
        }

        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Directory " + path + " doesn't exist.");
            System.exit(-1);
        }

        File dirClosed = new File(path + File.separator + "closed");
        if (!dirClosed.exists()) {
            System.out.println("Directory " + path + File.separator + "closed" + " doesn't exists.");
            System.exit(-1);
        }

        new Smictm(path);
    }

    private Smictm(String path) {
        model = new Model(path);
        taskPanel = new TaskViewPanel(model);
        calendarPanel = new CalendarViewPanel(model);

        model.addObserver(taskPanel);
        model.addObserver(calendarPanel);
        
        initView();
    }

    private void initView() {

        frame = new JFrame("smictm");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,700));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.add(taskPanel, JSplitPane.LEFT);
        splitPane.add(calendarPanel, JSplitPane.RIGHT);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
