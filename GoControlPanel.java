package _2017._09._assignments.projectgo.template.v2;


//import javafx.beans.binding.Bindings;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ObservableBooleanValue;
//import javafx.beans.value.ObservableObjectValue;
//import gogame.Hud.MenuItem;
//import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
//import gogame.GoPiece;

public class GoControlPanel extends Pane{

	private GoGameLogic goGameLogic;
	private TextField tf_score1, tf_score2, tf_territories; 
	private Label lbl_title, lbl_player1, lbl_player2, lbl_territories, lbl_score1, lbl_score2;

	private VBox vb; 
	private HBox territoriesBox, p1ScoreBox, p2ScoreBox, playerBox;
	static Ellipse gp  = new Ellipse(); 
	
	public GoControlPanel(GoGameLogic goGameLogic) {
		super();
		this.goGameLogic = goGameLogic;
		
		lbl_title = new Label("Control Panel");
		lbl_title.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		
	
		lbl_player1 = new Label("Turn:     Player ");
		lbl_player2 = new Label();
		
		Button reset_button = new Button("Reset Game");
		Button change_board_button = new Button("Change Board");
		Button skip_button = new Button("Skip Turn");
		Button menu_button = new Button("Rules");
		
		lbl_score1 = new Label("P1 Score:");
		tf_score1 = new TextField();
		tf_score1.setEditable(false);
		tf_score1.setPrefWidth(50);
		
		lbl_score2 = new Label("P2 Score:");
		tf_score2 = new TextField();
		tf_score2.setEditable(false);
		tf_score2.setPrefWidth(50);
		
		lbl_territories = new Label("Territories:");
		tf_territories = new TextField();
		tf_territories.setText("0");
		tf_territories.setEditable(false);
		tf_territories.setPrefWidth(50);
		
//		GoPiece gp = new GoPiece(1);
//		gp.setPiece(1);
		
//		GoPiece gp = new GoPiece(1);
//		gp = goGameLogic.getGoPiece();
		
		//gp = goGameLogic.getEllipse();
		gp.setCenterX(200); gp.setCenterY(200);
		gp.setRadiusX(30); gp.setRadiusY(30);
		
		//gp.setStyle("-fx-background-color: YELLOW");
		
		DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        
        gp.setEffect(ds);
		stoneColour(1);

		change_board_button.setOnAction(event -> {
            //root.setEffect(null);
            GoBoard.changeBackground();
	     });
		reset_button.setOnAction(event -> {
	          //root.setEffect(null);
			stoneColour(2);
	          goGameLogic.resetGame();
	     });
		
		skip_button.setOnAction(event -> {
          //root.setEffect(null);
			goGameLogic.pass();
	     });
		
		menu_button.setOnAction(event -> {
          //root.setEffect(null);
          popUpDialog();
	     });

		// Binding the SimpleIntegerProperty scoreProperty in GoGameLogic to the TextField tf_score
		this.lbl_player2.textProperty().bindBidirectional(this.goGameLogic.getCurrentPlayer(), new NumberStringConverter());
		this.tf_score1.textProperty().bindBidirectional(this.goGameLogic.getP1Score(), new NumberStringConverter());
		this.tf_score2.textProperty().bindBidirectional(this.goGameLogic.getP2Score(), new NumberStringConverter());
		//this.tf_territories.textProperty().bindBidirectional(this.goGameLogic.getPrisonersProperty(), new NumberStringConverter());
		//gp.fillProperty().bind(this.goGameLogic.getEllipse());
		//gp.styleProperty().bindBidirectional(this.goGameLogic.getStyle());
		
		//ObservableBooleanValue booleanCondition = goGameLogic.getEllipse();
		//final ObservableObjectValue<Paint> paintProperty = Bindings.when(booleanCondition).then(Color.RED).otherwise(Color.DODGERBLUE);

		this.vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(15.0);
		vb.setPrefWidth(175);
		
		
		playerBox = new HBox();
		playerBox.setAlignment(Pos.CENTER);
		playerBox.setSpacing(5);
		playerBox.getChildren().addAll(lbl_player1, lbl_player2); 
		
		territoriesBox = new HBox();
		territoriesBox.setAlignment(Pos.CENTER);
		territoriesBox.setSpacing(20);
		territoriesBox.getChildren().addAll(lbl_territories, tf_territories);
		
		p1ScoreBox = new HBox();
		p1ScoreBox.setAlignment(Pos.CENTER);
		p1ScoreBox.setSpacing(20);
		p1ScoreBox.getChildren().addAll(lbl_score1, tf_score1);
		
		p2ScoreBox = new HBox();
		p2ScoreBox.setAlignment(Pos.CENTER);
		p2ScoreBox.setSpacing(20);
		p2ScoreBox.getChildren().addAll(lbl_score2, tf_score2);
		
		//int curr_player = GoGameLogic.get_player();
		//changePlayerIcon(curr_player, gp);

		
		this.getChildren().add(vb);
		vb.getChildren().addAll (lbl_title,playerBox, gp, p1ScoreBox, p2ScoreBox, territoriesBox, reset_button, 
				skip_button,change_board_button, menu_button);
		
		//System.out.println("TEST PIECE" + gp);

	}	
	
	public static void stoneColour(int i) {
		if(i == 1)
			gp.setFill(Color.BLACK);
		else
			gp.setFill(Color.WHITE);	
	}
	
	
	
  
 private void popUpDialog(){
	   Alert rules = new Alert(AlertType.INFORMATION);
	   rules.setTitle("GO MENU");
	   rules.setHeaderText("The Rules of GO");
	   rules.setContentText("A game of Go starts with an empty board. This is a smaller 7x7 version. \n\n" + 
			   "Each player has an unlimited supply of stones, one takes the black stones, " + 
			   "the other takes the white. The main object of the game is to use your stones to " + 
	   			"form territories by surrounding vacant areas of the board. It is also possible to capture your opponent's " + 
	   			"stones by completely surrounding them. \n \n" +
	   			"- The empty points which are horizontally and vertically adjacent to a stone, or a solidly connected string"
	   			+ " of stones, are known as liberties. An isolated stone or solidly connected string of stones is captured "
	   			+ "when all of its liberties are occupied by enemy stones.\n \n"
	   			+ "- Stones occupying adjacent points constitute a solidly connected string. Diagonals do not count as connections. "
	   			+ "Several strings close together that belong to the same player are called a group. A string of stones is treated"
	   			+ " as a single unit and is captured when all of its liberties are occupied by enemy stones.\n\n"
	   			+ "- Suicide Rule: A player may not suicide (play a stone into a position where it would have no liberties) unless"
	   			+ " as a result, one or more of the stones surrounding it is captured. \n\n"
	   			+ " - KO Rule: A play is illegal if it would have the effect (after all steps of the play have been completed)"
	   			+ " of creating a position that has occurred previously in the game. \n \n"
	   			+ "Two Consecutive Skips ends the game. To end game press 'Skip Turn' twice. \n \n"
	   			+ "Scores: the amount of stones on the board, minus stones captured by opponent, plus opponent stones captured. "
	   			+ "The player with the highest score wins!  \n\n"
	   			+ "For more info visit: https://www.britgo.org \n \n"
	   			+ "Game created by Jen & Joe"
	   		
		);

	   rules.initModality(Modality.NONE);
	   rules.initStyle(StageStyle.UTILITY);
	   rules.show();
  }
 
	 public static void popUpWinner(String player){
		   Alert rules = new Alert(AlertType.INFORMATION);
		   rules.setTitle("Complete");
		   rules.setHeaderText("Match Finished");
		   rules.setContentText(player + " Wins!");
	
		   rules.initModality(Modality.NONE);
		   rules.initStyle(StageStyle.UTILITY);
		   rules.show();
	}
 
 	
}
