package gogame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GoGameLogic {
	
	private Integer score; 
	private IntegerProperty scoreProperty; 
	private GoBoard goBoard;
	
	
	// the current player who is playing and who is his opposition
	public static int current_player;
	public static int opposing;

	// is the game currently in play
	private boolean in_play;
	// current scores of player 1 and player 2
	private int player1_score;
	private int player2_score;
	
	private int[][] surrounding;
	private boolean[][] can_reverse;
	
	
	public GoGameLogic(GoBoard goBoard) {
		super();
		this.goBoard = goBoard;
		this.score = 1;
		
		surrounding = new int[3][3];
		can_reverse = new boolean[3][3];

		resetGame();
		
		//Making a SimpleIntegerProperty which will bind to the TextField in the controlPanel
		this.scoreProperty = new SimpleIntegerProperty(this.score);
	}

	public GoBoard getBoard() {
		return goBoard;
	}

	public void resetGame() {
		goBoard.resetRenders();
	
		in_play = true; 
		current_player = 1;
		opposing = 2;
		player1_score = 0;
		player2_score = 0;
		canMove(); //call can move to display the viable moves in grey on reset
	}
		
	
	
	// public method that will try to place a piece in the given x,y coordinate

	public void placePiece(double x, double y) {
		
	  // figure out which cell the current player has clicked on
	  final int cellx = (int) (x / goBoard.cell_width);
	  final int celly = (int) (y / goBoard.cell_height);
	  System.out.println("GameLogic" );

	  // if the game is not in play then do nothing
	  if(!in_play)
	    return;
	  
	  // ====== Steps =======
	  
	  // isEmpty
	  System.out.println("------------1------------" );
	  
	  if(goBoard.getRenderPos(cellx, celly) != 0)
	  	return;
	  
	  	// Capture
	  System.out.println("------------2-----------" );	
  		//capture(cellx , celly);

	  // Not Suicide
	  System.out.println("------------3-----------" );
	  
	  determineSurrounding(cellx, celly);
	  if(adjacentOpposingPiece())
	  		return;

	  // Not KO
	  System.out.println("-------------4-----------" );

		  // IF is Capture
	  System.out.println("-------------4.1-----------" );

		  // ELSE Not Suicide
	  System.out.println("-------------4.2-----------" );

	  System.out.println("-------------5-----------" );

	  placeAndReverse(cellx, celly);
		
	  // print out some information
	  System.out.println("---------- INFO --------" );
	  System.out.println("placed at: " + cellx + ", " + celly);
	  System.out.print("::: SCORES: " );
	  System.out.println("W:" + player1_score + " /  B:" + player2_score);
	  System.out.println("------------------------" );
	  System.out.println();
	  System.out.println();
	  
	  swapPlayers();

	  this.score++;
	  //Update the SimpleIntegerProperty scoreProperty when you update the int score so that the TextField tf_score in the GoControlPanel updates automatically
	  this.scoreProperty.setValue(this.score);	  

	  // determine what pieces surround the current piece. if there is no opposing
	  // pieces then a valid move cannot be made.
	  

	//  if(!adjacentOpposingPiece())
	//  return;

	  // see if a reverse can be made in any direction if none can be made then return
	// if(!determineReverse(cellx, celly))
	// return;
		

	  // at this point we have done all the checks and they have passed so now we can place
	  // the piece and perform the reversing
	  
	  
	  /*if(goBoard.getRenderPos(cellx, celly) != 0)
		  capture(cellx , celly);
	  else*/
	 
	  
	  //if we get to this point then a successful move has been made so swap the
	  //players and update the scores
	  //updateScores();
	  //showMoves();
	  //determineEndGame(); //check endGame after printing scores

		
	}

//================== New Code ==================================	
	private void capture(final int x , final int y) {
		goBoard.setRenderPos(x, y);
	}
	
	private void determineSurrounding(final int x, final int y) {
		for(int i=0; i<surrounding.length; i++) {
	        for(int j=0; j<surrounding[i].length; j++) {
	            surrounding[i][j] = goBoard.getRenderPos(x-(i-1), y-(j-1));    
	            System.out.println(i +" , "+ j);
	            System.out.println(surrounding[i][j]);
	        }
	    }	
		// surrounding array
		// (x-1, y-1  | x, y-1 | x+1, y-1)
		// (x-1, y    | x, y   | x+1, y)
		// (x-1, y+1  | x, y+1 | x+1, y+1) 
		// x -> Columns, y -> Rows
	}
	
	// private method for determining if any of the surrounding pieces are an opposing
	// piece. if a single one exists then return true otherwise false
	private boolean adjacentOpposingPiece() {
	
	// determine if suicide
	if(surrounding[0][1] == opposing && surrounding[1][0] == opposing 
    		&& surrounding[2][1] == opposing && surrounding[1][2]== opposing) {
    	System.out.println("Suicide: Cannot Move");
    	return true;
	}
	
	//hardcode - Pieces at top, bottom, left and right only
	//if(surrounding[0][1] == opposing) return true; 
	//else if(surrounding[1][0] == opposing) return true;
	//else if(surrounding[1][2] == opposing) return true;
	//else if(surrounding[2][1] == opposing) return true;
						
	/*check all surrounding pieces (inc diagonal)
	for(int i = 0; i<surrounding.length; i++) {
		for(int j = 0; j<surrounding[i].length; j++) {
			if(surrounding[i][j] == opposing) return true;
		}
	}*/
	return false;
}
	
	//========================================================================
	
	
	
	// This method is called when binding the SimpleIntegerProperty scoreProperty in this class to the TextField tf_score in controlPanel
	public IntegerProperty getScore() {
		return scoreProperty;
	}
	
	// private method for placing a piece and reversing pieces
	public void placeAndReverse(final int x, final int y) {
		//place		
		//System.out.println(x + " , " + y);
		goBoard.placePiece(x , y);
		
//		//reverse
//		for(int i=0; i<can_reverse.length; i++) {
//			for(int j=0; j<can_reverse[i].length; j++) {
//				if(can_reverse[i][j]) reverseChain(x, y, j-1, i-1);
//			}
//		}	
	}
	
	
	//private method for swapping the players
	public void swapPlayers() {
		  int temp = current_player;
		  current_player = opposing;
		  opposing = temp;
	}
	
				
	// private method to determine if a player has a move available + SHOW VIABLE MOVES IN GREY
	private boolean canMove() {
		boolean canmove = true;

		/*for(int i=0; i<render.length; i++) {
	        for(int j=0; j<render[i].length; j++) {
	            if(render[i][j].getPiece() == 0) { // is empty space?
    			render[i][j].setPiece(0); //reset previous pieces
    			determineSurrounding(i, j); 
        		if(adjacentOpposingPiece() && determineReverse(i,j)) { //has adjacent and viable reverse?
        			//render[i][j].setPiece(3); //set empty spaces that are viable moves to grey
            			canmove = true;
            		}
	            }
	        }
	    }*/
		return canmove;
	}
	
}

/*			
			// private method for updating the player scores
			private void updateScores() {
				player1_score = 0;
				player2_score = 0;
				for(int i = 0; i<render.length; i++) {
					for(int j = 0; j<render[i].length; j++) {
						if(render[i][j].getPiece() == 1) player1_score++; //add to p1score
						else if(render[i][j].getPiece() == 2) player2_score++; //add to p2score
					}
				}
			}

			
			
			// private method for determining which pieces surround x,y will update the
			// surrounding array to reflect this
			
			
			// private method for determining if a reverse can be made will update the can_reverse
			// array to reflect the answers will return true if a single reverse is found
			private boolean determineReverse(final int x, final int y) {
				boolean checkrev = false;
			
				for(int i=0; i<can_reverse.length; i++) {
			        for(int j=0; j<can_reverse[i].length; j++) {
			        		can_reverse[i][j] = isReverseChain(x, y, (j-1), (i-1), current_player);
			        		if(can_reverse[i][j]) checkrev = true;
			        }
			    }
				// Directions
				// UPLEFT, UP, UPRIGHT   (x-1, y-1) | (x, y-1) | (x+1, y-1)       //COLUMN, ROW
				// LEFT, RIGHT           (x-1, y)   | PIECE    | (x+1, y)
				// DOWNLEFT, DOWN, RIGHT (x-1, y+1) | (x, y+1) | (x+1, y+1)

				return checkrev; 
			}
			
			// private method for determining if a reverse can be made from a position (x,y) for
			// a player piece in the given direction (dx,dy) returns true if possible
			// assumes that the first piece has already been checked
			private boolean isReverseChain(final int x, final int y, final int dx, final int dy, final int player) {
				int cx = x; 
				int cy = y; 
				boolean check1 = true; //boolean, while the next piece is an opposing piece
				int count = 0;
				
				while(check1) {
					if(getPiece(cx+dx, cy+dy) != opposing) check1 = false;
					else {
						cx = cx+dx; cy = cy+dy;
						count++;
					}
				}
				
				if(count>0 && getPiece(cx+dx, cy+dy) == current_player) {
					//System.out.println("Holy crap it worked");
					return true;
				}
				return false;
			}
			
			
			
		
			
			// private method to reverse a chain
			private void reverseChain(final int x, final int y, final int dx, final int dy) {
				int currx = x;
				int curry = y;
				boolean gonext = true; // while the next piece is an opposing piece, keep going

				while(gonext) {
					if(getPiece(currx+dx, curry+dy) != opposing) gonext = false;
					else {
						render[currx+dx][curry+dy].swapPiece();
						//render[currx+dx][curry+dy] = new ReversiPiece(current_player); // test
						//getChildren().add(render[currx+dx][curry+dy]); //test
						currx = currx+dx; curry = curry+dy;
					}
				}
				
				if(getPiece(currx+dx, curry+dy) == current_player) {
					return;
				}
			}
			
			// private method for getting a piece on the board. this will return the board
			// value unless we access an index that doesnt exist. this is to make the code
			// for determing reverse chains much easier
			private int getPiece(final int x, final int y) {
				if(x <6 && x >=0 && y >= 0 && y<6) {
					return render[x][y].getPiece();
				}
				return -1;
			}
			
			// private method that will determine if the end of the game has been reached
			private void determineEndGame() {
				//no empty cells on board
				int emptycells = 0;
				for(int i=0; i<render.length; i++) {
			        for(int j=0; j<render[i].length; j++) {
			        		if(render[i][j].getPiece() == 0) emptycells++; //count empty spaces
			        }
			    }
				if (emptycells == 0) {
					System.out.println("   No more empty spaces.");
					System.out.println();
					System.out.println("   *********** GAME OVER ***************");
					determineWinner();
					in_play = false;
				}
				//player has lost all pieces
				else if(player1_score == 0 || player2_score == 0) {
					System.out.println("   *********** GAME OVER ***************");
					determineWinner();
					in_play = false;
				}
				//player cannot make move
				else if(!canMove()) { //current player can't move
					System.out.println("No moves for Player " + current_player);
					swapPlayers();
					if(!canMove()) { //both players cannot make move
						System.out.println("   No possible moves for both players.");
						System.out.println();
						System.out.println("   *********** GAME OVER ***************");
						determineWinner();
						in_play = false;
					}
				}
			}*/
			
		
			
			/*
			// private method that determines who won the game
			private void determineWinner() {
				 //glow for label
			      Glow glow = new Glow(); 
			      glow.setLevel(0.9);
			      winnerLabel.setEffect(glow); 

				if(player1_score > player2_score) { 
					System.out.println("    ---- White WINS! ----"); 
					winnerLabel.setText("  ** White Wins! **");
					}
				else if(player1_score < player2_score) { 
					System.out.println("    ---- Black WINS! ----");
					winnerLabel.setText("  ** Black Wins! **");
				}
				else { 
					System.out.println("    ----  DRAW  ----");
					winnerLabel.setText("  ** DRAW **");		
				}
				getChildren().add(winnerLabel);
			}	

			
		}

*/
