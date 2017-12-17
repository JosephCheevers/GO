package _2017._09._assignments.projectgo.template.v2;

import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

//class definition
class Piece extends Group {
	
	// XXX start of methods that only exist to faciliate testing
	public Ellipse getRenderPiece() { return piece; }
	public Translate getTranslate() { return t; }
	// XXX end of methods that only exist to facilite testing

	// private fields
	private int player;		// the player that this piece belongs to
	private Ellipse piece;	// ellipse representing the player's piece
	private Translate t;	// translation for the player piece
	private Text text;
	private int x; 
	private int y; 
	
	// shadows & lighting
	private DropShadow ds = new DropShadow(5.0, 3.0, 3.0, Color.web("#111111")); //shadow for pieces
	private InnerShadow inshadow = new InnerShadow(15, -4, -4, Color.web("#121212")); //innershadow
	private Light.Distant dist = new Light.Distant(-135.0f, 60, Color.WHITE); //light
	private Lighting pieceLight = new Lighting(dist); //light

		
	// default constructor for the class
	public Piece(int player, int x, int y) { 
		// take a copy of the player
		this.player = player;
		this.x = x; 
		this.y = y;
		// generate the ellipse for the player. if this is player 1 then this should
		// be white otherwise it should be black
		
		piece = new Ellipse();
		t = new Translate();
		piece.getTransforms().add(t);
		this.setPlayer(player);
		getChildren().add(piece);
		
		inshadow.setInput(pieceLight); //add light
		ds.setInput(inshadow); //add innershadow effect
		piece.setEffect(ds); //add shadow 
	}

	// overridden version of the resize method to give the piece the correct size
	@Override
	public void resize(double width, double height) {
		// call the superclass method
		super.resize(width, height);

		// resize and relocate the ellipse
		piece.setCenterX(width / 2.0); piece.setCenterY(height / 2.0);
		piece.setRadiusX(width / 4.0); piece.setRadiusY(height / 4.0);
	}

	// overridden version of the relocate method to position the piece correctly
	@Override
	public void relocate(double x, double y) {
		// call the superclass method
		super.relocate(x, y);
		// update the translate with the new position
		t.setX(x); t.setY(y);
	}

	// public method that will swap the colour and type of this piece
	public void swapPiece() {
		if(player == 1) {
			player = 2;
			piece.setFill(Color.WHITE);
		} else {
			player = 1;
			piece.setFill(Color.BLACK);
		}
	}

	// method that will set the piece type
	public void setPlayer(final int type) {
		player = type;

		// set the colour of the piece and if necessary make it visible
		if(type == 1) {
			piece.setFill(Color.WHITE);
			piece.setVisible(true);
		} else if (type == 2) {
			piece.setFill(Color.BLACK);
			piece.setVisible(true);
		} else if (type == 0) {
			piece.setVisible(false);
		}
	}
	
	public int getOpposingPlayer(){
		if(player==1)
			return 2; 
		if (player==2)
			return 1; 
		else return -1;
	}
	
	public static int getOpposingPlayer(int player){
		if(player==1)
			return 2; 
		if (player==2)
			return 1; 
		else return -1;
	}
	
	public String toString(){
		return new StringBuffer().append("P: ").append(this.player).append(" [").append(this.x).append(",").append(this.y).append("]").toString();
	}

	// returns the type of this piece
	public int getPlayer() { return player; }
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
