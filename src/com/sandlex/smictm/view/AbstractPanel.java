package com.sandlex.smictm.view;

import com.sandlex.smictm.model.Model;

import javax.swing.*;
import java.util.Observer;

/**
 * @author Alexey Peskov
 */
public abstract class AbstractPanel extends JPanel implements Observer {

    protected Model model;

    protected AbstractPanel(Model model) {
        this.model = model;
        initComponents();
    }

    abstract protected void initComponents();
}
