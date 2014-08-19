package com.craftsome;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ImageTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034116176900077620L;

	private static final int FONT_SIZE = 24;
	private static final long DEMO_TIME = 10000;

	private Image bgImage;
	private Image transparentImage;
	private boolean imagesLoaded;

	public static void main(String[] args) {
		DisplayMode displayMode;

		if (args.length == 3) {
			displayMode = new DisplayMode(Integer.parseInt(args[0]),
					Integer.parseInt(args[1]), Integer.parseInt(args[2]),
					DisplayMode.REFRESH_RATE_UNKNOWN);
		} else {
			displayMode = new DisplayMode(800, 600, 16,
					DisplayMode.REFRESH_RATE_UNKNOWN);
		}

		ImageTest test = new ImageTest();
		test.run(displayMode);
	}

	private void run(DisplayMode displayMode) {
		setBackground(Color.blue);
		setForeground(Color.white);
		setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));

		imagesLoaded = false;
		loadImages();
		
		this.setVisible(true);
		this.setBounds(50, 50, 800, 600);
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

	public void paint(Graphics g) {
		//super.paint(g);
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}

		// draw images
		if (imagesLoaded) {
			g.drawImage(bgImage, 0, 0, null);
			drawImage(g, transparentImage, 0, 0, "Transparent");
		}
	}

	private void drawImage(Graphics g, Image image, int x, int y, String caption) {
		g.drawImage(image, x, y, null);
		g.drawString(caption, x + 5, y + FONT_SIZE + image.getHeight(null));
	}

}
