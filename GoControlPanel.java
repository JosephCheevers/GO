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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
//import gogame.GoPiece;

public class GoControlPanel extends Pane{

	private GoGameLogic goGameLogic;
	private TextField tf_score, tf_prisoners; 
	private Label lbl_player1, lbl_player2, lbl_prisoners, lbl_score;

	private VBox vb; 
	private HBox prisonBox, scoreBox, playerBox;
	static Ellipse gp  = new Ellipse();
	
	public GoControlPanel(GoGameLogic goGameLogic) {
		super();
		this.goGameLogic = goGameLogic;
	
		lbl_player1 = new Label("Turn:     Player ");
		lbl_player2 = new Label();
		
		Button reset_button = new Button("Reset Game");
		Button change_board_button = new Button("Change Board");
		Button skip_button = new Button("Skip Turn");
		Button menu_button = new Button("Menu");
		
		lbl_score = new Label("Territory:");
		tf_score = new TextField();
		tf_score.setEditable(false);
		tf_score.setPrefWidth(50);
		
		lbl_prisoners = new Label("Prisoners:");
		tf_prisoners = new TextField();
		tf_prisoners.setText("0");
		tf_prisoners.setEditable(false);
		tf_prisoners.setPrefWidth(50);
		
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
		stoneColour(2);

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
			//goGameLogic.swapPlayers();
	     });
		
		menu_button.setOnAction(event -> {
          //root.setEffect(null);
          popUpDialog();
	     });

		// Binding the SimpleIntegerProperty scoreProperty in GoGameLogic to the TextField tf_score
		//this.lbl_player2.textProperty().bindBidirectional(this.goGameLogic.get_playerProperty(), new NumberStringConverter());
		this.tf_score.textProperty().bindBidirectional(this.goGameLogic.getScore(), new NumberStringConverter());
		//this.tf_prisoners.textProperty().bindBidirectional(this.goGameLogic.getPrisonersProperty(), new NumberStringConverter());
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
		
		prisonBox = new HBox();
		prisonBox.setAlignment(Pos.CENTER);
		prisonBox.setSpacing(20);
		prisonBox.getChildren().addAll(lbl_prisoners, tf_prisoners);
		
		scoreBox = new HBox();
		scoreBox.setAlignment(Pos.CENTER);
		scoreBox.setSpacing(20);
		scoreBox.getChildren().addAll(lbl_score, tf_score);
		
		//int curr_player = GoGameLogic.get_player();
		//changePlayerIcon(curr_player, gp);

		
		this.getChildren().add(vb);
		vb.getChildren().addAll (new Label("Control Panel"),playerBox, gp, scoreBox, prisonBox, reset_button, 
				skip_button,change_board_button, menu_button);
		
		//System.out.println("TEST PIECE" + gp);

	}	
	
	public static void stoneColour(int i) {
		if(i == 1)
			gp.setFill(Color.WHITE);
		else
			gp.setFill(Color.BLACK);	
	}
	
	
	public void popUpPause() {
		 //root.setEffect(new GaussianBlur());
	
		 Pane pauseRoot = new Pane ();
	     pauseRoot.getChildren().add(new Label("Paused"));
	     pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
	     //pauseRoot.setAlignment(Pos.CENTER);
	     pauseRoot.setPadding(new Insets(20));
	
	     //======================== Menu ==============================
	     pauseRoot.setPrefSize(300, 300);
	     
	     //ContentFrame frame1 = new ContentFrame(createContent(-25, "Player 1 Info"));
	    // ContentFrame frame2 = new ContentFrame(createContent(-25, "Whois Turn"));
	    // ContentFrame frame3 = new ContentFrame(createContent(-25, "Player 2 Info"));
	     //HBox hbox = new HBox(15, frame1, frame2, frame3);
	     
	     //hbox.setTranslateX(120);
	     //hbox.setTranslateY(50);
	
	
	        
	     //======================================================   
	     
	     Button resume = new Button("Resume");
	     //pauseRoot.getChildren().add(resume);
	
	     Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	     //initOwner(primaryStage);
	     popupStage.initModality(Modality.APPLICATION_MODAL);
	     popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
	     
	     pauseRoot.getChildren().addAll(resume);
	     resume.setOnAction(event -> {
	         //root.setEffect(null);
	         popupStage.hide();
	     });
	     popupStage.show();
  }
  
 private void popUpDialog(){
	   Alert rules = new Alert(AlertType.INFORMATION);
	   rules.setTitle("GO MENU");
	   rules.setHeaderText("The Rules of GO");
	   rules.setContentText("A game of Go starts with an empty board. This is a smaller 7x7 version. \n\n" + 
			   "Each player has an unlimited supply of pieces (called stones), one taking the black stones, " + 
			   "the other taking white. The main object of the game is to use your stones to " + 
	   			"form territories by surrounding vacant areas of the board. It is also possible to capture your opponent's" + 
	   			"stones by completely surrounding them. \n \n" +
	   			"- The empty points which are horizontally and vertically adjacent to a stone, or a solidly connected string"
	   			+ " of stones, are known as liberties. An isolated stone or solidly connected string of stones is captured "
	   			+ "when all of its liberties are occupied by enemy stones.\n \n"
	   			+ "- Stones occupying adjacent points constitute a solidly connected string. Diagonals do not count as connections. "
	   			+ "Several strings close together that belong to the same player are called a group. A string of stones is treated"
	   			+ " as a single unit and is captured when all of its liberties are occupied by enemy stones.\n\n"
	   			+ "- Suicide Rule: A player may not suicide (play a stone into a position where it would have no liberties) unless"
	   			+ " as a result, one or more of the stones surrounding it is captured."
	   			+ " - KO Rule: A play is illegal if it would have the effect (after all steps of the play have been completed)"
	   			+ " of creating a position that has occurred previously in the game. \n \n"
	   			+ "For more info visit: https://www.britgo.org \n \n"
	   			+ "Game created by Jen & Joe"
	   		
		);

	   rules.initModality(Modality.NONE);
	   rules.initStyle(StageStyle.UTILITY);
	   rules.show();
  }
 
 	private void changePlayerIcon(int p, Ellipse gp) {
		if (p == 1) {
			gp.setFill(Color.WHITE); //visible - Player1
		}
		else if(p == 2) {
			gp.setFill(Color.BLACK); //visible - Player2
		}
		else if(p == 0) {
			gp.setFill(Color.TRANSPARENT); //invisible
		}	
		else if(p == 3) {
			gp.setFill(Color.web("#555555", 0.2)); //set color of empty piece that is viable move to grey
		}
 }
}
