package org.example;

public class Main {
    public static void main(String[] args) {

        ReadersWritersPriority readersWriters = new ReadersWritersPriority();

        // Sukuriame skaitytojų ir rašytojų gijas
        Thread reader1 = new Thread(new Reader(readersWriters), "Reader1");
        Thread reader2 = new Thread(new Reader(readersWriters), "Reader2");
        Thread writer1 = new Thread(new Writer(readersWriters), "Writer1");

        // Pradedame gijas
        reader1.start();
        reader2.start();
        writer1.start();



    }
}