package gogame;

//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

//class defnition f
public class Go extends Application {
	
	private BorderPane bp_layout;
	private GoCustomControl customControl;
	private GoControlPanel controlPanel;
	private GoGameLogic gameLogic; 
	private GoBoard board; 
	
	// overridden init method
	public void init() {
		bp_layout = new BorderPane();				    // create layout
		board = new GoBoard();					        // creates a board
		gameLogic = new GoGameLogic(board); 	        // create gameLogic and pass board to gameLogic so gameLogic can call methods from board
		customControl = new GoCustomControl(gameLogic); // create customControl and pass gameLogic to customControl so customControl can call methods from gameLogic
		controlPanel = new GoControlPanel(gameLogic);   // create controlPanel and pass gameLogic to controlPanel so customControl can call methods from gameLogic
		
		bp_layout.setCenter(customControl);				// put the customControl in the center of the layout
		bp_layout.setLeft(controlPanel);				// put the controlPanel in the right of the layout
	}

	// overridden start method
	public void start(Stage primaryStage) {
		// set a title, size and the stack pane as the root of the scene graph
		primaryStage.setTitle("Go");
		primaryStage.setScene(new Scene(bp_layout, 1000, 800));
		primaryStage.show();
	}

	// overridden stop method
	public void stop() {
		System.out.println("Application closed");
	}

	// entry point into our program for launching our javafx applicaton
	public static void main(String[] args) {
		//T1
		launch(args);
	}

	/* private fields 
	
	
	public void popUpPause() {// ,BorderPane root) {
	   	 //root.setEffect(new GaussianBlur());

	        HBox pauseRoot = new HBox(50);
	        pauseRoot.getChildren().add(new Label("Paused"));
	        pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
	        pauseRoot.setAlignment(Pos.CENTER);
	        pauseRoot.setPadding(new Insets(20));

	        Button resume = new Button("Resume");
	        pauseRoot.getChildren().add(resume);

	        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
	        //popupStage.initOwner(primaryStage);
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

	        resume.setOnAction(event -> {
	            //root.setEffect(null);
	            popupStage.hide();
	        });

	        popupStage.show();
	   }*/

}
