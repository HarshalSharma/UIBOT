package com.harshalworks.codegadgets.uibot.recordingInput;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;

public class GlobalRecorder {

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		GlobalListener myListner = new GlobalListener();
		GlobalScreen.addNativeMouseListener(myListner);
		GlobalScreen.addNativeMouseMotionListener(myListner);
		GlobalScreen.addNativeKeyListener(myListner);

		// Disabling log.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);

		myListner.startRecording();
		Scanner scanner = new Scanner(System.in);
		while (myListner.isRecording) {
			//waiting to stop...
			//press F3 to stop.
		}
//		myListner.stopRecording();
		ArrayList<NativeInputEvent> inputEvents = myListner.getRecording();
		try {
			FileOutputStream fout = new FileOutputStream("mouseRecording");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(inputEvents);
		} catch (Exception e) {
			e.printStackTrace();
		}


		System.out.println(inputEvents.size() + " input events are recorded.");
		System.exit(1);
	}

}
