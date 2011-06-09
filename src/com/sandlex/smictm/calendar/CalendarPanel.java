package com.sandlex.smictm.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CalendarPanel extends JPanel {
	
	private final Object[] monthes = {"January", "February", "March", "April", "May", "June", "July", "September", "October", "November", "December"};
	private JComboBox month;
	private JSpinner year;
	private Calendar cal;
	
	public CalendarPanel() {
		cal = GregorianCalendar.getInstance();
		
		month = new JComboBox(monthes);
		month.getModel().setSelectedItem(monthes[cal.get(Calendar.MONTH)]);
		SpinnerNumberModel sm = new SpinnerNumberModel();
		sm.setMinimum(1970);
		year = new JSpinner(sm);
		year.setValue(cal.get(Calendar.YEAR));
		add(month);
		add(year);
	}

}
