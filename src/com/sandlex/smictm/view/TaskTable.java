package com.sandlex.smictm.view;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Alexey Peskov
 */
public abstract class TaskTable extends JTable implements KeyListener {

    protected TaskTable(TableModel dm) {
        super(dm);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("pressed");
        if (e.getKeyCode() == 67/* && e.isControlDown()*/)  {
            System.out.println("Copy selected tasks to clipboard");
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
