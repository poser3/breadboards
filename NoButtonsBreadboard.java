package com.oxfordmathcenter.breadboards;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.Timer;

import acm.graphics.GObject;
import acm.program.*;

/** 
 * Provides a simple graphical user interface consisting of an area to display text, 
 * a text field for entering text, and a single button labeled "Go" by default.
 * @author paul oser
 */
public class NoButtonsBreadboard extends AbstractBreadboard {
	
	private final int INSTRUCTIONS_WIDTH = 35;
	private final int INSTRUCTIONS_LINES = 4;
	private final int INPUT_WIDTH = 20;
	private JTextArea textArea_;
	private int APPLET_WIDTH = 500;
	private int APPLET_HEIGHT = 500;

	/**
	 * Sets window size and layout
	 */
	public void init() {
		textArea_ = new JTextArea(INSTRUCTIONS_LINES,INSTRUCTIONS_WIDTH);
		textArea_.setLineWrap(true);
		textArea_.setWrapStyleWord(true);
		add(textArea_, NORTH);
		
		setSize(APPLET_WIDTH,APPLET_HEIGHT);
	}
	
	/**
	 * Returns the textArea at the top of the breadboard window. 
	 * @return textArea at the top of the breadboard window
	 */
	public JTextArea getTextArea() {
		return textArea_;
	}
	
	/**
	 *  This routine gets called when the program starts running --  
	 *  this method should be overridden.
	 */
	public void run() {
		//override this method
	}
	
}