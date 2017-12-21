package _2017._09._assignments.projectgo.template.v2;

import java.util.ArrayList;

public class PiecesString{
	
	public PiecesString(int player){		
		this.piecesString = new ArrayList<Piece>();
		this.player = player; 
		this.hasLiberty = false;  
		System.out.println("PiecesString created");
	}
	
	public void add(Piece piece){
		//System.out.println("Trying to add "+piece);
		// if piece same player
		if(piece.getPlayer()==this.player)
			//if not in piecesString 
			if(!this.piecesString.contains(piece)){
				//add piece to piecesString 
				this.piecesString.add(piece);
				//output added piece
				System.out.println(piece+" added");
				// output the piecesString
				System.out.println("add() - Pieces Group: "+this.piecesString);
			}
			else //{}
				System.out.println(piece+" already added");
		// else if empty  
		else if(piece.getPlayer()==0){
			// output group has liberty
			System.out.println("group has liberty");
			// update hasLiberty to true
			this.hasLiberty=true;
			System.out.println(this.hasLiberty);//test
			
		}
		// else (piece opposite colour)
		else
			// output piece is opposite player
			System.out.println(piece+" is oppsite player");
	}
	
	public ArrayList<Piece> getPiecesString(){
		return this.piecesString;
	}
	
	
	public Piece get(int index){
		return this.piecesString.get(index);
	}
	
	public int size(){
		return this.piecesString.size();
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public boolean getHasLiberty() {
		//System.out.println("Method Liberty: " + hasLiberty);
		return this.hasLiberty;
	} 
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i< this.piecesString.size(); i++){
			Piece current_piece = this.get(i);
			sb.append("[").append(current_piece.getX()).append(",").append(current_piece.getY()).append("]");
		}
		sb.append("hasLibery:").append(this.hasLiberty);
		return sb.toString();
	}
	
	private ArrayList<Piece> piecesString;
	private int player;
	private boolean hasLiberty;
	
}
