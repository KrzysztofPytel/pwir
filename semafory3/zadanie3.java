package semafory3;

import static java.lang.Thread.sleep;
import java.util.concurrent.*;

class LiterkaX extends Thread {

    private char litera;
    private int uspienie;

    public static final Semaphore semafor = new Semaphore(1);

    LiterkaX(char litera, int uspienie) {
        this.litera = litera;
        this.uspienie = uspienie;
    }

    public synchronized void wyswietl(char x) {
        System.out.println(x);
    }

    public void run() {
        try {
            if (LiterkaY.semafor.availablePermits() == 1) {
                LiterkaY.semafor.acquire();
            }
            if (LiterkaZ.semafor.availablePermits() == 1) {
                LiterkaZ.semafor.acquire();
            }
        } catch (InterruptedException ex) {
        }
        while (true) {
            try {
                semafor.acquire();
                wyswietl(litera);
                sleep(uspienie);
                if (LiterkaY.semafor.availablePermits() == 0 && LiterkaZ.semafor.availablePermits() == 0 && LiterkaX.semafor.availablePermits() == 0) {
                    LiterkaY.semafor.release();
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}

class LiterkaY extends Thread {

    private char litera;
    private int uspienie;

    public static final Semaphore semafor = new Semaphore(1);

    LiterkaY(char litera, int uspienie) {
        this.litera = litera;
        this.uspienie = uspienie;
    }

    public synchronized void wyswietl(char x) {
        System.out.println(x);
    }

    public void run() {
        while (true) {
            try {
                semafor.acquire();
                wyswietl(litera);
                sleep(uspienie);
                if (LiterkaY.semafor.availablePermits() == 0 && LiterkaZ.semafor.availablePermits() == 0 && LiterkaX.semafor.availablePermits() == 0) {
                    LiterkaZ.semafor.release();
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}

class LiterkaZ extends Thread {

    private char litera;
    private int uspienie;

    public static final Semaphore semafor = new Semaphore(1);

    LiterkaZ(char litera, int uspienie) {
        this.litera = litera;
        this.uspienie = uspienie;
    }

    public synchronized void wyswietl(char x) {
        System.out.println(x);
    }

    public void run() {
        while (true) {
            try {
                semafor.acquire();
                wyswietl(litera);
                sleep(uspienie);
                if (LiterkaY.semafor.availablePermits() == 0 && LiterkaZ.semafor.availablePermits() == 0 && LiterkaX.semafor.availablePermits() == 0) {
                    LiterkaX.semafor.release();
                }
            } catch (InterruptedException ex) {
            }
        }
    }
}

public class zadanie3 {

    public static void main(String[] args) throws InterruptedException {
        new LiterkaX('A', 100).start();
        new LiterkaY('B', 300).start();
        new LiterkaZ('C', 900).start();
    }
}
