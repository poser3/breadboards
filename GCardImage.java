package com.oxfordmathcenter.breadboards;

import acm.graphics.GImage;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * An extension of GObject that displays as a playing card of a given rank and suit
 * @author paul
 */
public class GCardImage extends GImage {
	
	private static final int CARDS_WIDTH = 950;
	private static final int CARDS_HEIGHT = 392;
	public static final int NUM_CARDS_IN_DECK = 52;
	public static final int NUMBER_OF_SUITS = 4;
	public static final int NUMBER_OF_RANKS = 13;
	public static final int CARD_WIDTH = CARDS_WIDTH / NUMBER_OF_RANKS;
	public static final int CARD_HEIGHT = CARDS_HEIGHT / NUMBER_OF_SUITS;
	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int CLUBS = 0;
	public static final int SPADES = 1;
	public static final int HEARTS = 2;
	public static final int DIAMONDS = 3;
	
	private static GImage cardsGImage;
	private static boolean isCardsImageLoaded = false;
	
	/**
	 * Create a card GObject that can be added to an ACM GraphicsProgram
	 * @param rank the rank of the card (for Ace, Jack, Queen, and King, see static constants of this class)
	 * @param suit the suit of the card (see the static constants of this class for possible values)
	 */
	public GCardImage(int rank, int suit) {
		//must invoke super constructor first, so create a temporary 1x1 image, we will reset it momentarily
        super(new int[1][1]);
        
        if (! isCardsImageLoaded) {
        	//get the image of the entire deck from the cards.png resource and store in a static variable
        	//update isCardsImageLoaded accordingly
            cardsGImage = new GImage(new int[1][1]);
            InputStream cardsInputStream = this.getClass().getResourceAsStream("cards.png");
            try {
            	Image cardsImage = ImageIO.read(cardsInputStream);
            	cardsGImage.setImage(cardsImage);
            	isCardsImageLoaded = true;
            }
            catch (IOException e) {
            	System.out.println("There was a problem loading the cards.png image");
            }
        }
        
        //reset the image so the correct card is displayed
        this.displayCard(rank, suit);
	}
	
	/**
	 * Similar to GCard(rank,suit), except that it places the card GObject at coordinates (x,y)
	 * @param rank the rank of the card to be displayed
	 * @param suit the suit of the card to be displayed
	 * @param x the x-coordinate of the upper left corner of the image
	 * @param y the y-coordinate of the upper left corner of the image
	 */
	public GCardImage(int rank, int suit, double x, double y) {
		this(rank, suit);
		this.setLocation(x,y);
	}
	
	public void displayCard(int rank, int suit) {
		int[][] cardsPixelArray = cardsGImage.getPixelArray();
		
		//create a pixelArray for this card that is of the right size
		int[][] cardPixelArray = new int[CARD_HEIGHT][CARD_WIDTH];
		
		//fill the card pixel array with the appropriate pixels from the cards pixel array
		for (int row = 0; row <  CARD_HEIGHT; row++) {
			for (int col = 0; col < CARD_WIDTH; col++) {
				cardPixelArray[row][col] = cardsPixelArray[suit*CARD_HEIGHT + row ][(rank - 1)*CARD_WIDTH + col];
			}
		}
		
		//reset this image to one constructed from the card pixel array
		GImage cardGImage = new GImage(cardPixelArray);
		this.setImage(cardGImage.getImage());		
	}

}
