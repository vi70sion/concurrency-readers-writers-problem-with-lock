package org.example;

import java.io.*;

public class Writer implements Runnable{

    private ReadersWritersPriority rwPriority;
    String filePath = "C:\\JavaTest\\API\\concurrency-readers-writers-problem\\juokeliai.txt";
    String textToWrite = "Nauja eilutė ";
    int lineCount = 5;

    public Writer(ReadersWritersPriority rw) {
        this.rwPriority = rw;
    }

    @Override
    public void run() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath, true));
            int line = 1;
            while (lineCount > 0) {
                rwPriority.startWrite();
                System.out.println(Thread.currentThread().getName() + " writes data");
                writer.newLine();
                writer.write(textToWrite + line++);
                Thread.sleep(500);
                rwPriority.endWrite();
                Thread.sleep(500);
                lineCount -= 1;
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        } catch (IOException e) {
            System.out.println("File not found");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
