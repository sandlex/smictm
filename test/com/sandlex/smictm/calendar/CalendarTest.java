package com.sandlex.smictm.calendar;

import javax.swing.JFrame;

import com.sandlex.smictm.model.Model;

public class CalendarTest {
	
	public static void main(String[] args) {
		CalendarPanel cal = new CalendarPanel(new Model(""));
		JFrame frame = new JFrame();
		frame.add(cal);
		frame.pack();
		frame.setVisible(true);
	}

}
