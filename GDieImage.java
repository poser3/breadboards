package com.oxfordmathcenter.breadboards;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import acm.graphics.GImage;

/**
 * An extension of GObject that displays as a die face showing a particular value
 * @author paul
 */
public class GDieImage extends GImage {
	
	public static final int DIE_HEIGHT = 49;
	public static final int DIE_WIDTH = 50;
	public static final int NUM_FACES = 6;
	
	private static GImage diceGImage;
	private static boolean isDiceImageLoaded = false;
	
	/**
	 * Create a new die image, that displays a given value on its face and is positioned at (0,0)
	 * @param value the value to be displayed on the die face
	 */
	public GDieImage(int value) {
		//must invoke super constructor first, so create a temporary 1x1 image, we will reset it momentarily
        super(new int[1][1]);
        
        if (! isDiceImageLoaded) {
        	//get the image of all die faces from the dice.png resource, and store in a static variable
        	//update isDiceImageLoaded accordingly
            diceGImage = new GImage(new int[1][1]);
    	    InputStream diceInputStream = this.getClass().getResourceAsStream("dice.png");
    	    try {
    	    	Image diceImage = ImageIO.read(diceInputStream);
    	    	diceGImage.setImage(diceImage);
    	    	isDiceImageLoaded = true;
    	    }
    	    catch (IOException e) {
    		    System.out.println("There was a problem loading the dice image");
    		}
        }
        
        //display the right die-face picture for this value
        displayValue(value);
	}
	
	/**
	 * Create a new die image, that displays a given value on its face and is positioned at (x,y)
	 * @param value the value to be displayed on the die face
	 * @param x the x-coordinate of the upper left corner of the die face image
	 * @param y the y-coordinate of the upper left corner of the die face image
	 */
	public GDieImage(int value, double x, double y) {
		this(value);
		this.setLocation(x,y);
	}
	
	/**
	 * change the image of this die to show a particular value
	 * @param value the value to be displayed on the die face
	 */
	public void displayValue(int value) {
	   //grab pixel array for the large image of all die faces	
       int[][] dicePixelArray_ = diceGImage.getPixelArray();
	
	   //create a pixelArray for the die face that is to be displayed, that is of the right size
	   int[][] diePixelArray = new int[DIE_HEIGHT][DIE_WIDTH];
	
	   //fill the card pixel array with the appropriate pixels from the dice pixel array
	   for (int row = 0; row <  DIE_HEIGHT; row++) {
	   	   for (int col = 0; col < DIE_WIDTH; col++) {
			   diePixelArray[row][col] = dicePixelArray_[row][(value-1) * DIE_WIDTH + col];
		   }
	   }
	   
	   //reset this image to one constructed from the card pixel array
	   GImage dieGImage = new GImage(diePixelArray);
	   this.setImage(dieGImage.getImage());
	}
}
