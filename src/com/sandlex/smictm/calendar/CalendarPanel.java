package com.sandlex.smictm.calendar;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sandlex.smictm.model.Model;

public class CalendarPanel extends JPanel implements ActionListener,
		ChangeListener {
	//TODO sunday & saturday make red colored
	// TODO Detect time changing during a program executing

	private final Object[] monthes = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	private List<JButton> buttons;
	private JComboBox month;
	private JSpinner year;
	private Calendar currentDate;
	private Calendar displayedDate;
	private JPanel dates;
	private Model model;

	public CalendarPanel(Model model) {
		this.model = model;
		currentDate = GregorianCalendar.getInstance();
		displayedDate = GregorianCalendar.getInstance();

		month = new JComboBox(monthes);
		month.getModel().setSelectedItem(
				monthes[currentDate.get(Calendar.MONTH)]);
		SpinnerNumberModel sm = new SpinnerNumberModel();
		sm.setMinimum(1970);
		year = new JSpinner(sm);
		year.setValue(currentDate.get(Calendar.YEAR));
		add(month);
		add(year);

		dates = new JPanel();
		dates.setLayout(new GridLayout(6, 7));
		add(dates);
		updateCalendar();
		year.addChangeListener(this);
		month.addActionListener(this);
	}

	public void updateCalendar() {
		System.out.println("update cal");
		displayedDate.set((Integer) year.getValue(), Arrays.asList(monthes)
				.indexOf(month.getModel().getSelectedItem()), 1);

		buttons = new ArrayList<JButton>(42);
		dates.removeAll();
		for (int i = 1; i < 43; i++) {
			if (i < displayedDate.get(Calendar.DAY_OF_WEEK)
					|| buttons.size() > displayedDate
							.getActualMaximum(Calendar.DAY_OF_MONTH) - 1) {
				dates.add(new JLabel());
			} else {
				final JButton button = new JButton();
				
				//TODO Do not create calendar instance. Optimize
				if (model.isDateHasTasks(new GregorianCalendar(displayedDate.get(Calendar.YEAR), displayedDate.get(Calendar.MONTH), buttons.size() + 1).getTime())) {
					button.setText("<html><u>" + String.valueOf(buttons.size() + 1) + "</u></html>");
				} else {
					button.setText(String.valueOf(buttons.size() + 1));
				}
				
				button.setBorderPainted(isCurrentDate());
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO Do not create calendar instance. Optimize
						model.setSelectionDate(new GregorianCalendar(displayedDate.get(Calendar.YEAR), displayedDate.get(Calendar.MONTH), buttons.indexOf(button) + 1).getTime());
				        model.selectTasksForDate();
					}});
				buttons.add(button);
				dates.add(button);
			}
		}

		updateUI();
	}

	private boolean isCurrentDate() {
		return displayedDate.get(Calendar.YEAR) == currentDate
		.get(Calendar.YEAR)
		&& displayedDate.get(Calendar.MONTH) == currentDate
				.get(Calendar.MONTH)
		&& buttons.size() + 1 == currentDate.get(Calendar.DATE);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateCalendar();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateCalendar();
	}

}
