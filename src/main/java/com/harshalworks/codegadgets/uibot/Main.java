package com.harshalworks.codegadgets.uibot;

import com.harshalworks.codegadgets.uibot.performingOutput.RecordingPerformer;
import com.harshalworks.codegadgets.uibot.recordingInput.GlobalRecorder;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);


        PrintWriter pw = new PrintWriter(System.out);
        Scanner scanner = new Scanner(System.in);
        pw.printf("Note: Press F3 to stop the program." +
                "This free software is provided with NO WARRANTY."+
                "\nMENU:\n1.Record Input\n2.Play Recording\n\nENTER CHOICE:");
        pw.flush();
        int choice = scanner.nextInt();
        String fileName = "";
        RecordingPerformer performer;
        switch (choice) {
            case 1:
                pw.println("Please enter a filename to save recording:");
                pw.flush();
                fileName = scanner.next();
                try {
                    GlobalRecorder.record(fileName);
                    pw.println("Recording started. Press F3 to stop.");
                    pw.flush();
                    ;
                } catch (NativeHookException e) {
                    System.err.println("There was a problem registering the native hook.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                pw.println("Enter Roborecord filename:");
                pw.flush();
                fileName = scanner.next();
                fileName += ".roborecord";
                pw.println("How many times do you want to play? [1-10] Enter number:");
                pw.flush();;
                int num = scanner.nextInt();
                performer = new RecordingPerformer();
                try {
                    pw.println("Playing.. Press F3 to stop.");
                    pw.flush();
                    performer.playRecording(fileName, num);
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
                pw.flush();
                break;
        }
        pw.println("Exiting...");
        pw.close();
    }

}