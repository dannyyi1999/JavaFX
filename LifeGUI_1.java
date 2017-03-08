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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class LifeGUI_1 extends Application{

	private LifeModel model;
	private BooleanGridPane gridPane;
	private Button clear;
	private Button load;
	private Button nextGen;
	Scanner in;
	Label gen;
	Slider slide;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Grid");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 500, 500);
		gridPane = new BooleanGridPane();
		
		Boolean[][] data = {{true, true , true , true , true},
							{true, false, false, false, true},
							{true, false, false, false, true},
							{true, false, false, false, true},
							{true, false, false, false, true},
							{true, true , true , true , true}}; 
		
		model = new LifeModel(data);
		gridPane.setModel(model);
		
		GridMouseHandler mouse = new GridMouseHandler();
		gridPane.setOnMousePressed(mouse);
		gridPane.setOnMouseDragged(mouse);
		
		slide = new Slider(0, 50, 10);
		slide.setShowTickMarks(true);
		slide.setShowTickLabels(true);
		slide.setMajorTickUnit(5);
		slide.valueProperty().addListener(new SlideHandler());
		
		HBox hbox = new HBox();
		clear = new Button("Clear");
		clear.setOnMouseClicked(new ButtonHandler());
		load = new Button("Load");
		load.setOnMouseClicked(new ButtonHandler());
		nextGen = new Button ("Next Generation");
		nextGen.setOnMouseClicked(new ButtonHandler());
		
		gen = new Label("Generation " + gridPane.getModel().getGeneration());
		
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		file.getItems().addAll(open, save);
		menuBar.getMenus().add(file);
		
		hbox.getChildren().addAll(clear, load, nextGen, gen, slide);
		
		root.setTop(menuBar);
		root.setBottom(hbox);
		root.setCenter(gridPane);
		stage.setScene(scene);
		stage.show();
	}
	
	private class GridMouseHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent e) {
			int row = gridPane.rowForYPos(e.getY());
			int col = gridPane.colForXPos(e.getX());
			if(row >= 0 && row < gridPane.getModel().getNumRows() && col >= 0 && col < gridPane.getModel().getNumCols()){
				if(e.getButton().equals(MouseButton.PRIMARY)){
					gridPane.getModel().setValueAt(row, col, true);
				}else {
					gridPane.getModel().setValueAt(row, col, false);
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
				for(int i = 0; i < gridPane.getModel().getNumRows(); i++){
					for(int j = 0; j < gridPane.getModel().getNumCols(); j++){
						if(gridPane.getModel().getValueAt(i, j)){
							gridPane.getModel().setValueAt(i, j, false);
						}
					}
				}
			}else if(e.getSource() == load){
				FileDialog fd = new FileDialog(new Frame(), "Select a Color database", FileDialog.LOAD);
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
			}else if(e.getSource() == nextGen){
				gridPane.getModel().nextGeneration();
				gen.setText("Generation " + gridPane.getModel().getGeneration());
				
			}
		}
		
	}
	
}
