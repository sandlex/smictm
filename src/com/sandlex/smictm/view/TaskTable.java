package com.sandlex.smictm.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Alexey Peskov
 */
public abstract class TaskTable extends JTable implements KeyListener {

    private class TooltippedRowRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setToolTipText("<html>" + getToolTip(row).replaceAll("\n", "<br>") + "</html>");
            return this; 
        }
    }

    protected TaskTable(TableModel dm) {
        super(dm);
        TableColumnModel colModel = getColumnModel();
        for (int j = 0; j < colModel.getColumnCount(); j++) {
            if (AbstractPanel.TASK_COL_NAME.equals(colModel.getColumn(j).getHeaderValue())) {
                colModel.getColumn(j).setCellRenderer(new TooltippedRowRenderer());
                break;
            }
        }
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

    protected abstract String getToolTip(int row);
}
