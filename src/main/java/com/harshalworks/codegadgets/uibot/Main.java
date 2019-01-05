package com.harshalworks.codegadgets.uibot;

import com.harshalworks.codegadgets.uibot.performingOutput.RecordingPerformer;
import com.harshalworks.codegadgets.uibot.recordingInput.GlobalRecorder;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends JFrame{

    public static void main(String[] args) {
        PrintWriter pw = new PrintWriter(System.out);
        Scanner scanner = new Scanner(System.in);
        pw.printf("\n\nNote: Press F3 to stop the program.\nMENU:\n1.Record Input\n2.Play Recording\n\nENTER CHOICE:");
        int choice = scanner.nextInt();
        String fileName = "";
        RecordingPerformer performer;
        switch (choice){
            case 1:
                pw.println("Please enter a filename to save recording:");
                fileName = scanner.next();
                try {
                    GlobalRecorder.record(fileName);
                    pw.println("Recording started. Press F3 to stop.");
                } catch (NativeHookException e) {
                    System.err.println("There was a problem registering the native hook.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                pw.println("Enter Roborecord filename:");
                fileName = scanner.next();
                performer = new RecordingPerformer();
                try {
                    performer.playRecording(fileName);
                    pw.println("Playing.. Press F3 to stop.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
                break;
            default:
                pw.println("Invalid Choice");
                break;
        }
        pw.close();
    }

}