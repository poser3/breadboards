package com.oxfordmathcenter.breadboards;

//TODO: Add push, pop, and a constructor that doesn't take the breadboard

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.Color;

import com.oxfordmathcenter.breadboards.*;

/**
 * Logo-inspired turtles for use with GBreadboard class which can move forward or reverse,
 * turn left or right, change size, be visible or invisible, raise or lower a pen to trace it's path,
 * change body or pen color, and change speed.
 * @author paul oser
 */
public class Turtle extends GCompound {
	
	final double RADIANS_PER_DEGREE = Math.PI / 180;
	final double MOVING_FORWARD_DISTANCE = 1;
	final double TIME_TO_TURN = 0.5; // milliseconds
	final double TIME_TO_MOVE = 0.5; // milliseconds
    final double MAX_SPEED = 1.0;
    final double TOLERANCE = 0.00001; // for comparing doubles
	final double DEFAULT_BODY_SIZE = 20.0;
	final double DEFAULT_DIRECTION = 0;
	final double BODY_TO_HEAD_RATIO = 2.5;
	
	private double x_;
	private double y_;
	private double direction_;
	private double bodySize_;
	private double headSize_;
	private boolean isTailDown_;
	private Color turtleColor_ = Color.GREEN;
	private GOval body_;
	private GOval head_;
	private GPen tailPen_;
	private OneButtonBreadboard gBreadboard_;
	private double speed_;
	private double angle_ = 90;
	
	/**
	 * Constructs a new turtle in the center of gBreadboard's drawing area
	 */
	public Turtle(OneButtonBreadboard gBreadboard) {
		
		//set default speed
		speed_ = 0;
		
	    //set the containing GBreadboard and add the turtle to this GBreadboard
		gBreadboard_ = gBreadboard;
		gBreadboard_.add(this);
		
		//set location in the center of the drawing region (by default)
		x_ = gBreadboard_.getRegionPanel(OneButtonBreadboard.CENTER).getWidth() / 2.0;
		y_ = gBreadboard_.getRegionPanel(OneButtonBreadboard.CENTER).getHeight() / 2.0;
		
		//set default body and head sizes and direction
		bodySize_ = DEFAULT_BODY_SIZE;
		headSize_ = bodySize_ / BODY_TO_HEAD_RATIO;
		direction_ = DEFAULT_DIRECTION;
		
		//construct head
		head_ = new GOval(0,0); //these size arguments gets overridden by update()
		head_.setFilled(true);
		head_.setVisible(true);
		head_.setColor(Color.BLACK);
		this.add(head_);
		
		//construct body
		body_ = new GOval(0,0); //these size arguments gets overridden by update()
		body_.setFilled(true);
		body_.setVisible(true);
		body_.setColor(Color.BLACK);
		this.add(body_);
		
		//construct tail pen
		tailPen_ = new GPen(0,0); //thes location arguments get overridden by update();
		tailPen_.setColor(Color.BLACK);
		isTailDown_ = true;
		this.add(tailPen_);
		
		//prevent future additions or removal of elements to this compound object
		this.markAsComplete();
		
		//put head and body in their correct locations and make them the correct sizes
		this.update();
	}
	
	/**
	 * Constructs a new turtle positioned at (x,y) in the gBreadboard's drawing area
	 * @param x x-coordinate of center of turtle's body
	 * @param y y-coordinate of center of turtle's body
	 */
	public Turtle(double x, double y, OneButtonBreadboard gBreadboard) {	
		this(gBreadboard);
		x_ = x;
		y_ = y;
		this.update();
	}
	
	/**
	 * This method ensures the turtles position, orientation, color, and other
	 * attributes are reflected on the screen
	 */
	public void update() {
		
		//make head the correct size and put in the correct location
		headSize_ = bodySize_ / BODY_TO_HEAD_RATIO;
		double headCenterX = x_ + Math.cos(-direction_) * (bodySize_ + headSize_) / 2.0;
		double headCenterY = y_ + Math.sin(-direction_) * (bodySize_ + headSize_) / 2.0;
		double headLeftUpperCornerX = headCenterX - headSize_ / 2.0;
		double headLeftUpperCornerY = headCenterY - headSize_ / 2.0;
		head_.setLocation(headLeftUpperCornerX, headLeftUpperCornerY);
		head_.setSize(headSize_, headSize_);
		
		//update color of turtle
		head_.setFillColor(turtleColor_);
		body_.setFillColor(turtleColor_);
		
		//make body the correct size and put in the correct location
		double bodyLeftUpperCornerX = x_ - bodySize_ / 2.0;
		double bodyLeftUpperCornerY = y_ - bodySize_ / 2.0;
		body_.setLocation(bodyLeftUpperCornerX, bodyLeftUpperCornerY);
		body_.setSize(bodySize_, bodySize_);
		
		//make tailPen in correct location
		tailPen_.setLocation(x_,y_);
		
		// if speed is set to slow (i.e., "0"), update the associated graphics now -- 
		// as otherwise, the screen may not get updated with the new positions of
        // things by the system until much later, which can mess up the animation 
        // effect significantly
		if (! this.isAtMaxSpeed()) this.getGCanvas().paintAll(this.getGCanvas().getGraphics());
		
	}
	
	/**
	 * Returns whether or not the turtle is at maximum speed
	 * @return true if turtle speed is essentially 0, false otherwise
	 */
	public boolean isAtMaxSpeed() {
		return (Math.abs(MAX_SPEED - speed_) < TOLERANCE);
	}
	
	/**
	 * Moves the turtle some number of units forward or reverse
	 * @param distance number of units to move forward (if negative, turtle moves in reverse instead)
	 */
	public void forward(double distance) {
		
		// calculate changes in x and y per unit moved
		double dx = MOVING_FORWARD_DISTANCE * Math.cos(-this.getDirection()) * (distance > 0 ? 1 : -1);
		double dy = MOVING_FORWARD_DISTANCE * Math.sin(-this.getDirection()) * (distance > 0 ? 1 : -1);
		
		// move turtle and pen one unit at a time, pausing after each move to slow the animation down
		int iterations = (int) Math.abs(distance);
		for (int i=0; i < iterations; i++) {
			if (this.isPenDown()) {
				this.getTailPen().drawLine(dx, dy);
			}
			else {
				this.getTailPen().move(dx, dy);
			}
			x_ += dx;
			y_ += dy;
			this.update();
			if (! this.isAtMaxSpeed()) this.pause(TIME_TO_MOVE * (1.0 - speed_));
		}
		
		// move turtle and pen the last fraction of a unit
		double fractionalDistance = Math.abs(distance % 1.0);
		fractionalDistance *= (distance > 0 ? 1 : -1);
		dx *= fractionalDistance;
		dy *= fractionalDistance;
		if (this.isPenDown()) {
		     this.getTailPen().drawLine(dx, dy);
		}
		else {
			this.getTailPen().move(dx, dy);
		}
		x_ += dx;
		y_ += dy;
		this.update();
	}
	
	/**
	 * Moves the turtle forward by its own size (i.e., the diameter of its body)
	 */
	public void forward() {
		forward(bodySize_);
	}
	
	/**
	 * Gets bounding rectangle for entire path traced by turtle.
	 * @return the bounding rectangle for the entire path traced by the turtle.
	 */
	public GRectangle getBounds() {
		return tailPen_.getBounds();
	}
	
	/**
	 * Alters the turtle's direction by some number of degrees, counter-clockwise about turtle's center
	 * @param degrees number of degrees to turn 
	 */
	public void left(double degrees) {
		
		double newDirection = this.getDirection() + RADIANS_PER_DEGREE * degrees;
		
		// animate the turn
		// turn one degree at a time, pausing after each turn to slow the animation down
		if (! this.isAtMaxSpeed()) {
			int iterations = (int) Math.abs(degrees);
	        for (int i=0; i < iterations; i++) {
	        	if (degrees > 0) {
	        		this.setDirection(this.getDirection() + RADIANS_PER_DEGREE);
	        	}
	        	else {
	        		this.setDirection(this.getDirection() - RADIANS_PER_DEGREE);
	        	}
	        	this.update();
	        	this.pause(TIME_TO_TURN * (1.0 - speed_));
			}
		}
        
        // put turtle in final orientation
        this.setDirection(newDirection);
        this.update();
	}
	
	/**
	 * Alters the turtle's direction by some number of degrees, clockwise about turtle's center
	 * @param degrees number of degrees to turn 
	 */
	public void right(double degrees) {
		this.left(-degrees);
	}
	
	/**
	 * Alters the turtle's direction by the default number of degrees, counter-clockwise about
	 * turtle's center
	 */
	public void left() {
		left(angle_);
	}
	
	/**
	 * Alters the turtle's direction by the default number of degrees, clockwise about
	 * turtle's center
	 */
	public void right() {
		right(angle_);
	}
	

	private GCanvas getGCanvas() {
		return gBreadboard_.getGCanvas();
	}
    
    /**
     * Set the color of the turtle's body and head
     */
    public void setColor(Color color) {
    	this.turtleColor_ = color;
    	this.update();
    }
    
    /**
     * Set the color of the pen in the turtle's "tail"
     * @param color color of the pen in the turtle's "tail"
     */
    public void setPenColor(Color color) {
    	this.tailPen_.setColor(color);
    	this.update();
    }
    
    /**
     * This method returns the turtle's heading as an angle (in radians)
     * @return the angle (in radians) that corresponds to the turtle's heading. 
     * 0 means the turtle is heading to the right, while pi/2 indicates the turtle
     * is heading upwards.
     */
    public double getDirection() {
    	return direction_;
    }
    
    /**
     * Sets the heading for the turtle
     * @param degrees direction the heading of the turtle.  When 0, the turtle heads 
     * to the right, while pi/2 heads the turtle upwards.
     */
    public void setDirection(double degrees) {
    	direction_ = degrees;
    	this.update();
    }
    
    /**
     * Determines whether the tail is up or down.  Recall, when the tail is down 
     * turtle draws it path, while when the tail is up nothing is drawn.
     * @return true if tail is down, false otherwise
     */
    public boolean isPenDown() {
    	return isTailDown_;
    }
    
	/**
	 * Sets the turtle's tail (which can be thought of as holding a pen) down,   
     * which means the turtle will draw its path
	 */
	public void penDown() {
		isTailDown_ = true;
	}
	
	/**
	 * Sets the turtle's tail (which can be thought of as holding a pen) up,   
     * which means the turtle will not draw its path
	 */
	public void penUp() {
		isTailDown_ = false;
	}
	
	/* 
	 * getter for GPen (private for now)
	 */
	private GPen getTailPen() {
		return this.tailPen_;
	}
    
    /**
     * This method returns a text-description of the turtle, noting its position and direction
     * @return a string similar in form to "Turtle at (50,100) with heading 3.1459"
     */
    public String toString() {
    	return ("Turtle at (" + x_ + ", " + 
                y_ + ") with heading " + this.direction_);
    }
    
    /**
     * Set the speed of the turtle
     * @param speed if speed is 0, the turtle will move slowly enough to see all of the movements;
     * if speed is 1, the turtle will move and draw as fast as possible (possibly faster than the
     * screen can update)
     */
    public void setSpeed(double speed) {
    	speed_ = speed;
    }
    
    /**
     * Gets the speed of the turtle
     * @return 0 for slow enough to be seen, 1 for as fast as possible.
     */
    public double getSpeed() {
    	return speed_;
    }
    
    /**
     * Removes all lines from the turtle's path
     */
    public void erasePath() {
    	tailPen_.erasePath();
    }
    
    /** 
     * Makes the turtle invisible
     */
    public void hideTurtle() {
    	body_.setVisible(false);
    	head_.setVisible(false);
    }
    
    /** 
     * Makes the turtle visible (the turtle itself is initially visible).
     */
    public void showTurtle() {
    	body_.setVisible(true);
    	head_.setVisible(true);
    }
    
    /**
     * Moves/teleports the turtle to the point (x,y) without drawing a line.
     */
    public void setLocation(double x, double y) {
    	x_ = x;
    	y_ = y;
    	this.update();
    }
    
    /**
     * Returns the size of the turtle (the diameter of its body)
     * @return size of the turtle (the diameter of its body)
     */
    public double getTurtleSize() {
    	return bodySize_;
    }
    
    /** 
     * Sets the size of the turtle (i.e., the diameter of the body)
     * @param size the diameter of the turtle body
     */
    public void setSize(int size) {
    	bodySize_ = size;
    	this.update();
    }
    
    /**
     * Returns whether the turtle is visible
     * @return true if visible, false otherwise
     */
    public boolean isTurtleVisible() {
    	return this.isVisible();
    }
    
    /**
     * Returns angle turtle should turn (in degrees) if no angle is specified in a call
     * to right() or left()
     * @return default turning angle in degrees
     */
    public double getDefaultTurningAngle() {
    	return angle_;
    }
    
    /** Sets angle turtle should turn (in degrees) if no angle is specified in a call
     * to right() or left()
     * @param angle default turning angle in degrees
     */
    public void setDefaultTurningAngle(double angle) {
    	angle_ = angle;
    }
    
    /**
     * Makes it possible for a Turtle  to be run under the control of a GraphicsProgram object.
     * @param args An array of string arguments
     */
    public static void main(String[] args) {
    	
    }
    
    
}
