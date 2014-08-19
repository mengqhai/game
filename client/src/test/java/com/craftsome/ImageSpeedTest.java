package com.craftsome;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ImageSpeedTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6862483266851219724L;

	private static final int FONT_SIZE = 24;
	private static final long TIME_PER_IMAGE = 1500;
	private boolean imagesLoaded;
	private Image bgImage;
	private Image transparentImage;

	private void run() {
		setBackground(Color.blue);
		setForeground(Color.white);
		setFont(new Font("Dailog", Font.PLAIN, FONT_SIZE));
		imagesLoaded = false;
		synchronized(this) {
			
		}
	}
	
	public void loadImages() {
		bgImage = loadImage("images/random-wallpapers-blue.jpg");
		transparentImage = loadImage("images/Mario-icon.png");
		imagesLoaded = true;
		repaint();
	}

	private Image loadImage(String fileName) {
		return new ImageIcon(fileName).getImage();
	}

}
