package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final Logger instance = new Logger();

    private Logger(){}

    public static Logger getInstance(){
        return instance;
    }

    public void log(String message){
        try (FileOutputStream fos = new FileOutputStream("logs.log", true)){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            writer.write(String.format(
                    "[%s] %s",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME),
                    message));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
