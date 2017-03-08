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


import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GridDriver extends Application{

	private LifeModel model;
	private BooleanGridPane gridPane;
	private Button clear;
	private Button load;
	private Scanner in;
	
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
		
		model = new LifeModel(data);
		gridPane.setModel(model);
		gridPane.setOnMouseClicked(new GridMouseHandler());
		
		Slider slide = new Slider(0, 50, 10);
		slide.setShowTickMarks(true);
		slide.setShowTickLabels(true);
		slide.setMajorTickUnit(5);
		slide.valueProperty().addListener(new SlideHandler());
		
		HBox hbox = new HBox();
		clear = new Button("Clear");
		clear.setOnMouseClicked(new ButtonHandler());
		load = new Button("Load");
		load.setOnMouseClicked(new ButtonHandler());
		hbox.getChildren().addAll(clear, load);
		
		root.setTop(hbox);
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
	
	private class ButtonHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent e) {
			if(e.getSource() == clear){
				for(int i = 0; i < model.getNumRows(); i++){
					for(int j = 0; j < model.getNumCols(); j++){
						if(model.getValueAt(i, j)){
							model.setValueAt(i, j, false);
						}
					}
				}
			}else if(e.getSource() == load){
				FileDialog fd = new FileDialog(new Frame(), "Select a grid", FileDialog.LOAD);
				fd.setVisible(true);
				
				try {
					ArrayList<String> list = new ArrayList<String>();
					in = new Scanner(new File(fd.getDirectory() + fd.getFile()));
					while(in.hasNext()){
						list.add(in.nextLine());
					}
					Boolean[][] newData = new Boolean[list.size()][list.get(0).length() / 2 + 1];
					for(int row = 0; row < newData.length; row++){
						for(int col = 0; col < list.get(row).length(); col++){
							if(list.get(row).charAt(col) == 'x'){
								newData[row][col / 2] = false;
							}else if(list.get(row).charAt(col) == 'o'){
								newData[row][col / 2] = true;
							}
						}
					}
					gridPane.setModel(new LifeModel(newData));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		
	}
	
}
