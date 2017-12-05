package gogame;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class GoBoard extends Pane{
	
	// rectangle that makes the background of the board
	public static Rectangle background;
	// arrays for the lines that makeup the horizontal and vertical grid lines
	private Line[] horizontal;
	private Line[] vertical;
	// arrays holding translate objects for the horizontal and vertical grid lines
	private Translate[] horizontal_t;
	private Translate[] vertical_t;

	// the width and height of a cell in the board
	public double cell_width;
	public double cell_height;

	//Label for displaying winners
	Label winnerLabel = new Label();
	
	static int change = 0; // int for background changes
	
	public GoBoard() {
		super();
		this.getChildren().add(new Label("The Board"));	
	
		// allocate memory for arrays
		//variable for board size
		int boardsize = 7;
		//render = new ReversiPiece[boardsize][boardsize];
		horizontal = new Line[boardsize];
		vertical = new Line[boardsize];
		horizontal_t = new Translate[boardsize];
		vertical_t = new Translate[boardsize];
		
		
		
		// call methods for initialising lines & background, render and resetting game
		this.initialiseLinesBackground();
		//this.initialiseRender();
		//this.resetGame();
		//this.canMove();
	}

		// overridden version of the resize method to give the board the correct size
	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		
		// figure out the width and height of a cell
		cell_width = width / 7.0;
		cell_height = height / 7.0;
		
		// resize the rectangle to take the full size
		background.setWidth(width); background.setHeight(height);
		
		//resize and reposition winner label
		 //style: changing font size with resize
		
		winnerLabel.setStyle("-fx-text-fill: RED;-fx-font-size:" + cell_width/2.5 + "pt; "
				+ "-fx-font-family: \"Helvetica\", Georgia, Sans-serif;"); 
	
		//winnerLabel.setLayoutX((width/2.5)); winnerLabel.setLayoutY((height/2.5));	 //resize - NOT WORKING
		//winnerLabel.setLayoutX((width - winnerLabel.getWidth()) / 2); // NOT WORKING
		
		//resize and relocate horizontal and vertical lines
		this.horizontalResizeRelocate(width);
		this.verticalResizeRelocate(height);
		
		//resize and relocate pieces
		//pieceResizeRelocate();
		
	}
		

	// private method that will initialise the background and the lines
	private void initialiseLinesBackground() {
		background = new Rectangle();
		
		//background image 
		Image image = new Image("board1.jpg");
		ImagePattern imagePattern = new ImagePattern(image);
		background.setFill(imagePattern);
		this.getChildren().add(background);	
		DropShadow shadow1 = new DropShadow(1.0, 1.0, 1.0, Color.BLACK);
	
		// generate HORIZONTAL lines and attach their translate objects
		for(int i = 0; i<horizontal.length; i++) {
			horizontal[i] = new Line();
			horizontal[i].setStroke(Color.BLACK);
			horizontal[i].setStartX(0);  //cell width
			horizontal[i].setEndX(0);
			horizontal[i].setStartY(0); horizontal[i].setEndY(0);
			horizontal[i].setEffect(shadow1); //add shadow
			
			horizontal_t[i] = new Translate(0, 0);

			horizontal[i].getTransforms().add(horizontal_t[i]);

			this.getChildren().add(horizontal[i]); // add to board
		}
		// generate VERTICAL lines and attach their translate objects
		for(int i = 0; i<vertical.length; i++) {
			vertical[i] = new Line();
			vertical[i].setStartX(0); 
			vertical[i].setStartY(0); vertical[i].setEndY(0); //cell height
			vertical[i].setEffect(shadow1); //add shadow
			
			vertical_t[i] = new Translate(0, 0);
			vertical[i].getTransforms().add(vertical_t[i]);

			this.getChildren().add(vertical[i]); // add to board
		}
	}
	
	// private method for resizing and relocating the horizontal lines
	private void horizontalResizeRelocate(final double width) {
		for(int i = 0; i<horizontal.length; i++) {
			horizontal[i].setStartX(cell_width*0.5);
			horizontal[i].setEndX(width - cell_width*0.5);
			horizontal_t[i].setY(cell_height*(i+0.5));
		}	
	}
	
	// private method for resizing and relocating the vertical lines
	private void verticalResizeRelocate(final double height) {
		for(int i = 0; i<vertical.length; i++) {
			vertical[i].setStartY(cell_height*0.5);
			vertical[i].setEndY(height - cell_height/2);
			vertical_t[i].setX(cell_width*(i+0.5));
		}	
	}
	
	static void changeBackground() { //method to change background of board
		//change background (3 options)
		if(change>1) change =0;
		else {
			change++;
		}
		
		if(change == 0) {
			//background.setStyle("-fx-background-image: url(\"board1.jpg\");-fx-background-repeat: no-repeat;-fx-background-size: contain;");
			Image image = new Image("board1.jpg");
			ImagePattern imagePattern = new ImagePattern(image);
			background.setFill(imagePattern);
		}
		else if(change == 1) {
			Image image = new Image("board2.jpg");
			ImagePattern imagePattern = new ImagePattern(image);
			background.setFill(imagePattern);
		}
		else {
			Image image = new Image("board3.jpg");
			ImagePattern imagePattern = new ImagePattern(image);
			background.setFill(imagePattern);
		}
		//System.out.println("Change: " + change); //test
	}
}

