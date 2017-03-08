/*
 * Danny Yi February 2nd 2017 Period 4
 * This lab took me approximately 2 hours.
 * You know that feeling when you complete your code and you compile it and run it for 
 * the first time and it works? That's what happened to me on this lab and man that felt
 * pretty good! Most of the methods were pretty straight forward to code except for that
 * pesky nextGeneration one. Although testing for the boundaries was pretty annoying, I
 * eventually solved the problem with just a couple of continue methods. What really annoyed
 * me the most was since each square whether or not is dies or lives happens simultaniously,
 * you can't just change one thing after you check it because that would affect neighboring
 * future ones that still needed to be tested. There were two solutions that I thought of at 
 * first. One was just creating a new array and just stuff the new values in there and send it
 * back to the original one, but that seemed like too much effort. So I ended up just doing 
 * the second method where I recorded the position of the array I needed to change, and after
 * the loop I would just switch the value between true and false.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class LifeModel extends GridModel<Boolean> implements GenerationListener{
	int generation;
	ArrayList <GenerationListener> listener;
	Scanner in;
	
	public LifeModel(Boolean[][] grid){
		super(grid);
		generation = 0;
	}
	
	public void addGenerationListener(GenerationListener l){
		if(!listener.contains(l)){
			listener.add(l);
		}
	}
	
	public void removeGenerationListener(GenerationListener l){
		listener.remove(l);
	}
	
	public void setGeneration(int gen){
		generation = gen;
		generationChanged(generation, gen);
	}
	
	public int getGeneration(){
		return generation;
	}
	
	public void runLife(int numGenerations){
		for(int i = 0; i < numGenerations; i++){
			setGeneration(generation + 1);
			nextGeneration();
		}
	}
	//returns the amount of true values in the row.
	public int rowCount(int row){
		int count = 0;
		if(row >= getNumRows() || row < 0){
			return -1;
		}else {
			for(int i = 0; i < getNumCols(); i++){
				if(getValueAt(row, i)){
					count++;
				}
			}
		}
		return count;
	}
	
	//returns amount of trues in the desired row.
	public int colCount(int col){
		int count = 0;
		if(col >= getNumCols() || col < 0){
			return -1;
		}else {
			for(int i = 0; i < getNumRows(); i++){
				if(getValueAt(i, col)){
					count++;
				}
			}
		}
		return count;
	}
	
	//returns the total amount of trues on the board.
	public int totalCount(){
		int count = 0;
		for(int i = 0; i < getNumRows(); i++){
			count += rowCount(i);
		}
		return count;
	}
	
	public void printBoard(){
		System.out.print("  ");
		for(int i = 0; i < getNumCols(); i++){
			System.out.print(i % 10);
		}
		System.out.println();
		for(int i = 0; i < getNumRows(); i++){
			System.out.printf("%2d", i);
			for(int a = 0; a < getNumCols(); a++){
				if(getValueAt(i, a)){
					System.out.print("*");
				}else {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	public void nextGeneration(){
		ArrayList <Integer> list = new ArrayList <Integer>();
		for(int row = 0; row < getNumRows(); row++){
			for(int col = 0; col < getNumCols(); col++){
				int trues = 0;
				for(int i = row - 1; i <= row + 1; i++){
					for(int j = col - 1; j <= col + 1; j++){
						if(i < 0 || i > getNumRows() - 1){
							continue;
						}else if(j < 0 || j > getNumCols() - 1){
							continue;
						}else if(i == row && j == col){
							continue;
						}else {
							if(getValueAt(i, j)){
								trues++;
							}
						}
					}
				}
				if(!getValueAt(row, col) && trues == 3){
					list.add(row * getNumRows() + col);
				}else if(getValueAt(row, col) && trues < 2){
					list.add(row * getNumRows() + col);
				}else if(getValueAt(row, col) && trues > 3){
					list.add(row * getNumRows() + col);
				}
			}
		}
		for(int i = 0; i < list.size(); i++){
			setValueAt(list.get(i) / getNumRows(), list.get(i) % getNumRows(),
					!getValueAt(list.get(i) / getNumRows(), list.get(i) % getNumRows()));
		}
		setGeneration(generation + 1);
	}

	@Override
	public void generationChanged(int oldVal, int newVal) {
		
	}
	
}









