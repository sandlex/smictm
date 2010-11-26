package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Activity;
import com.sandlex.smictm.model.Model;
import com.sandlex.smictm.model.Task;

import javax.swing.*;
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
    private boolean isEditMode = false;
    private int rowBeingEdited = -1;

    public TaskViewPanel(Model model) {
        super(model);
    }

    @Override
    protected void initComponents() {

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        JScrollPane tableScrollPane = new JScrollPane();

        table = new TaskViewTable();
        tableScrollPane.setViewportView(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.add(tableScrollPane, JSplitPane.TOP);

        JScrollPane areaScrollPane = new JScrollPane();
        area = new JTextArea();
        areaScrollPane.setViewportView(area);
        area.addKeyListener(this);
        splitPane.add(areaScrollPane, JSplitPane.BOTTOM);

        add(splitPane, BorderLayout.CENTER);

        splitPane.setDividerLocation(580);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        //isMetaDown - returns whether the meta key was pressed during the event. Maps to the Windows key on Windows
        // and the Command key on Mac OS X.
        if (e.getKeyCode() == KeyEvent.VK_ENTER && (e.isControlDown() || e.isMetaDown())) {
            if (isEditMode) {
                model.updateTask(rowBeingEdited, area.getText());
                isEditMode = false;
            } else {
                model.addTask(area.getText());
            }
            area.setText("");
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            area.setText("");
            if (isEditMode) {
                isEditMode = false;
                updateTableSelection();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    private void updateTableSelection() {
        int row = table.getSelectedRow();
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        table.getSelectionModel().setSelectionInterval(row, row);
    }

    public void update(Observable o, Object arg) {
        updateTableSelection();
    }

    private class TaskViewTable extends TaskTable implements MouseListener {

        public TaskViewTable() {
            super(new TaskViewTableModel());
            getColumnModel().getColumn(0).setMaxWidth(120);
            getColumnModel().getColumn(2).setMaxWidth(15);
            getColumnModel().getColumn(2).setPreferredWidth(15);
            getColumnModel().getColumn(2).setMinWidth(15);

            TaskViewTableCellEditor editor = new TaskViewTableCellEditor();
            getColumnModel().getColumn(0).setCellEditor(editor);

            addMouseListener(this);
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                if (getColumnModel().getSelectedColumns()[0] == 1) {
                    model.addActivity(getSelectedRow(), Activity.Touched);
                } else if (getColumnModel().getSelectedColumns()[0] == 2) {
                    isEditMode = true;
                    rowBeingEdited = getSelectedRow();
                    area.setText(model.getTask(rowBeingEdited).getName());
                }
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        @Override
        protected String getToolTip(int row) {
            return ((TaskAbstractTableModel) table.getModel()).getTaskInfo(row);
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

    private class TaskViewTableModel extends TaskAbstractTableModel {
        private String[] columnNames = { "State", "Task", "" };

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
                case 2:
                    return row == rowBeingEdited && isEditMode ? " * " : "...";
            }

            throw new IllegalArgumentException();
        }

        public String getTaskInfo(int row) {
            Task task = model.getTask(row);

            return task.getName();
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 0);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            super.setValueAt(aValue, rowIndex, columnIndex);

            if (columnIndex == 0) {

                if (JOptionPane.showConfirmDialog(table, "Apply new state " + aValue + "?", "Confirmation", JOptionPane.OK_CANCEL_OPTION)
                        == JOptionPane.OK_OPTION) {
                    model.addState(rowIndex, aValue.toString());
                } else {
                    
                }
            }
        }
    }
}
