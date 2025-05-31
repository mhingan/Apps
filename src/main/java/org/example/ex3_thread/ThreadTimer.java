package org.example.ex3_thread;

class ThreadTimer extends Thread {
    // Variabila 'running' controleaza daca firul de executie trebuie sa continue
    // 'volatile' asigura ca modificarile sunt vizibile imediat pentru toate thread-urile
    private volatile boolean running = true;

    // 'elapsed' tine cont de timpul scurs in milisecunde
    private volatile long elapsed = 0;

    @Override
    public void run() {
        // Cat timp 'running' este true, firul continua sa ruleze
        while (running) {
            try {
                Thread.sleep(1);  // opreste executia pentru 1 milisecunda
                elapsed++;        // incrementeaza timpul scurs cu 1 ms
            } catch (InterruptedException e) {
                // daca firul este intrerupt, se opreste setand running pe false
                running = false;
            }
        }
    }

    // Returneaza timpul scurs in milisecunde
    public long getElapsed() {
        return elapsed;
    }

    // Reseteaza timpul scurs la 0
    public void reset() {
        elapsed = 0;
    }

    // Opreste firul de executie setand running pe false si il intrerupe daca este adormit
    public void shutdown() {
        running = false;
        this.interrupt(); // forteaza iesirea din sleep
    }
}

