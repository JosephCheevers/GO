package gogame;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;
//import gogame.ReversiControl;
//import gogame.ReversiControlSkin;
//import gogame.Go;

//class definition for a reversi piece
class GoPiece extends Group {
	// private fields
	private int playercopy;		// the player that this piece belongs to
	private Ellipse piece;	// ellipse representing the player's piece
	private Translate t;	// translation for the player piece
	private DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.web("#111111")); //shadow for pieces
	
	
	// default constructor for the class
	public GoPiece(int player) {
		playercopy = player; 
		piece = new Ellipse(); t = new Translate();
		piece.getTransforms().add(t);
		piece.setEffect(ds); //add shadow
		
		if(playercopy == 1) {
			piece.setFill(Color.WHITE);
		}
		else if (playercopy == 2) {
			piece.setFill(Color.BLACK);
		}
		else {
			piece.setFill(Color.TRANSPARENT); //invisible
		}
		this.getChildren().add(piece);
	}
	
	// overridden version of the resize method to give the piece the correct size
	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		piece.setCenterX(width/2); piece.setCenterY(height/2);
		piece.setRadiusX(width/4); piece.setRadiusY(height/4); //divide by 3 for slightly smaller pieces
	}
	
	// overridden version of the relocate method to position the piece correctly
	@Override
	public void relocate(double x, double y) {
		super.relocate(x, y);
		t.setX(x);  t.setY(y);
	}
	
	// public method that will swap the colour and type of this piece
	//change a white piece to a black piece and vice versa
	public void swapPiece() { 
		if(playercopy == 1) {
			playercopy = 2; //change value of player
			setPiece(playercopy); //switch piece
		}
		else if(playercopy == 2) {
			playercopy=1;
			setPiece(playercopy);
		}	
	}
	
	// method that will set the piece type
	// Change the colour of a piece depending on the value passed in. If the type is zero the
	// piece should be invisible otherwise it should be set to be visible.
	public void setPiece(final int type) {
		if (type == 1) {
			piece.setFill(Color.WHITE); //visible - Player1
		}
		else if(type == 2) {
			piece.setFill(Color.BLACK); //visible - Player2
		}
		else if(type == 0) {
			piece.setFill(Color.TRANSPARENT); //invisible
		}	
		else if(type == 3) {
			piece.setFill(Color.web("#555555", 0.2)); //set color of empty piece that is viable move to grey
		}
	}
	
	// returns the type of this piece
	public int getPiece() {
		return playercopy;
	}
	
	// allows printing of pieces for debugging purposes
	public String toString() {
		return piece.toString();
	}
}
