package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements Runnable{

    private ReadersWritersPriority rwPriority;
    String filePath = "C:\\JavaTest\\API\\concurrency-readers-writers-problem\\juokeliai.txt";

    public Reader(ReadersWritersPriority rw) {
        this.rwPriority = rw;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                rwPriority.startRead();
                System.out.println(Thread.currentThread().getName() + " reads data");
                System.out.println(line);
                Thread.sleep(200);
                rwPriority.endRead();
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
