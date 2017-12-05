package gogame;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

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

		// Binding the SimpleIntegerProperty scoreProperty in GoGameLogic to the TextField tf_score

		this.tf_score.textProperty().bindBidirectional(this.goGameLogic.getScore(), new NumberStringConverter());
		this.vb = new VBox();
		this.getChildren().add(vb);
		vb.getChildren().addAll (new Label("Control Panel"),tf_Player, tf_score);
	}	
}
