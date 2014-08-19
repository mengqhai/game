package com.craftsome;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullScreenTest extends JFrame {

	private static final Logger logger = LoggerFactory
			.getLogger(FullScreenTest.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2296872418101686743L;

	private static final long DEMO_TIME = 5000;

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

		FullScreenTest test = new FullScreenTest();
		//test.setVisible(true);
		test.run(displayMode);
	}

	public void run(DisplayMode displayMode) {
		setBackground(Color.BLUE);
		setForeground(Color.white);
		setFont(new Font("Dialog", Font.PLAIN, 24));

		SimpleScreenManager screen = new SimpleScreenManager();

		try {
			screen.setFullScreen(displayMode, this);
			Thread.sleep(DEMO_TIME);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		} finally {
			screen.restoreScreen();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("Full Screen", 20, 50);
	}
	
	

}
