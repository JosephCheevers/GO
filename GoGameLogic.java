package _2017._09._assignments.projectgo.template.v2;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.Media; 
import javafx.scene.media.MediaPlayer; 
import java.io.File;

public class GoGameLogic {


	// ************ PRIVATE MEMBER DATA  ************************************

	
	private int playerCurrent; // the current player
	private int playerOpposing; // the opposing player

	private boolean in_play;  // is the game currently in play
	private int scorePlayer1; // score of player1
	private int scorePlayer2; // score of player2
	private int passesPlayer1;// passes by player1
	private int passesPlayer2;// passes by player2

	// properties for binding
	private IntegerProperty scoreProperty1;
	private IntegerProperty scoreProperty2;  
	private SimpleIntegerProperty currentPlayerProperty; 



	private ArrayList<Piece [][]> renders; // board history
	private int renderCurrent; // index of the board currently in use in the board array
	//public PiecesString ps;
	
	private boolean consecutiveSkip; //boolean for passing
	
	// music file for stone placement
	String musicFile = "placeStone.mp3"; // For example 
	Media sound = new Media(new File(musicFile).toURI().toString()); 
	MediaPlayer mediaPlayer = new MediaPlayer(sound);  
	
	private GoBoard goBoard;  // reference to the GoBoard
		
	public GoGameLogic(GoBoard goBoard) {
		super();
		this.goBoard = 	goBoard;
		//this.score = 1;
		this.playerCurrent = 1;
		consecutiveSkip = false; //two consecutive skips
		
		//Making a SimpleIntegerProperty which will bind to the TextField in the controlPanel
		//scores
		this.scoreProperty1 = new SimpleIntegerProperty(this.scorePlayer1);
		this.scoreProperty2 = new SimpleIntegerProperty(this.scorePlayer2);
		//player
		this.currentPlayerProperty = new SimpleIntegerProperty(this.playerCurrent);
		//territory
//		this.territoryProperty1 = new SimpleIntegerProperty(this.territoryP1);
//		this.territoryProperty1 = new SimpleIntegerProperty(this.territoryP1);
		
		//piececolor
		//ps = new PiecesString(playerCurrent); // set color with this piece
		
		this.resetGame();
	} 

	public void resetGame() {
		System.out.println("Game Reset");
		// reset the render
		goBoard.resetRenders();
		
		in_play = true; 
		
		// reset the variables monitoring the game
		playerCurrent = 1;
		playerOpposing = 2;
		//score = 0;
		scorePlayer1 = 0; 
		scorePlayer2 = 0; 
		passesPlayer1 = 0;
		passesPlayer2 = 0;
		consecutiveSkip = false;
		canMove(); 
		
		GoControlPanel.stoneColour(playerCurrent);
		
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
		mediaPlayer.stop();
		System.out.println();
		System.out.println("tryPlacePeice()*******************************");

		// Determine which cell the current player has clicked on
		final int cellx = (int) (x / this.getGoBoard().getCell_width());
		final int celly = (int) (y / this.getGoBoard().getCell_height());

		// if the game is not in play exit method
		System.out.println("in_play:"+in_play);
		if(!in_play)
			return;

		// if piece is empty exit method
		if(goBoard.getRender()[cellx][celly].getPlayer() != 0)
			return;
		
		// Attempt to capture 
		this.capture(this.playerCurrent,cellx, celly);
		
		//piecesStringAddNeighbours(cellx,celly,ps);
		

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
		
		checkSkip();
		
		System.out.println("P1 score: " + scorePlayer1);
		System.out.println("P2 score: " + scorePlayer2);

		// Default case - place the piece
		this.placePiece(cellx, celly);
		
		// increment score if stone placed
		if(playerCurrent == 1) scorePlayer1++;
		else scorePlayer2++;
		
		//Update the SimpleIntegerProperty scoreProperty when you update the int score so that the TextField tf_score in the GoControlPanel updates automatically
		this.scoreProperty1.setValue(this.scorePlayer1);
		this.scoreProperty2.setValue(this.scorePlayer2);
		
		System.out.println("P1 score: " + scorePlayer1);
		System.out.println("P2 score: " + scorePlayer2);
		

		// if we get to this point then a successful move has been made so swap the
		// players and update the scores
		swapPlayers();
		boardSave();
		//updateScores(); // NB captureAndIncrement() will update some of the score values
		determineEndGame();

		// Print out information on game status
		
		System.out.println("----------------------------------------");
		System.out.println("Status: " );
		System.out.println("P1 Score: " + scorePlayer1);
		System.out.println("P2 Score: " + scorePlayer2);
		System.out.println("game over?  " + !in_play);
		System.out.println("----------------------------------------");
	}

	// Places a piece 
	private void placePiece(final int x, final int y) {
		goBoard.getRender()[x][y].setPlayer(playerCurrent); 
		mediaPlayer.play(); //play sound if piece placed
		

		//if the cell is empty update the relevant render
	}

	// private method for swapping the players
	public void swapPlayers() {
		// swap the players
		 int temp = playerCurrent;
		 playerCurrent = playerOpposing;
		 playerOpposing = temp;
		  
		 GoControlPanel.stoneColour(playerCurrent);
		this.currentPlayerProperty.setValue(playerCurrent);
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

//	// Attempts to capture neighbouring opponent groups 
//	private boolean capture(int player, int x, int y) {
//		boolean captured = false;
////		// Place the piece
////		//this.placePiece(x,y);
////		
////		System.out.println("FUCK: " + x + "," + y);
////		
////
////		// Call captureAndIncrementScore in all for 4 directions provided there is a 
////		// opposing piece there. 
////		//Piece currPiece = goBoard.getRender()[x][y];
//////		if(x>=0 && x<7 && y>=0 && y<7) {
////			System.out.println("capture GONE IN");
////			
////			
////			// not working
////			//UP
////			if (x>=0 && x<7 && y-1>=0 && y-1<7 && goBoard.getRender()[x][y-1].getPlayer() == playerOpposing) { //breaks
////				System.out.println("Capture: UP");
////				captureAndIncrementScore(captured, x, y-1);
////			}
////			
////			//RIGHT
////			if (x+1>=0 && x+1<7 && y>=0 && y<7 && goBoard.getRender()[x+1][y].getPlayer() == playerOpposing) {
////				System.out.println("Capture: RIGHT");
////				captureAndIncrementScore(captured, x+1, y);
////			}
////			
////			//DOWN
////			if (x>=0 && x<7 && y+1>=0 && y+1<7 && goBoard.getRender()[x][y+1].getPlayer() == playerOpposing) {
////				System.out.println("Capture: DWN");
////				captureAndIncrementScore(captured, x, y+1);
////			}
////			
////			//LEFT
////			if (x-1>=0 && x-1<7 && y>=0 && y<7 && goBoard.getRender()[x-1][y].getPlayer() == playerOpposing) {
////				System.out.println("Capture: LFT");
////				captureAndIncrementScore(captured, x-1, y);
////			}
//		//}
//
//
//
//		// If you didn't capture reset the piece. 
//
//		return captured; 
//	}
	
	// Attempts to capture neighbouring opponent groups 
		private boolean capture(int player, int x, int y) {
			boolean captured = false;
			// Place the piece
			this.placePiece(x,y);
			System.out.println();
//			System.out.println("=========== Capture "+ player + "===========");
//			System.out.println("FUCK: " + x + "," + y);
			

			// Call captureAndIncrementScore in all for 4 directions provided there is a 
			// opposing piece there. 	
//			System.out.println("capture GONE IN");	
			
			//UP
			if (goBoard.getPiecePlayer(x, y-1) == playerOpposing) { //breaks
//				System.out.println("Capture: UP");
				//capture if no liberties? 
				captureAndIncrementScore(false, x, y-1);
			}
			
			//RIGHT
			if (goBoard.getPiecePlayer(x+1, y) == playerOpposing) { //breaks
//				System.out.println("Capture: RIGHT");
				captureAndIncrementScore(false, x+1, y);
			}
			//DOWN
			if (goBoard.getPiecePlayer(x, y+1) == playerOpposing) { //breaks
//				System.out.println("Capture: DWN");
				captureAndIncrementScore(false, x, y+1);
			}
			//LEFT
			if (goBoard.getPiecePlayer(x-1, y) == playerOpposing) { //breaks
//				System.out.println("Capture: LFT");
				captureAndIncrementScore(false, x-1, y);

			}
			
//			System.out.println("=========== Capture End ===========");
//			System.out.println();

			// If you didn't capture reset the piece. 
			return captured; 
		}

	// Attempt to capture a group in this direction and update the scores
	private void captureAndIncrementScore(boolean captured, int x, int y){

		// Make a PiecesString starting with the opponents piece
		PiecesString capturePieces = new PiecesString(playerOpposing);
		Piece currPiece = goBoard.getPiece(x, y);
		capturePieces.add(currPiece);
		
		// loop check up down left right
		// if opposing, add to list
		// when captured, set captured to true
		// boolean for liberties
		

		for(int i = 0; i < capturePieces.size(); i++) {
			Piece currentPiece = capturePieces.get(i);
			//System.out.println("test2" + goBoard.getRender()[x][y]);
			int tempx = currentPiece.getX();
			int tempy = currentPiece.getY();
			//int tempPlayer = currentPiece.getPlayer();
			// UP	
			if (tempy-1 >= 0 && tempy-1 <7 && goBoard.getPiecePlayer(tempx, tempy-1) == playerOpposing) {
					capturePieces.add(goBoard.getPiece(tempx,tempy-1));
	//				currPiece = goBoard.getRender()[x][y-1];
					//capturePiecesAddNeighbours(currPiece.getX(), currPiece.getY(), capturePieces);
					//System.out.println("test - up");
			}
			// RIGHT
			if (tempx+1 >= 0 && tempx+1 < 7 && goBoard.getPiecePlayer(tempx+1, tempy) == playerOpposing) {
				capturePieces.add(goBoard.getPiece(tempx+1,tempy));
	//			currPiece = goBoard.getRender()[x+1][y];
				//System.out.println("test - right");
			}
			
			// DOWN
			if (tempy+1 >= 0 && tempy+1 <7 && goBoard.getPiecePlayer(tempx, tempy+1) == playerOpposing) {
				capturePieces.add(goBoard.getPiece(tempx, tempy+1));
	//			currPiece = goBoard.getRender()[x][y+1];
				//System.out.println("test - down");
			}
			
			//LEFT
			if (tempx-1 >= 0 && tempx-1 < 7 && goBoard.getPiecePlayer(tempx-1, tempy) == playerOpposing) {
				capturePieces.add(goBoard.getPiece(tempx-1,tempy));
	//			currPiece = goBoard.getRender()[x-1][y];
				//System.out.println("test - left");
			}
		}

				

		// Call piecesStringAddNeighbours() to add all the neighbouring opponent pieces  
		for(int i = 0; i < capturePieces.size(); i++) {
			piecesStringAddNeighbours(capturePieces.get(i).getX(), capturePieces.get(i).getY(), capturePieces);
		}
		
		System.out.println("Capture String : " + capturePieces);
		
		boolean hasLiberty = false;
		captured = false;
		
		for(int i = 0; i < capturePieces.size(); i++) {
			Piece currentPiece = capturePieces.get(i);
			//System.out.println("test2" + goBoard.getRender()[x][y]);
			int tempx = currentPiece.getX();
			int tempy = currentPiece.getY();
			
			//UP
			if (goBoard.getPiecePlayer(tempx, tempy-1) == 0) {
				hasLiberty = true;
			}
			// RIGHT
			if (goBoard.getPiecePlayer(tempx+1, tempy) == 0) {
				hasLiberty = true;
			}
			
			// DOWN
			if (goBoard.getPiecePlayer(tempx, tempy+1) == 0) {
				hasLiberty = true;
			}
			
			//LEFT
			if (goBoard.getPiecePlayer(tempx-1, tempy) == 0) {
				hasLiberty = true;
			}
		}
			//if no liberty, reset pieces 
			if (!hasLiberty) {
				captured = true;
				for(int i = 0; i < capturePieces.size(); i++) {
					goBoard.setPiece(capturePieces.get(i).getX(), capturePieces.get(i).getY(), 0);
					// add to score
					
				}
				updateScores(capturePieces.size()); //change scores according to how many in capture list
				captured = true;
			}
			captured = true;
			
			
			
		
//
//		// If the piecesString has no liberties capture it and update scores
//		if (!capturePieces.getHasLiberty()) {
//			System.out.println("NO LIBERTY");
//			//capture
//			for(int i = 0; i < capturePieces.size(); i++) {
//				//capture(playerOpposing, capturePieces.get(i).getX(), capturePieces.get(i).getY());
//				System.out.println("Went into loop. Should reset piece");
//				goBoard.getRender()[capturePieces.get(i).getX()][capturePieces.get(i).getY()].setPlayer(0);
//				System.out.println("Piece in capturepieces: " + capturePieces.get(i));
//				System.out.println("Piece captured.");
//				//update score
//				updateScores();
//				//captured = true;
//			}
//			System.out.println("Finished loop.");
//			System.out.println("***** list is: " + capturePieces);
//			System.out.println(" --- END CAPTINCREM METHOD ---");
//		}
	}

	// The most important function 
	// Add the neighbours of the same player to the PiecesString
	public void piecesStringAddNeighbours(int x, int y, PiecesString piecesString){
		
		// Attempt to add non empty neighbours of the same player type 
		// to the PiecesString in all 4 directions

		//Piece currPiece = goBoard.getRender()[x][y];
		
		
		for(int i = 0; i < piecesString.size(); i++) {
			Piece currentPiece = piecesString.get(i);
			int tempx = currentPiece.getX();
			int tempy = currentPiece.getY();
			//int tempPlayer = currentPiece.getPlayer();

			// UP	
			if (goBoard.getPiecePlayer(tempx, tempy-1) != -1) {
					piecesString.add(goBoard.getPiece(tempx,tempy-1));
			}
			// RIGHT
			if (goBoard.getPiecePlayer(tempx+1, tempy) != -1) {
				piecesString.add(goBoard.getPiece(tempx+1,tempy));
			}
			
			// DOWN
			if (goBoard.getPiecePlayer(tempx, tempy+1) != -1) {
				piecesString.add(goBoard.getPiece(tempx,tempy+1));
			}
			
			//LEFT
			if (goBoard.getPiecePlayer(tempx-1, tempy) != -1) {
				piecesString.add(goBoard.getPiece(tempx-1,tempy));
			}
		}

			
			
		//}
//		System.out.println("PiecesString is: " + piecesString.getPiecesString());
//		System.out.println();
//		if (goBoard.getRender()[x-1][y].getPlayer() == playerCurrent){
//			ps.add(goBoard.getRender()[x-1][y]);
//			
//		}
		
		

	}


// ************ SUICIDE FUNCTIONS  ************************************	

	// Place a piece of this player in position x , y. If it is part of a group
	// with no liberties then this is a suicide move.
	private boolean isSuicide(int x, int y) {
		
		goBoard.setPiece(x, y, playerCurrent);
		
//		//UP
//		if (goBoard.getPiecePlayer(x, y-1) == playerCurrent) { //breaks
//			piecesStringHasLiberty(x, y-1, playerCurrent);
//		}
//		
//		//RIGHT
//		if (goBoard.getPiecePlayer(x+1, y) == playerCurrent) { //breaks
//			piecesStringHasLiberty(x+1, y, playerCurrent);
//		}
//		//DOWN
//		if (goBoard.getPiecePlayer(x, y+1) == playerCurrent) { //breaks
//			piecesStringHasLiberty(x, y+1, playerCurrent);
//		}
//		//LEFT
//		if (goBoard.getPiecePlayer(x-1, y) == playerCurrent) { //breaks
//			piecesStringHasLiberty(x-1, y, playerCurrent);
//
//		}

		//System.out.println();
		//System.out.println("***********Suicide check ***********");

		// Find out if it is part of a group with no liberties by calling piecesStringHasLiberty()
		boolean isSuicide = !piecesStringHasLiberty(playerCurrent, x, y);

		//System.out.println("isSuicide: " + isSuicide);

		// make sure to reset the piece if it is a suicide move
		if (isSuicide == true) goBoard.setPiece(x, y, 0); 

		return (isSuicide);  
		
	}

	// NB this function also uses piecesStringAddNeighbours() in 
	// the above section
	public boolean piecesStringHasLiberty(int player, int x, int y){
		// Place piece of this player
		//goBoard.setPiece(x, y, player);//put stuff
		
		Piece currentPiece = goBoard.getPiece(x, y);
			
		// Create a PiecesString starting with this piece
		PiecesString suicideString = new PiecesString(playerCurrent); // set color with this piece
		suicideString.add(currentPiece);

		// Repeatedly call piecesStringAddNeighbours() to make its group
		//for loop i < size
		

//		for (int i = 0; i < suicideString.size(); i++) {
//			Piece tempPiece = suicideString.get(i);
//			//System.out.println("test2" + goBoard.getRender()[x][y]);
//			int tempx = tempPiece.getX();
//			int tempy = tempPiece.getY();
//		
//			System.out.println("Loop");
//			
//			//UP
//			if (goBoard.getPiecePlayer(x, y-1) == playerCurrent) { //breaks
//				suicideString.add(goBoard.getPiece(tempx,tempy-1));
//			}
//			
//			//RIGHT
//			if (goBoard.getPiecePlayer(x+1, y) == playerCurrent) { //breaks
//				suicideString.add(goBoard.getPiece(tempx+1,tempy));
//			}
//			//DOWN
//			if (goBoard.getPiecePlayer(x, y+1) == playerCurrent) { //breaks
//				suicideString.add(goBoard.getPiece(tempx, tempy+1));
//			}
//			//LEFT
//			if (goBoard.getPiecePlayer(x-1, y) == playerCurrent) { //breaks
//				suicideString.add(goBoard.getPiece(tempx-1,tempy));
//
//			}
//			
//		}
		
		// Repeatedly call piecesStringAddNeighbours() to make its group
		//for loop i < size
		for (int i = 0; i < suicideString.size(); i++) {
			piecesStringAddNeighbours(suicideString.get(i).getX(), suicideString.get(i).getY(), suicideString);
		}
		

		System.out.println("SuicideString: " + suicideString); // problem - gives false


		
		System.out.println(suicideString.getHasLiberty()); // problem - gives false
		
		// return if it has liberties or not
		return suicideString.getHasLiberty();
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
//	public IntegerProperty getScore() {
//		if(playerCurrent == 1) return scoreProperty1; //return p1's score
//		else return scoreProperty2; //return p2's score
//	}
	
	public IntegerProperty getP1Score() {
		return scoreProperty1; //return p1's score
	}
	public IntegerProperty getP2Score() {
		return scoreProperty2; //return p1's score
	}
	
//	public IntegerProperty getTerritory() {
//		if(playerCurrent == 1) return territoryProperty1; //return p1's territory
//		else return territoryProperty2; //return p2's territory
//	}

	public IntegerProperty getCurrentPlayer() {
		return this.currentPlayerProperty;
	}

// ************ ADVANCED FUNCTIONS  ************************************	
	// private method to allow players to pass
	public void pass(){
		/// do some work
		
		if(playerCurrent == 1) {
			passesPlayer1++; //increment pass for p1
			System.out.println("P1 has passed");
			System.out.println(passesPlayer1);
		}
		else {
			passesPlayer2++; // increment pass for p2
			System.out.println("P2 has passed");
			System.out.println(passesPlayer2);
		}
		swapPlayers(); //swap 
		

		// see if the game is done
		this.determineEndGame(); 
	}

	// Updates the player's scores //prisoners scores
	private void updateScores(int captured) {
		if (playerCurrent == 1) {
			scorePlayer1 += captured;
			scorePlayer2 -= captured; //stones removed(captured)
		}
		else {
			scorePlayer2 += captured;
			scorePlayer1 -= captured; //stones removed(captured)
		}
		// update the score, if you are doing the integerProperty binding you might do it here.
		// but is is best up update the 
	}
	
	private void checkSkip() {
		/// have each of the players passed in succession
				if(playerCurrent == 1 && passesPlayer1 > 0 && passesPlayer2 == 0) {
					if(consecutiveSkip) {
						System.out.println("*****reset*******");
						//reset
						passesPlayer1 = 0;
						consecutiveSkip = false;
					}
					//set consecutive
					else {
						consecutiveSkip = true;
					}
				}
				
				else if(playerCurrent == 2 && passesPlayer2 > 0 && passesPlayer1 == 0) {
					if(consecutiveSkip) {
						//reset
						passesPlayer2 = 0;
						consecutiveSkip = false;
					}
					//set consecutive
					else {
						consecutiveSkip = true;
					}
				}
	}

	// Determines if the end of the game has been reached
	private void determineEndGame() {
		/// have each of the players passed in succession
		if(playerCurrent == 1 && passesPlayer1 > 0 && passesPlayer2 == 0) {
			if(consecutiveSkip) {
				System.out.println("*****reset*******");
				//reset
				passesPlayer1 = 0;
				consecutiveSkip = false;
			}
			//set consecutive
			else {
				consecutiveSkip = true;
			}
		}
		
		else if(playerCurrent == 2 && passesPlayer2 > 0 && passesPlayer1 == 0) {
			if(consecutiveSkip) {
				//reset
				passesPlayer2 = 0;
				consecutiveSkip = false;
			}
			//set consecutive
			else {
				consecutiveSkip = true;
			}
		}
		
		if(passesPlayer2 > 0 && passesPlayer1 >0) {
			in_play = false;
			System.out.println("GAME OVER BITCH");
		}
		
		determineWinner();
	}

	// Private method to determine if a player has a moves available 
	// (advanced so returns true by default)
	private boolean canMove() {
		
		// idea: loop through pieces in board
		// check suicide, ko, empty in each
		// if valid move, return true, else return false
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
