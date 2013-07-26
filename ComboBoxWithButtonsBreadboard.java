package com.oxfordmathcenter.breadboards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

import acm.program.*;

/** 
 * Provides a simple graphical user interface consisting of an area to display text, 
 * a text field for entering text, and a single button labeled "Go" by default.
 * @author paul oser
 */
public class ComboBoxWithButtonsBreadboard extends AbstractBreadboard {
	
	private final int INSTRUCTIONS_WIDTH = 35;
	private final int INSTRUCTIONS_LINES = 4;
	private final int INPUT_WIDTH = 20;
	private JButton button1_;
	private JButton button2_;
	private JTextArea textArea_;
	private JLabel label_;
	private JComboBox comboBox_;
	//private JTextField userInputTextField_;
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
		
		label_ = new JLabel("Input: ");
		add(label_, SOUTH);
		
		String[] choices = {"Choice 1", "Choice 2", "Choice 3"};
		BreadboardComboBoxModel model = new BreadboardComboBoxModel(choices);
		comboBox_ = new JComboBox(model);
		this.add(comboBox_, SOUTH);
		
		button1_ = new JButton("Button1");
		button1_.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onButton1Click();
					}
				});
		add(button1_, SOUTH);	
		
		button2_ = new JButton("Button2");
		button2_.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onButton2Click();
					}
				});
		add(button2_, SOUTH);	
		
		setSize(APPLET_WIDTH,APPLET_HEIGHT);
	}
	
	/**
	 * Returns the label at the bottom left of the breadboard window.
	 * @return the label at the bottom left of the breadboard window.
	 */
	public JLabel getLabel() {
		return label_;
	}
	

	/**
	 * Returns the textArea at the top of the breadboard window. 
	 * @return textArea at the top of the breadboard window
	 */
	public JTextArea getTextArea() {
		return textArea_;
	}
	
	/**
	 * Returns the left-most button on the form
	 * @return the left-most button, the one with the initial text "Button1"
	 */
	public JButton getButton1() {
		return button1_;
	}
	
	/**
	 * Returns the right-most button on the form
	 * @return the right-most button, the one with the initial text "Button2"
	 */
	public JButton getButton2() {
		return button2_;
	}
	
	/**
	 * Gets JComboBox element displayed at the bottom of the window
	 * @return the JComboBox element at the bottom of the window
	 */
	public JComboBox getComboBox() {
		return comboBox_;
	}
	
	/**
	 * This routine is called when the user hits the "Button1" button --  
	 * this method should be overridden.
	 */
	public void onButton1Click() {		
		//override this method
	}
	
	/**
	 * This routine is called when the user hits the "Button2" button --  
	 * this method should be overridden.
	 */
	public void onButton2Click() {		
		//override this method
	}
	/**
	 *  This routine gets called when the program starts running --  
	 *  this method should be overridden.
	 */
	public void run() {
		//override this method
	}
	
}