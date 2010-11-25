package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.model.TaskEventBean;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Observable;

/**
 * @author Alexey Peskov
 */
public class CalendarViewPanel extends AbstractPanel implements PropertyChangeListener {

    private JTable table;
    private JCalendar calendar;

    public CalendarViewPanel(Model model) {
        super(model);
    }

    @Override
    protected void initComponents() {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        calendar = new JCalendar();
        calendar.addPropertyChangeListener(this);

        add(calendar, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        table = new CalendarViewTable();
        scrollPane.setViewportView(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void update(Observable o, Object arg) {
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("calendar")) {
            Calendar cal = (Calendar) evt.getNewValue();
            model.setSelectionDate(cal.getTime());
            model.selectTasksForDate();
        }
    }

    private class CalendarViewTable extends TaskTable {

        public CalendarViewTable() {
            super(new CalendarViewTableModel());
        }
    }

    private class CalendarViewTableModel extends AbstractTableModel {
        private String[] columnNames = { "Task name", "Activity" };

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return model.getCalendarTasksNumber();
        }

        public Object getValueAt(int row, int col) {

            TaskEventBean taskEvent = model.getCalendarTask(row);

            switch (col) {
                case 0:
                    return taskEvent.getTask().getShortName();
                case 1:
                    return taskEvent.getStateChanges();
            }

            throw new IllegalArgumentException();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0);
        }
    }
}
