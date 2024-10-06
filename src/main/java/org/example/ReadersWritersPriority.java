package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadersWritersPriority {
    private int readers = 0;
    private int writersWaiting = 0;
    private boolean isWriting = false;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock priorityLock = rwLock.writeLock();
    private final Condition canRead = priorityLock.newCondition();
    private final Condition canWrite = priorityLock.newCondition();

    public void startRead() throws InterruptedException {
        priorityLock.lock();
        try {
            while (writersWaiting > 0 || isWriting) {
                canRead.await();
            }
            readers++;
        } finally {
            priorityLock.unlock();
        }
    }

    public void endRead() {
        priorityLock.lock();
        try {
            readers--;
            if (readers == 0) {
                canWrite.signal();
            }
        } finally {
            priorityLock.unlock();
        }
    }

    public void startWrite() throws InterruptedException {
        priorityLock.lock();
        try {
            writersWaiting++;
            while (isWriting) {
                canWrite.await();
            }
            writersWaiting--;
            isWriting = true;
        } finally {
            priorityLock.unlock();
        }
    }

    public void endWrite() {
        priorityLock.lock();
        try {
            isWriting = false;
            canRead.signalAll();
            canWrite.signal();
        } finally {
            priorityLock.unlock();
        }
    }
}
