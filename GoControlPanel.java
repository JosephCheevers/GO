package gogame;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

HELLO JOE

public class GoControlPanel extends Pane{

	private GoGameLogic goGameLogic;
	private TextField tf_score ; 
	private Label tf_Player; 

	private VBox vb; 
	
	public GoControlPanel(GoGameLogic goGameLogic) {
		super();
		this.goGameLogic = 	goGameLogic;
		this.tf_score = new TextField();
		this.tf_Player = new Label("Player 1");
		Button reset_button = new Button("Reset Game");
		Button change_board_button = new Button("Change Board");
		
		change_board_button.setOnAction(event -> {
	            //root.setEffect(null);
	            GoBoard.changeBackground();
		     });

		// Binding the SimpleIntegerProperty scoreProperty in GoGameLogic to the TextField tf_score

		this.tf_score.textProperty().bindBidirectional(this.goGameLogic.getScore(), new NumberStringConverter());
		this.vb = new VBox();
		this.getChildren().add(vb);
		vb.getChildren().addAll (new Label("Control Panel"),tf_Player, tf_score, reset_button, change_board_button);
	}	
}
