package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Activity;
import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.model.Task;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 * @author Alexey Peskov
 */
public class TaskViewPanel extends AbstractPanel implements KeyListener {

    private JTextArea area;
    private TaskTable table;

    public TaskViewPanel(Model model) {
        super(model);
    }

    @Override
    protected void initComponents() {

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        JScrollPane scrollPane = new JScrollPane();

        table = new TaskViewTable();
        scrollPane.setViewportView(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.add(scrollPane, JSplitPane.TOP);

        area = new JTextArea();
        area.addKeyListener(this);
        splitPane.add(area, JSplitPane.BOTTOM);

        add(splitPane, BorderLayout.CENTER);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown())  {
            model.addTask(area.getText());
//            area.setText("");
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void update(Observable o, Object arg) {
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        table.getSelectionModel().setSelectionInterval(model.getTasksNumber() - 1, model.getTasksNumber() - 1);
    }

    private class TaskViewTable extends TaskTable implements MouseListener, ListSelectionListener
    {

        public TaskViewTable() {
            super(new TaskViewTableModel());
            getColumnModel().getColumn(0).setMaxWidth(120);

            TaskViewTableCellEditor editor = new TaskViewTableCellEditor();
            getColumnModel().getColumn(0).setCellEditor(editor);

            addMouseListener(this);
            getSelectionModel().addListSelectionListener(this);
        }

        public void valueChanged(ListSelectionEvent e) {
            super.valueChanged(e);
            int index = getSelectionModel().getLeadSelectionIndex();
            if (!e.getValueIsAdjusting()) { //todo fired twice fix
                if (index > -1) {
                    area.setText(model.getTask(index).getName());
                } else {
                    area.setText("");
                }
            }
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                model.addActivity(getSelectedRow(), Activity.Doing);
            }
        }

        public void mousePressed(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void mouseReleased(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void mouseEntered(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void mouseExited(MouseEvent e) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        private class TaskViewTableCellEditor extends AbstractCellEditor implements TableCellEditor {

            private TableCellEditor editor;

            public Object getCellEditorValue() {
                return editor.getCellEditorValue();
            }

            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

                Component component = null;

                if (column == 0) {
                    JComboBox stateCombo = new JComboBox();
                    Task task = model.getTask(row);

                    stateCombo.setModel(new DefaultComboBoxModel(task.getState().getNextStates()));

                    component = stateCombo;
                }

                if (component != null) {
                    editor = new DefaultCellEditor((JComboBox) component);
                }

                return component;
            }
        }
    }

    private class TaskViewTableModel extends AbstractTableModel {
        private String[] columnNames = { "State", "Task name" };

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return model.getTasksNumber();
        }

        public Object getValueAt(int row, int col) {

            Task task = model.getTask(row);

            switch (col) {
                case 0:
                    return task.getState();
                case 1:
                    return task.getShortName();
            }

            throw new IllegalArgumentException();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            super.setValueAt(aValue, rowIndex, columnIndex);

            if (columnIndex == 0) {
                model.addState(rowIndex, aValue.toString());
            }
        }
    }
}