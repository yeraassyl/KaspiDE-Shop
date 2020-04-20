package com.company.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public enum Target{
        CONSOLE,
        FILE
    }

    private static final Logger instance = new Logger();

    private static final String FILE = "shop_log.log";
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_DATE_TIME;
    private static Target outputTarget;
    private Logger(){}

    public static void setOutputTarget(Target outputTarget) {
        Logger.outputTarget = outputTarget;
    }

    public static Logger getInstance(){
        return instance;
    }

    public void log(String message){
        if (!hasOutputTarget()){
            logToConsole(message);
            logToFile(message);
        }
        else{
            log(outputTarget, message);
        }
    }

    public void log(Target outputTarget, String message){
        switch (outputTarget){
            case CONSOLE:
                logToConsole(message);
                break;
            case FILE:
                logToFile(message);
                break;
        }
    }

    private void logToConsole(String message){
        System.out.println(message);
    }

    private void logToFile(String message){
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write(message + '\n');
        }
        catch (IOException exception) {
            System.err.println("Something went wrong: " + exception);
        }
    }

    private boolean hasOutputTarget(){
        return outputTarget != null;
    }

    private String now(){
        return LocalDateTime.now().format(FORMAT);
    }
}
