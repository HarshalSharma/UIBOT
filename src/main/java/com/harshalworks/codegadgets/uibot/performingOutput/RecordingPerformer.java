package com.harshalworks.codegadgets.uibot.performingOutput;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.harshalworks.codegadgets.uibot.recordingInput.NativeToAwt;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;

public class RecordingPerformer {

	public static void main(String args[]) {
		new RecordingPerformer();
	}

	public RecordingPerformer() {
		loadMouseRecording();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(1);
		}
		robot.setAutoWaitForIdle(true);
		play();
	}

	ArrayList<NativeInputEvent> inputEvents;
	Robot robot;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public void loadMouseRecording() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mouseRecording"));
			inputEvents = (ArrayList<NativeInputEvent>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		for (NativeInputEvent nme : inputEvents) {
			try {
				performNativeInputEvents(nme);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			takeBreak();
		}
	}

	public void performNativeInputEvents(NativeInputEvent e) throws AWTException {
		String nativeInputClassName = e.getClass().getSimpleName();
		switch (nativeInputClassName) {
		case "NativeMouseEvent":
			// if (!validatePoint(((NativeMouseEvent) e).getPoint())) {
			// System.out.println("INVALID POINT FOUND.");
			// System.exit(1);
			// }
			robot.waitForIdle();
			// ActionMaker.gradualMouseMove(e.getPoint(), robot);
			robot.mouseMove(((NativeMouseEvent) e).getX(), ((NativeMouseEvent) e).getY());
			robot.waitForIdle();
			switch (e.getID()) {
			case NativeMouseEvent.NATIVE_MOUSE_PRESSED:
				if (((NativeMouseEvent) e).getButton() == NativeMouseEvent.BUTTON1)
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				else if (((NativeMouseEvent) e).getButton() == NativeMouseEvent.BUTTON2)
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				break;
			case NativeMouseEvent.NATIVE_MOUSE_RELEASED:
				if (((NativeMouseEvent) e).getButton() == NativeMouseEvent.BUTTON1)
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				else if (((NativeMouseEvent) e).getButton() == NativeMouseEvent.BUTTON2)
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				break;
			default:
				break;
			}
			break;
		case "NativeKeyEvent":
			NativeKeyEvent keyEvent = (NativeKeyEvent) e;
			if (!validateKey(keyEvent))
				return;
			switch (e.getID()) {
			case NativeKeyEvent.NATIVE_KEY_PRESSED:
				if (keyEvent.getModifiersText(keyEvent.getModifiers()).contains("Shift")) {
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(NativeToAwt.getAwtKeyCode(keyEvent));
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} else {
					robot.keyPress(NativeToAwt.getAwtKeyCode(keyEvent));
				}
				break;
			case NativeKeyEvent.NATIVE_KEY_RELEASED:
				robot.keyRelease(NativeToAwt.getAwtKeyCode(keyEvent));
				break;
			default:
				System.out.println("default key event : " + e.getID());
				break;
			}
		}
	}

	public void takeBreak() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void takeBreak(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean validatePoint(Point point) {
		try {
			if (point.getX() > screenSize.getWidth()) {
				System.out.println("OUTSIDE X BOUNDS");
				return false;
			}
			if (point.getY() > screenSize.getHeight()) {
				System.out.println("OUTSIDE Y BOUNDS");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean validateKey(NativeKeyEvent keyEvent) {

		try {
			if (NativeToAwt.getAwtKeyCode(keyEvent) == KeyEvent.VK_UNDEFINED && keyEvent.getModifiers() == 0) {
				return false;
			} else if (NativeToAwt.getAwtKeyCode(keyEvent) == KeyEvent.VK_UNDEFINED && keyEvent.getModifiers() > 0) {

				return false;
			}
		}catch (Exception exp){
			exp.printStackTrace();
		}

		return true;
	}

}
