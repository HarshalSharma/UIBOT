package com.harshalworks.codegadgets.uibot.mousetest;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javax.swing.*;

public class ActionRecorder {

	static LinkedHashMap<Point, Integer> points;

	public static void main(String args[]) {

		JFrame f = new JFrame("GlassPane");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setOpacity((float) 0.2);

		points = new LinkedHashMap<>();

		long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent e) {
				if (e.getID() == MouseEvent.MOUSE_PRESSED) {
					MouseEvent me = (MouseEvent) e;
					points.put(me.getPoint(), me.getButton());
				}
			}
		}, eventMask);

		System.out.println("INput message:");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		System.out.println(points);
	}


	public static LinkedHashMap<Point, Integer>  recordOperations(){
		LinkedHashMap<Point, Integer> points;
		JFrame f = new JFrame("GlassPane");
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setOpacity((float) 0.2);

		points = new LinkedHashMap<>();

		long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent e) {
				if (e.getID() == MouseEvent.MOUSE_PRESSED) {
					MouseEvent me = (MouseEvent) e;
					ActionRecorder.points.put(me.getPoint(), me.getButton());
				}
			}
		}, eventMask);

		System.out.println("INput message:");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		return points;
	}

}
