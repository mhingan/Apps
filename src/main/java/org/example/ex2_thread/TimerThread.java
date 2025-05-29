package org.example.ex2_thread;

class TimerThread extends Thread {
    private volatile boolean running = true;
    private volatile long elapsed = 0;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1);      // dormim 1 ms
                elapsed++;            // incrementăm contorul
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public long getElapsed() {
        return elapsed;
    }

    public void reset() {
        elapsed = 0;
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}

