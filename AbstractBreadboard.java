package com.oxfordmathcenter.breadboards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

public abstract class AbstractBreadboard extends GraphicsProgram {
	
	Timer timer_;
	int timerDelay_ = 1;
	
	public int getTimerDelay() {
		return timerDelay_;
	}

	/** 
	 * Constructs the breadboard and its related timer and timerListener
	 */
	public AbstractBreadboard() {
		//add timer and timer listener
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateElements();
			}
		};
		timer_ = new Timer(timerDelay_, timerListener);
		
		//add mouse listeners
		MouseListener mouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				onMouseClick(e);
			}

			public void mouseEntered(MouseEvent e) {
				onMouseEntered(e);
			}

			public void mouseExited(MouseEvent e) {
				onMouseExited(e);
			}

			public void mousePressed(MouseEvent e) {
				onMousePressed(e);
				
			}

			public void mouseReleased(MouseEvent e) {
				onMouseReleased(e);
			}
		};
		this.getGCanvas().addMouseListener(mouseListener);
		
		//add mouse motion listener
		MouseMotionListener mouseMotionListener = new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}

			public void mouseMoved(MouseEvent e) {
				onMouseMoved(e);
			}
		};
		this.getGCanvas().addMouseMotionListener(mouseMotionListener);
	}
	
	/**
	 * Gets the timer associated with this breadboard
	 * @return
	 */
	public Timer getTimer() {
		return timer_;
	}

	
	// loops through each GObject attached to this gBreadboard.  For each
	// that implements a TimerAware interface, their onTimerTick() method
	// is invoked
	private void updateElements() {
		int numElements = this.getElementCount();
		for (int i=0; i < numElements; i++) {
			acm.graphics.GObject element = this.getElement(i);
			if (element instanceof TimerAware) {
				((TimerAware) element).onTimerTick();
			}
		}
		
		this.onTimerTick();
	}
	
	public void onTimerTick() {
		//this method should be overloaded
	}
	
	public void onMouseClick(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMouseEntered(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMouseExited(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMousePressed(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMouseReleased(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMouseDragged(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void onMouseMoved(MouseEvent e) {
		//this method should be overloaded
	}
	
	public void paintBreadboardCanvas() {
		this.getGCanvas().paintAll(this.getGCanvas().getGraphics());
	}

}
