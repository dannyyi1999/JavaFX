
/*
 * Danny Yi March 5th 2017 Period 4
 * This lab took me approximately 90 minutes.
 * Every time when there's something new to learn, it always ends up taking me a huge
 * chunk of time just to understand it. I think that's usually because of my negligence
 * to search through the API of a bunch of classes and finding out what methods I would
 * need. During this lab, one problem that I encountered that I still have no idea how
 * to fix is implementing some effect to change the picture. It's hard to change my picture
 * because I can't change the value of each box because I couldn't store each drawing
 * of a box and a piece to a variable.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VBoxApp extends Application {
	
	private final double PIECE_RADIUS = 20;
	private double spotSize = PIECE_RADIUS;
	
	// colors
	private final Color BOARD_COLOR_ONE = Color.GREY;
	private final Color BOARD_COLOR_TWO = Color.ANTIQUEWHITE;
	
	private final Color PIECE_COLOR_ONE = Color.RED;
	private final Color PIECE_COLOR_TWO = Color.BLACK;
 

	Button button;
	Slider slide;
	Rectangle[][] board;
	Label label2;
	


    private class ButtonHandle implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent m) {
			if(m.getSource() == button){
				if(label2.getText().equals("Sign into your Slack account.")){
					label2.setText("Sign into your facebook account.");
				}else {
					label2.setText("Sign into your Slack account.");
				}
			}
		}
    	
    }
    
    private class SliderListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> observable,
				Number oldValue, Number newValue) {
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					if((y + x) % 2 == 0){
						board[y][x].setFill(new Color
								((Double)newValue, (Double)newValue, (Double)newValue, 1));
					}
				}
			}
		}
    	
    }
    


    @Override
    public void start(Stage primaryStage) throws Exception {

        CheckBox cb1 = new CheckBox("Yes");
        CheckBox cb2 = new CheckBox("No");
        CheckBox cb3 = new CheckBox("A little");
        Group root = new Group();
        board = new Rectangle[8][8];

		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				Circle piece = new Circle(x * PIECE_RADIUS * 2 + PIECE_RADIUS,
						y * PIECE_RADIUS * 2 + PIECE_RADIUS ,PIECE_RADIUS);
				Rectangle square = new Rectangle(x * PIECE_RADIUS * 2,
						y * PIECE_RADIUS * 2,PIECE_RADIUS * 2, PIECE_RADIUS * 2);
				if((x + y) % 2 == 0){
					square.setFill(BOARD_COLOR_ONE);
					if(y < 3){
						piece.setFill(PIECE_COLOR_ONE);
						root.getChildren().add(square);
						root.getChildren().add(piece);
					}else if(y > 4){
						piece.setFill(PIECE_COLOR_TWO);
						root.getChildren().add(square);
						root.getChildren().add(piece);
					}else {
						root.getChildren().add(square);
					}
					
				}else {
					square.setFill(BOARD_COLOR_TWO);
					root.getChildren().add(square);
				}
				board[y][x] = square;
			}
			
		}
 
        VBox vboxMeals = new VBox(5);
        vboxMeals.getChildren().addAll(cb1, cb2, cb3);

        Label label = new Label("Do you enjoy coding?");

        VBox vboxOuter = new VBox(10);
        vboxOuter.getChildren().addAll(label, vboxMeals);
        vboxOuter.setAlignment(Pos.CENTER_LEFT);
        
        TilePane tilePane = new TilePane(); 	
        tilePane.setPrefColumns(2); //preferred columns
        tilePane.setAlignment(Pos.CENTER);
        
        button = new Button("Click here to change text");
        
        PasswordField passwordField1 = new PasswordField();
        passwordField1.setPromptText("Username");
        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Password");
        label2 = new Label("Sign into your Slack account.");
        
        slide = new Slider(0, 1, 0.5);
        slide.setMajorTickUnit(0.1);
        slide.setShowTickMarks(true);
        slide.setShowTickLabels(true);
        slide.valueProperty().addListener(new SliderListener());
        
        button.setOnAction(new ButtonHandle());
        
        VBox account = new VBox(10);
        account.getChildren().addAll(label2, passwordField1, passwordField2, slide);
        
        tilePane.getChildren().addAll(vboxOuter, button, account, root);
        tilePane.setAlignment(Pos.BASELINE_CENTER);
    	
        Scene scene = new Scene(tilePane);
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
