package com.harshalworks.codegadgets.uibot.mousetest;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import Enums.ClickType;

public class ActionMaker {

	public static void main(String args[]) throws InterruptedException {
		// performMouseClick(90, 750, ClickType.LEFT);
		// performTextInput("NOTEPAD");
		// Thread.sleep(100);
		// performTextInput("\n");
		// Thread.sleep(100);
		// performMouseClick(900, 600, ClickType.LEFT);
		// performTextInput("I AM SKYNET");

//		actionExecutor(ActionRecorder.recordOperations());

	}

	public static void actionExecutor(LinkedHashMap<Point, Integer> points) throws InterruptedException {
		for (Entry<Point, Integer> e : points.entrySet()) {
			if (e.getValue() == 1)
				performMouseClick((int) e.getKey().getX(), (int) e.getKey().getY(), ClickType.LEFT);
			else if (e.getValue() == 3)
				performMouseClick((int) e.getKey().getX(), (int) e.getKey().getY(), ClickType.RIGHT);

			Thread.sleep(100);
		}
	}

	public static void performTextInput(String text) {
		try {
			Robot robot = new Robot();
			robot.waitForIdle();
			for (char c : text.toCharArray()) {
				robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
				robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void performMouseClick(int x, int y, ClickType clickType) {
		Point point = new Point(x, y);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			if (point.getX() > screenSize.getWidth()) {
				System.out.println("OUTSIDE X BOUNDS");
				return;
			}
			if (point.getY() > screenSize.getHeight()) {
				System.out.println("OUTSIDE Y BOUNDS");
				return;
			}
			click(point, clickType);
			Thread.sleep(1000);

		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void click(Point p, ClickType clickType) throws AWTException, InterruptedException {
		Robot r = new Robot();
		gradualMouseMove(p, r);
		switch (clickType) {
		case LEFT:
			r.mousePress(InputEvent.BUTTON1_MASK);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case RIGHT:
			r.mousePress(InputEvent.BUTTON3_MASK);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			r.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		default:
			break;
		}
	}

	public static void gradualMouseMove(Point pointIn, Robot robot) {
//		 TO ADJUST SPEED OF MOVEMENT
//		 CHANGE: 1:sleep duration 2:deltaX # 3:deltaY #
		Point mouseLocation; // Point for updating the currentLocation
		int deltaX; // X coordinate update value
		int deltaY; // Y coordinate update value
		boolean xInProgess = true; // Stop condition for X
		boolean yInProgess = true; // Stop condition for Y

		int pastX, pastY;

		do {
//			 update Mouse Location
			mouseLocation = MouseInfo.getPointerInfo().getLocation();
//			 calculate X coordinates
			if (pointIn.getX() - mouseLocation.getX() > 2) {
				deltaX = 1; // Mouse is LEFT of Destination
			} else if (pointIn.getX() - mouseLocation.getX() < -2) {
				deltaX = -1; // Mouse is RIGHT of Destination
			} else {
				xInProgess = false; // X Done
				deltaX = 0;
			}
			// calculate Y coordinates
			if (pointIn.getY() - mouseLocation.getY() > 2) {
				deltaY = 1; // Mouse is ABOVE Destination
			} else if (pointIn.getY() - mouseLocation.getY() < -2) {
				deltaY = -1; // Mouse is BELOW Destination
			} else {
				yInProgess = false; // Y Done
				deltaY = 0;
			}

//			 move mouse on Screen by adjustment amount
			pastX = (int) (mouseLocation.getX() + deltaX);
			pastY = (int) (mouseLocation.getY() + deltaY);
			robot.mouseMove(pastX, pastY);

//			 Thread.sleep(1); // pause to slow down movement
		} while (xInProgess || yInProgess); // loop until complete on X & Y
	}

}
