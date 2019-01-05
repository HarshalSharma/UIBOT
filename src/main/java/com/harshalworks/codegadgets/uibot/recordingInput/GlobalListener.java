package com.harshalworks.codegadgets.uibot.recordingInput;

import java.util.ArrayList;

import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalListener implements NativeMouseInputListener, NativeKeyListener{
	
	public void nativeMouseClicked(NativeMouseEvent e) {
		// System.out.println("Mouse Clicked: " + e.getClickCount());
		if (isRecording)
			nativeEvents.add(e);
	}

	public void nativeMousePressed(NativeMouseEvent e) {
		// System.out.println("Mouse Pressed: " + e.getButton());
		if (isRecording)
			nativeEvents.add(e);
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		// System.out.println("Mouse Released: " + e.getButton());
		if (isRecording)
			nativeEvents.add(e);
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		// System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
		if (isRecording)
			nativeEvents.add(e);
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		// System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
		if (isRecording)
			nativeEvents.add(e);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		System.out.println("KEY TYPED");
		if (isRecording)
			nativeEvents.add(nativeEvent);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		if(nativeEvent.getKeyCode() == NativeKeyEvent.VC_F3){
			stopRecording();
			return;
		}
		if (isRecording)
			nativeEvents.add(nativeEvent);
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		if (isRecording)
			nativeEvents.add(nativeEvent);		
	}
	
	public volatile boolean isRecording = false;
	ArrayList<NativeInputEvent> nativeEvents;

	public void startRecording() {
		isRecording = true;
		nativeEvents = new ArrayList<>();
	}

	public void stopRecording() {
		isRecording = false;
	}

	public ArrayList<NativeInputEvent> getRecording() {
		return nativeEvents;
	}

}