package com.sandlex.smictm.view;

import javax.swing.table.AbstractTableModel;

/**
 * @author Alexey Peskov
 */
public abstract class TaskAbstractTableModel extends AbstractTableModel {

    abstract protected String getTaskInfo(int row);
}
