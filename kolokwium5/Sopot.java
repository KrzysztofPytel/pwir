/*
W Sopocie, jako atrakcja turystyczna, jeździ Ciuchcia. 
Ciuchcia wozi turystów po całym mieście.
Jej pojemność to 10 miejsc. Ciuchcia działa następująco:
•	czeka na turystów,
•	jeśli znajdzie się 10 turystów chętnych na przejażdżkę (wsiądą do ciuchci) ciuchcia zaczyna objazd miasta,
•	po objeździe turyści wysiadają, ciuchcia jest pusta i znowu czeka na turystów.
Napisz aplikację symulująca działanie ciuchci. 
Utwórz odpowiednie klasy i wątki (np. Turysta, Ciuchcia). 
Utwórz 100 turystów, którzy będą chodzić po mieście i co jakiś czas będą próbować przejechać się ciuchcią. 
Dobierz odpowiednie parametry uśpienia wątków, tak by symulacja była łatwa do interpretacji.
Wykorzystaj poznane zagadnienia.
 */
package kolokwium5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Ciuchcia extends Thread {

    public int miejsca = 10;
    public int chetni = 0;
    private List turysci = new ArrayList();
    Semaphore semafor = new Semaphore(1);

    public void run() {
        while (true) {
            if (chetni == miejsca) {
                try {
                    semafor.acquire();
                } catch (InterruptedException ex) {
                }
                System.out.println("Ciuchcia odjezdza zabiera turystow: " + turysci);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                }
                System.out.println("Ciuchcia przyjechala");
                wysiadz();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }

    public synchronized void jestemChetny(String name) throws InterruptedException {
        if (chetni != miejsca) {
            System.out.println(name + " zapisuje sie");
            turysci.add(name);
            chetni++;
            czekajNaOdjazd();
        }
    }

    public synchronized void czekajNaOdjazd() throws InterruptedException {
        wait();
    }

    public synchronized void wysiadz() {
        turysci.clear();
        notifyAll();
        semafor.release();
        chetni = 0;
    }

}

class Turysta extends Thread {

    private Ciuchcia ciuchcia;
    private String name;

    public Turysta(String name, Ciuchcia ciuchcia) {
        this.name = name;
        this.ciuchcia = ciuchcia;
    }

    public void run() {
        while (true) {
            try {
                zwiedzaj();
            } catch (InterruptedException ex) {
            }
            if (ciuchcia.chetni != ciuchcia.miejsca) {
                try {
                    ciuchcia.jestemChetny(name);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    public void zwiedzaj() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 20000 + 3000));
    }
}

public class Sopot {

    public static void main(String[] args) {
        Ciuchcia ciuchcia = new Ciuchcia();
        ciuchcia.start();
        for (int i = 0; i < 100; i++) {
            new Turysta("Turysta" + i, ciuchcia).start();
        }
    }
}
