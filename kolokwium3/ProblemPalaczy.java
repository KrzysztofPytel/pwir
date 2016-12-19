/*
W systemie są trzy procesy typu ProblemPalaczy oraz jeden proces typu Agent. 
Każdy ProblemPalaczy chciałby bez przerwy skręcać papierosy i wypalać je. 
Czynność ta wymaga posiadania trzech składników: tytoniu, papieru i zapałki.
Jeden ProblemPalaczy ma tytoń, drugi - papier, a trzeci zapałki. 
Każdy z Palaczy ma nieskończenie wielkie zasoby „swojego” składnika i żadnych zapasów pozostałych składników. 
W zaspokojeniu pragnień Palaczy pomaga Agent, który kładzie na stole dwa składniki (wybrane losowo). 
ProblemPalaczy, który ma trzeci składnik zabiera ze stołu pozostałe dwa, skręca papierosa i wypala go. 
Agent czeka, aż ProblemPalaczy skończy się delektować. Następnie cały cykl się powtarza. 
Zaimplementuj opisany system przedstaw jego wyniki na ekranie.
 */
package kolokwium3;

import java.util.concurrent.ThreadLocalRandom;

class Palarnia {

    private boolean tyton, papier, zapalki;

    public Palarnia() {
        tyton = false;
        papier = false;
        zapalki = false;
    }

    public void polozPapier(boolean p) {
        papier = p;

    }

    public void polozTyton(boolean t) {
        tyton = t;
    }

    public void polozZapalki(boolean z) {
        zapalki = z;
    }

    public boolean sprawdzPapier() {
        return papier;
    }

    public boolean sprawdzTyton() {
        return tyton;
    }

    public boolean sprawdzZapalki() {
        return zapalki;
    }

    public void wezPapier() {
        papier = false;
    }

    public void wezZapalki() {
        zapalki = false;
    }

    public void wezTyton() {
        tyton = false;
    }

    public synchronized void obudzAgenta() {
        notifyAll();
    }

    public synchronized void spij() throws InterruptedException {
        wait();
    }

    public void delektujSie() throws InterruptedException {
        Thread.sleep(3000);
    }
}

class PalaczTyton implements Runnable {

    private Palarnia palarnia;

    public PalaczTyton(Palarnia palarnia) {
        this.palarnia = palarnia;
    }

    public void run() {
        while (true) {
            if (palarnia.sprawdzPapier() && palarnia.sprawdzZapalki()) {
                try {
                    System.out.println("Palacz posiadajacy tyton delektuje sie");
                    palarnia.wezPapier();
                    palarnia.wezZapalki();
                    palarnia.delektujSie();
                    palarnia.obudzAgenta();
                } catch (InterruptedException ex) {
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}

class PalaczPapier implements Runnable {

    private Palarnia palarnia;

    public PalaczPapier(Palarnia palarnia) {
        this.palarnia = palarnia;
    }

    public void run() {
        while (true) {
            if (palarnia.sprawdzTyton() && palarnia.sprawdzZapalki()) {
                try {
                    System.out.println("Palacz posiadajacy papier delektuje sie");
                    palarnia.wezTyton();
                    palarnia.wezZapalki();
                    palarnia.delektujSie();
                    palarnia.obudzAgenta();
                } catch (InterruptedException ex) {
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}

class PalaczZapalki implements Runnable {

    private Palarnia palarnia;

    public PalaczZapalki(Palarnia palarnia) {
        this.palarnia = palarnia;
    }

    public void run() {
        while (true) {
            if (palarnia.sprawdzPapier() && palarnia.sprawdzTyton()) {
                try {
                    System.out.println("Palacz posiadajacy zapalki delektuje sie");
                    palarnia.wezTyton();
                    palarnia.wezPapier();
                    palarnia.delektujSie();
                    palarnia.obudzAgenta();
                } catch (InterruptedException ex) {
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}

class Agent implements Runnable {

    private Palarnia palarnia;
    int zasob1, zasob2;

    public Agent(Palarnia palarnia) {
        this.palarnia = palarnia;
    }

    public void run() {
        while (true) {
            zasob1 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            zasob2 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            while (zasob1 == zasob2) {
                zasob2 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            }

            if (zasob1 == 1 || zasob2 == 1) {
                palarnia.polozPapier(true);
            }
            if (zasob1 == 2 || zasob2 == 2) {
                palarnia.polozTyton(true);
            }
            if (zasob1 == 3 || zasob2 == 3) {
                palarnia.polozZapalki(true);
            }

            try {
                palarnia.spij();
            } catch (InterruptedException ex) {
            }
        }
    }
}

public class ProblemPalaczy {

    public static void main(String[] args) {

        Palarnia palarnia = new Palarnia();

        new Thread(new Agent(palarnia)).start();
        new Thread(new PalaczZapalki(palarnia)).start();
        new Thread(new PalaczTyton(palarnia)).start();
        new Thread(new PalaczPapier(palarnia)).start();
    }

}
