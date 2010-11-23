package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.model.TaskEventBean;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DatePanel;
import net.sf.nachocalendar.event.DateSelectionEvent;
import net.sf.nachocalendar.event.DateSelectionListener;
import net.sf.nachocalendar.model.DateSelectionModel;
import net.sf.nachocalendar.model.DefaultDateSelectionModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Observable;

/**
 * @author Alexey Peskov
 */
public class CalendarViewPanel extends AbstractPanel {

    private JTable table;
    private DefaultDateSelectionModel dsm;

    public CalendarViewPanel(Model model) {
        super(model);
    }

    @Override
    protected void initComponents() {
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        final DatePanel datepanel = CalendarFactory.createDatePanel();
        datepanel.setSelectionMode(DateSelectionModel.SINGLE_SELECTION);
        dsm = new DefaultDateSelectionModel();
        datepanel.setDateSelectionModel(dsm);
        dsm.addDateSelectionListener(new DateSelectionListener() {

            public void valueChanged(DateSelectionEvent dateSelectionEvent) {
                model.setSelectionDate(dsm.getLeadSelectionDate());
                model.selectTasksForDate();
            }
        });

        add(datepanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        table = new CalendarViewTable();
        scrollPane.setViewportView(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void update(Observable o, Object arg) {
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
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
                    return taskEvent.getTaskEvent().getName();
            }

            throw new IllegalArgumentException();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0);
        }
    }
}
