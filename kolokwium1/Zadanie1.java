/*
W systemie znajdują się dwa typy zasobów: A i B, które są wymienne, ale pierwszy z nich jest wygodniejszy niż drugi. 
Jest m zasobów typu A i n (n > m) zasobów typu B. 
W systemie działają trzy grupy procesów, które różnią się sposobem zgłaszania zapotrzebowania na zasób:

procesy pierwszej grupy żądają wyłącznie wygodnego zasobu (typu A) i czekają, aż będzie dostępny.
procesy drugiej grupy żądają wygodnego zasobu (typu A), lecz jeśli jest niedostępny, to czekają na zasób dowolnego typu,
procesy trzeciej grupy żądają dowolnego zasobu, ale jeśli żaden nie jest dostępny, to nie czekają.

Napisz aplikację implementującą powyższy system. 
Liczba procesów każdej grupy oraz wartości m i n są parametrami programu.
*/

package kolokwium1;

import java.util.concurrent.Semaphore;

class ZasobA {

    private int liczbaZasobow;
    public Semaphore semaforA;

    public ZasobA(int liczbaZasobow) {
        this.liczbaZasobow = liczbaZasobow;
        semaforA = new Semaphore(liczbaZasobow, true);
    }

    public void dodaj() {
        semaforA.tryAcquire();
    }

    public void dodajDoKolejki() {
        try {
            semaforA.acquire();
        } catch (InterruptedException ex) {
        }
    }

    public void zwolnij() {
        semaforA.release();
    }

    public void korzystaj(String name) { 
        System.out.println("[" + name + "}:" + " Korzystam z zasobu A...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}

class ZasobB {

    private int liczbaZasobow;
    public Semaphore semaforB;

    public ZasobB(int liczbaZasobow) {
        this.liczbaZasobow = liczbaZasobow;
        semaforB = new Semaphore(liczbaZasobow, true);
    }

    public void dodaj() {
        semaforB.tryAcquire();
    }

    public void dodajDoKolejki() {
        try {
            semaforB.acquire();
        } catch (InterruptedException ex) {
        }
    }

    public void zwolnij() {
        semaforB.release();
    }

    public void korzystaj(String name) {
        System.out.println("[" + name + "}:" + " Korzystam z zasobu B...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

}

class ProcesA extends Thread {

    private ZasobA zasobA;
    private String name;

    public ProcesA(ZasobA zasobA) {
        this.zasobA = zasobA;
        name = "A";
    }

    public void run() {
        while (true) {
            if (zasobA.semaforA.availablePermits() != 0) {
                zasobA.dodaj();
                zasobA.korzystaj(name);
                zasobA.zwolnij();
            } else {
                System.out.println("[A]: Nie ma wolnego zasobu A, czekam na wolny zasob A");
                zasobA.dodajDoKolejki();
                zasobA.korzystaj(name);
                zasobA.zwolnij();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }

}

class ProcesB extends Thread {

    private ZasobA zasobA;
    private ZasobB zasobB;
    private String name;
    int temp;

    public ProcesB(ZasobA zasobA, ZasobB zasobB) {
        this.zasobA = zasobA;
        this.zasobB = zasobB;
        name = "B";
        temp = (Math.random() <= 0.5) ? 1 : 2;
    }

    public void run() {
        while (true) {
            if (zasobA.semaforA.availablePermits() != 0) {
                zasobA.dodaj();
                zasobA.korzystaj(name);
                zasobA.zwolnij();
            } else if (zasobA.semaforA.availablePermits() == 0) {
                if (zasobB.semaforB.availablePermits() != 0) {
                    zasobB.dodaj();
                    zasobB.korzystaj(name);
                    zasobB.zwolnij();
                } else {
                    if(temp == 1)
                    {
                        System.out.println("[B]: nie ma wolnego zasobu, dolaczam sie do A");
                        zasobA.dodajDoKolejki();
                        zasobA.korzystaj(name);
                        zasobA.zwolnij();
                    }
                    if(temp == 2)
                    {
                        System.out.println("[B]: nie ma wolnego zasobu, dolaczam sie do B");
                        zasobB.dodajDoKolejki();
                        zasobB.korzystaj(name);
                        zasobB.zwolnij();
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}

class ProcesC extends Thread {

    private ZasobA zasobA;
    private ZasobB zasobB;
    private String name;

    public ProcesC(ZasobA zasobA, ZasobB zasobB) {
        this.zasobA = zasobA;
        this.zasobB = zasobB;
        name = "C";
    }

    public void run() {
        while (true) {
            if (zasobA.semaforA.availablePermits() != 0) {
                zasobA.dodaj();
                zasobA.korzystaj(name);
                zasobA.zwolnij();
            } else if (zasobB.semaforB.availablePermits() != 0) {
                zasobB.dodaj();
                zasobB.korzystaj(name);
                zasobB.zwolnij();
            } else {
                System.out.println("[C]: Nie ma wolnych zasobow, poczekam sobie...");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }

}

public class Zadanie1 {

    public static void main(String[] args) {
        ZasobA zasobA = new ZasobA(3);
        ZasobB zasobB = new ZasobB(6);
        
        ProcesA[] procesA = new ProcesA[5];
        ProcesB[] procesB = new ProcesB[5];
        ProcesC[] procesC = new ProcesC[5];
        
        for(int i = 0;i<procesA.length;i++)
        {
            procesA[i] = new ProcesA(zasobA);
            procesA[i].start();
        }
        
        for(int i = 0;i<procesB.length;i++)
        {
            procesB[i] = new ProcesB(zasobA, zasobB);
            procesB[i].start();
        }
        
        for(int i = 0;i<procesC.length;i++)
        {
            procesC[i] = new ProcesC(zasobA, zasobB);
            procesC[i].start();
        }
    }
}
