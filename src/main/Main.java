package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import gui.GUI;

public class Main {
	public static BufferedImage img;
	public static double[][] input;
	// [][img]
	public static double[][] solutions = {
		{1},
		{1},
		{1},
		{1},
		{1},
		{0},
		{0},
		{0},
		{0},
		{0}
	};
	public static GUI gui;
	
	public static void main(String[] args) {
		gui = new GUI();
		input = new double[10][2500];
		try {
			for (int i = 0; i < 5; i++) {
				img = ImageIO.read(Main.class.getClassLoader().getResource("x" + i + ".png"));
				int count = 0;
				for (int j = 0; j < img.getHeight(); j++) {
					for (int k = 0; k < img.getWidth(); k++) {
						//0 is white and 1 is black
						if(img.getRGB(j, k) == -1) {
							input[i][count] = 0;
						} else {
							input[i][count] = 1;
						}
						count++;
					}
				}
			}			
			
			for (int i = 5; i < 10; i++) {
				img = ImageIO.read(Main.class.getClassLoader().getResource("o" + (i-5) + ".png"));
				int count = 0;
				for (int j = 0; j < img.getHeight(); j++) {
					for (int k = 0; k < img.getWidth(); k++) {
						//0 is white and 1 is black
						if(img.getRGB(j, k) == -1) {
							input[i][count] = 0;
						} else {
							input[i][count] = 1;
						}
						count++;
					}
				}
			}
			
			System.out.println("created Input data");
			
			NeuralNetwork nn = new NeuralNetwork(2500, 200, 1);
			
			System.out.println("created neural network");
			
			List<Double>output;
			
			System.out.println("start training");
			
			nn.fit(input, solutions, 2000);
			
			System.out.println("trained");
			
			double[][] wrongInput = new double[1][2500];
			img = ImageIO.read(Main.class.getClassLoader().getResource("x3.png"));
			int count = 0;
			for (int j = 0; j < img.getHeight(); j++) {
				for (int k = 0; k < img.getWidth(); k++) {
					//0 is white and 1 is black
					if(img.getRGB(j, k) == -1) {
						input[0][count] = 0;
					} else {
						input[0][count] = 1;
					}
					count++;
				}
			}
			
			output = nn.predict(wrongInput[0]);
			System.out.println(output.toString());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void paint(Graphics2D g) {
		g.drawImage(img, null, 50, 50);
	}
}





