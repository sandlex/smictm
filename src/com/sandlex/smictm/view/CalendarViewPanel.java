package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.model.TaskEvent;
import com.sandlex.smictm.model.TaskEventBean;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

/**
 * @author Alexey Peskov
 */
public class CalendarViewPanel extends AbstractPanel implements PropertyChangeListener {

    private static final String ACTIVITIES_COL_NAME = "Activities";

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

        private class TooltippedActivitiesRowRenderer extends DefaultTableCellRenderer {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setToolTipText("<html>" + getToolTipForActivities(row) + "</html>");
                return this;
            }
        }

        public CalendarViewTable() {
            super(new CalendarViewTableModel());
            TableColumnModel colModel = getColumnModel();
            for (int j = 0; j < colModel.getColumnCount(); j++) {
                if (ACTIVITIES_COL_NAME.equals(colModel.getColumn(j).getHeaderValue())) {
                    colModel.getColumn(j).setCellRenderer(new TooltippedActivitiesRowRenderer());
                    break;
                }
            }

        }

        @Override
        protected String getToolTip(int row)
        {
            return ((TaskAbstractTableModel) table.getModel()).getTaskInfo(row);
        }

        private String getToolTipForActivities(int row) {
            return ((CalendarViewTableModel) table.getModel()).getTaskActivitiesInfo(row);
        }
    }

    private class CalendarViewTableModel extends TaskAbstractTableModel {
        private String[] columnNames = { TASK_COL_NAME, ACTIVITIES_COL_NAME };

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

        public String getTaskInfo(int row) {
            TaskEventBean taskEvent = model.getCalendarTask(row);

            return taskEvent.getTask().getName();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0);
        }

        public String getTaskActivitiesInfo(int row) {
            StringBuilder activities = new StringBuilder();
            DateFormat dateForm = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat timeForm = new SimpleDateFormat("hh:mm");
            TaskEventBean taskEvent = model.getCalendarTask(row);
            String lastDate = "";
            for (TaskEvent event : taskEvent.getTask().getActivities()) {
                String newDate = dateForm.format(event.getDate());
                if (!lastDate.equals(newDate)) {
                    activities.append("<b>").append(newDate).append("</b><br>");
                    lastDate = newDate; 
                }
                activities.append(timeForm.format(event.getDate())).append(" - ").append(event.getName()).append("<br>");
            }

            return activities.toString();
        }
    }
}
