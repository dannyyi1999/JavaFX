/*
 * Danny Yi March 5th 2017 Period 4
 * This lab took me approximately 60 minutes.
 * After I completed this lab, I felt really accomplished and felt like I could 
 * potentially do a lot more. It is definitely getting a lot interesting once I
 * know what I'm doing. I learned a solution to how to fix the problem of HW#3
 * GUI of fixing the complete checkerboard. I should've realized that using 2D
 * arrays would be good for making the board and easy to adjust the slide with
 * a slider. In this lab that's how I did it, by adjusting each panel size.
 */


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class GridDriver extends Application{

	private GridModel <Boolean> model;
	private BooleanGridPane gridPane;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Grid");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 500, 500);
		gridPane = new BooleanGridPane();
		
		Boolean[][] data = {{false, false, false, false},
							{false, false, false, false},
							{false, false, false, false},
							{false, false, false, false},
							{false, false, false, false},
							{false, false, false, false}}; 
		
		model = new GridModel<Boolean>(data);
		gridPane.setModel(model);
		gridPane.setOnMouseClicked(new GridMouseHandler());
		
		Slider slide = new Slider(0, 50, 10);
		slide.setShowTickMarks(true);
		slide.setShowTickLabels(true);
		slide.setMajorTickUnit(5);
		slide.valueProperty().addListener(new SlideHandler());
		
		root.setCenter(gridPane);
		root.setBottom(slide);
		stage.setScene(scene);
		stage.show();
	}
	
	private class GridMouseHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e) {
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());
			for(int i = row - 1; i <= row + 1; i++){
				for(int j = col - 1; j <= col + 1; j++){
					if(i < 0 || i > model.getNumRows() - 1 
					|| j < 0 || j > model.getNumCols() - 1
					|| (i == row && j == col)){
						continue;
					}else {
						model.setValueAt(i, j, !model.getValueAt(i, j));
					}
				}
			}
		}
	}
	
	private class SlideHandler implements ChangeListener<Number>{

		@Override
		public void changed(ObservableValue<? extends Number> observable,
				Number oldValue, Number newValue) {
			gridPane.setTileSize((Double)newValue);
		}
		
	}

}
