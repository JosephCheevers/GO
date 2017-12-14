package _2017._09._assignments.projectgo.template.v2;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GoGameLogic {


	// ************ PRIVATE MEMBER DATA  ************************************

	private SimpleIntegerProperty currentPlayerProperty; // property to help with binding

	private int playerCurrent; // the current player
	private int playerOpposing; // the opposing player

	private boolean in_play;  // is the game currently in play
	private int scorePlayer1; // score of player1
	private int scorePlayer2; // score of player2
	private int passesPlayer1;// passes by player1
	private int passesPlayer2;// passes by player2

	private Integer score; 
	private IntegerProperty scoreProperty;  

	private ArrayList<Piece [][]> renders; // board history
	private int renderCurrent; // index of the board currently in use in the board array
	PiecesString ps ;

	private GoBoard goBoard;  // reference to the GoBoard
		
	public GoGameLogic(GoBoard goBoard) {
		super();
		this.goBoard = 	goBoard;
		this.score = 1;
		this.playerCurrent = 2;
		//Making a SimpleIntegerProperty which will bind to the TextField in the controlPanel
		this.scoreProperty = new SimpleIntegerProperty(this.score);
		this.currentPlayerProperty = new SimpleIntegerProperty(this.playerCurrent);
		ps = new PiecesString(playerCurrent); // set color with this piece
		this.resetGame();
	} 

	public void resetGame() {
		
		// reset the render
		goBoard.resetRenders();
		
		in_play = true; 
		
		// reset the variables monitoring the game
		playerCurrent = 2;
		playerOpposing = 1;
		score = 0;
		scorePlayer1 = 0; 
		scorePlayer2 = 0; 
		passesPlayer1 = 0;
		passesPlayer2 = 0;
		canMove(); 
		
		//call can move to display the viable moves in grey on reset
		


		// reset renders (board history) 
		this.renders = new ArrayList<Piece [][]>();
		// save the board in renders
		this.boardSave();
	}

	// Return the GoBoard of GameLogic
	public GoBoard getGoBoard() {
		return goBoard;
	}


// ************ TRYING TO PLACE A PIECE FUNCTIONS  ************************************

	//Try to place a piece in the given x,y coordinate
	public void placePieceTry(double x, double y) {
		System.out.println("tryPlacePeice()*******************************");
		this.score++;
		//Update the SimpleIntegerProperty scoreProperty when you update the int score so that the TextField tf_score in the GoControlPanel updates automatically
		this.scoreProperty.setValue(this.score);
		// Determine which cell the current player has clicked on
		final int cellx = (int) (x / this.getGoBoard().getCell_width());
		final int celly = (int) (y / this.getGoBoard().getCell_height());

		// if the game is not in play exit method
		System.out.println("in_play:"+in_play);
		if(!in_play)
			return;

		// if piece is not empty exit method
		if(goBoard.getRender()[cellx][celly].getPlayer() != 0)
			return;
		

		
		// Attempt to capture 
		//this.capture(this.playerCurrent,cellx, celly);
		
		piecesStringAddNeighbours(cellx,celly,ps);

		
		System.out.println();
		System.out.println("TESTING1: " + ps);

		// Test to is if it is KO
		if(isKO()){
			// you might want to prevent the move or make the move and rewind the board history
			return;
		}

		// Test to see if placing a piece here results in its group having no liberty
		if(isSuicide(cellx, celly)){
			// you might want to prevent the move or make the move and rewind the board history
			return;
		}

		// Default case - place the piece
		this.placePiece(cellx, celly);

		// if we get to this point then a successful move has been made so swap the
		// players and update the scores
		swapPlayers();
		boardSave();
		updateScores(); // NB captureAndIncrement() will update some of the score values
		determineEndGame();

		// Print out information on game status
	}

	// Places a piece 
	private void placePiece(final int x, final int y) {
		goBoard.getRender()[x][y].setPlayer(playerCurrent);  
		//if the cell is empty update the relevant render
	}

	// private method for swapping the players
	public void swapPlayers() {
		// swap the players
		 int temp = playerCurrent;
		 playerCurrent = playerOpposing;
		 playerOpposing = temp;
		  
		 GoControlPanel.stoneColour(playerCurrent);
		//this.playerProperty.setValue(playerCurrent);
	} 


// ************ KO FUNCTIONS  ************************************	

	// Method which returns true if current board is equal to the board 
	// the last time it was this players turn 
	private boolean isKO() {
		// If there are enough boards in the history

		// Printout the current board

		// Printout the old board 

		// Compare the old board to the new board and return true if they match

		// If the move is not allowed make sure to undo the move 

		return false; 
	}

	// Saves a copy of the board in board history 
	private void boardSave(){

		Piece[][] render; // something missing on this line
		Piece[][] copy = new Piece[7][7];
		//make a deep copy of the render array using a for loop

		// add the copy to the board history, 
		// you have have the current board in the board history if you want but 
		// make sure you know what index it is in.
	}

	// Reverts to previous board
	public void undo() {
		// Revert to a previous board

		// If you disconnect the render from the board you will have to remove 
		// old pieces and add the new pieces again

		// Change some game variables if required
	}

// ************ CAPTURE FUNCTIONS  ************************************	

	// Attempts to capture neighbouring opponent groups 
	private boolean capture(int player, int x, int y) {
		// Place the piece
		//this.placePiece(x,y);
		

		// Call captureAndIncrementScore in all for 4 directions provided there is a 
		// opposing piece there. 
		//Piece currPiece = goBoard.getRender()[x][y];
		if (capture(playerCurrent,x, y-1)) {
			//captureAndIncrementScore();
		}
		//captureAndIncrementScore() ;

		// If you didn't capture reset the piece. 

		return false; 
	}

	// Attempt to capture a group in this direction and update the scores
	private void captureAndIncrementScore(boolean captured, int x, int y){

		// Make a PiecesString starting with the opponents piece
		PiecesString capturePieces = new PiecesString(playerOpposing);

		// Call piecesStringAddNeighbours() to add all the neighbouring opponent pieces  
		piecesStringAddNeighbours(x, y, capturePieces);

		// If the piecesString has no liberties capture it and update scores
		if (!capturePieces.hasLiberty()) {
			//capture
			for(int i = 0; i < capturePieces.size(); i++) {
				capture(playerOpposing, capturePieces.get(i).getX(), capturePieces.get(i).getY());
				//update score
				updateScores();
			}
		}
	}

	// The most important function 
	// Add the neighbours of the same player to the PiecesString
	public void piecesStringAddNeighbours(int x, int y, PiecesString piecesString){
		
		
//		PiecesString ps = piecesString;
		
		

		// Attempt to add non empty neighbours of the same player type 
		// to the PiecesString in all 4 directions
		
		// UP x, y-1
		Piece currPiece = goBoard.getRender()[x][y];
		
		
//		for(int i = 0; i<=ps.size(); i++) {
			//Piece currentPiece = ps.get(i);
			//System.out.println("test2" + goBoard.getRender()[x][y]);
		// UP	
		if (goBoard.getPiece(x, y-1) != -1) {
				ps.add(goBoard.getRender()[x][y-1]);
				currPiece = goBoard.getRender()[x][y-1];
				System.out.println("test - up");
		}
		// RIGHT
		if (goBoard.getPiece(x+1, y) != -1) {
			ps.add(goBoard.getRender()[x+1][y]);
			currPiece = goBoard.getRender()[x+1][y];
			System.out.println("test - right");
		}
		
		// DOWN
		if (goBoard.getPiece(x, y+1) != -1) {
			ps.add(goBoard.getRender()[x][y+1]);
			currPiece = goBoard.getRender()[x][y+1];
			System.out.println("test - down");
		}
		
		//LEFT
		if (goBoard.getPiece(x-1, y) != -1) {
			ps.add(goBoard.getRender()[x-1][y]);
			currPiece = goBoard.getRender()[x-1][y];
			System.out.println("test - left");
		}

			

			
			
		//}
		System.out.println("PiecesString is: " + piecesString.getPiecesString());
		System.out.println();
//		if (goBoard.getRender()[x-1][y].getPlayer() == playerCurrent){
//			ps.add(goBoard.getRender()[x-1][y]);
//			
//		}
		
		

	}


// ************ SUICIDE FUNCTIONS  ************************************	

	// Place a piece of this player in position x , y. If it is part of a group
	// with no liberties then this is a suicide move.
	private boolean isSuicide(int x, int y) {
		// Place a piece of this player in x , y 

		// Find out if it is part of a group with no liberties by calling piecesStringHasLiberty()

		// make sure to reset the piece if it is a suicide move

		return false; 
	}

	// NB this function also uses piecesStringAddNeighbours() in 
	// the above section
	public boolean piecesStringHasLiberty(int player, int x, int y){
		// Place piece of this player

		// Create a PiecesString starting with this piece

		// Repeatedly call piecesStringAddNeighbours() to make its group

		// return if it has liberties or not

		return false; 
	}

// ************ PRETTY PRINT FUNCTION  ************************************

	// Return Renders(board history) as a String
	public String rendersToString(){
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i<this.renders.size(); i++){
			sb.append("\nRenders ").append(i).append(renders.get(i)).append("\n");
			sb.append(GoBoard.renderToString(renders.get(i)));
		}
		return sb.toString();
	}

// ************ BINDING FUNCTIONS   ************************************	

	// This method is called when binding the SimpleIntegerProperty scoreProperty in this class to the TextField tf_score in controlPanel
	public IntegerProperty getScore() {
		return scoreProperty;
	}

	public IntegerProperty getCurrentPlayer() {
		return this.currentPlayerProperty;
	}

// ************ ADVANCED FUNCTIONS  ************************************	
	// private method to allow players to pass
	public void pass(){
		/// do some work

		// see if the game is done
		this.determineEndGame();
	}

	// Updates the player's scores
	private void updateScores() {
		if (playerCurrent == 1) {
			scorePlayer1++;
		}
		else {
			scorePlayer2++;
		}
		// update the score, if you are doing the integerProperty binding you might do it here.
		// but is is best up update the 
	}

	// Determines if the end of the game has been reached
	private void determineEndGame() {
		/// have each of the players passed in succession

		determineWinner();
	}

	// Private method to determine if a player has a moves available 
	// (advanced so returns true by default)
	private boolean canMove() {
		return true;
	}

	// private method that determines who won the game
	private void determineWinner() {
		// what is the prisoner score

		// what is the territory score (advanced) 

		// update the variables

		// show who the winner is
	}


}
