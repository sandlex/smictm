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
    private static String path;

    public static void main(String[] arg) {

        if (arg.length > 0) {
            path = arg[0];
        } else {
            path = "tasks";
        }

        boolean isPathOk = true;
        File dir = new File(path);
        if (!dir.exists()) {
            isPathOk = dir.mkdir();
        }

        File dirClosed = new File(path + File.separator + "closed");
        if (!dirClosed.exists()) {
            isPathOk = dirClosed.mkdir();
        }

        if (!isPathOk) {
            System.out.println("Directory '" + path + "' cannot be created in current directory.");
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

        frame = new JFrame("smictm 0.2 - " + path);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,700));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.add(taskPanel, JSplitPane.LEFT);
        splitPane.add(calendarPanel, JSplitPane.RIGHT);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }

        frame.pack();
        frame.setVisible(true);
    }
}
