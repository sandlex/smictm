package com.sandlex.smictm.calendar;

import javax.swing.JFrame;

import org.junit.Test;

public class CalendarTest {
	
	public static void main(String[] args) {
		CalendarPanel cal = new CalendarPanel();
		JFrame frame = new JFrame();
		frame.add(cal);
		frame.pack();
		frame.setVisible(true);
	}

}
