package com.craftsome;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SimpleScreenManager class manages initializing and displaying full screen
 * graphics modes.
 * 
 * 
 * @author qinghai
 * 
 */
public class SimpleScreenManager {
	
	private final static Logger logger = LoggerFactory.getLogger(SimpleScreenManager.class); 

	private GraphicsDevice device;

	public SimpleScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}

	
	/**
	 * Enters full screen mode and changes the display mode.
	 * 
	 * @param displayMode
	 * @param window
	 */
	public void setFullScreen(DisplayMode displayMode, JFrame window) {
		window.setUndecorated(true);
		window.setResizable(false);
		device.setFullScreenWindow(window);
		if (displayMode != null && device.isDisplayChangeSupported()) {
			try {
				device.setDisplayMode(displayMode);
			} catch (IllegalArgumentException e) {
				logger.warn("Illegal mode for this device");
			}
		}
	}
	
	
	/**
	 * Returns the window currently used in full screen mode.
	 * 
	 * @return
	 */
	public Window getFullScreenWindow() {
		return device.getFullScreenWindow();
	}
	
	/**
	 * Restores the screen's display mode.
	 * 
	 */
	public void restoreScreen() {
		Window window = device.getFullScreenWindow();
		if (window != null) {
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}
}
