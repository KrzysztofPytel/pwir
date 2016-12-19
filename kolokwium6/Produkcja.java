/*
W pewnym zakładzie produkcyjnym jest linia montażowa produktów. 
Linia składa się z kilku procesów zależnych od siebie. Schemat pracy wygląda w sposób następujący:
P11---\
       P21---\
P12---/       \
               P31
P13---\       /
       P22---/
P14---/
Zasady pracy linii:
•	procesy P11 i P12 produkują części dla procesu P21,
•	procesy P13 i P14 produkują części dla procesu P22,
•	procesy P21 i P22 produkują części dla procesu P31.
Procesy mogą wykonywać swoją pracę jeśli:
•	P21 otrzyma części od procesu P11 i P12,
•	P22 otrzyma części od procesu P13 i P14,
•	P31 otrzyma części od procesu P21 i P22.
Procesy, które wyprodukują części powinny dostarczyć je kolejnym procesom jeśli są one gotowe na ich przyjęcie, 
jeśli nie powinny poczekać. Procesy uzależnione od dostaw nie mogę rozpocząć swojej pracy jeśli nie mają wszystkich części.
Napisz aplikację symulująca działanie linii produkcyjnej. 
Utwórz odpowiednie klasy i wątki (np. P11, P12 itd.). 
Dobierz odpowiednie parametry uśpienia wątków, tak aby symulacja generowała w miarę bezproblemową produkcję. 
Przedstaw wyniki na ekranie tak aby można było obserwować postępy prac. Wykorzystaj poznane zagadnienia.
 */
package kolokwium6;

import java.util.concurrent.ThreadLocalRandom;

class Zaklad {

    public void produkuj() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000 + 1000));
    }

}

class ProdukcjaStopien1 extends Thread {

    private Zaklad zaklad;
    boolean tworzonyMaterial = false;
    private String nazwa;
    private String docelowyProdukt;

    public ProdukcjaStopien1(String nazwa, Zaklad zaklad, String docelowy) {
        this.zaklad = zaklad;
        this.nazwa = nazwa;
        this.docelowyProdukt = docelowy;
    }

    public void run() {
        while (true) {
            if (tworzonyMaterial == false) {
                System.out.println(nazwa + " Produkuje czesci do: " + docelowyProdukt);
                try {
                    zaklad.produkuj();
                    tworzonyMaterial = true;
                } catch (InterruptedException ex) {
                }
            } else {
                try {
                    czekajNaPobranie();
                } catch (InterruptedException ex) {
                }

            }
        }

    }

    public synchronized void czekajNaPobranie() throws InterruptedException {
        wait();
    }

    public synchronized boolean pobierz() {
        if (tworzonyMaterial) {
            tworzonyMaterial = false;
            notify();
            System.out.println("Pobrano: " + nazwa);
            return true;
        } else {
            return false;
        }
    }
}

class ProdukcjaStopien2 extends Thread {

    private Zaklad zaklad;
    private ProdukcjaStopien1 material1;
    private ProdukcjaStopien1 material2;
    private String nazwa;
    private boolean material_1;
    private boolean material_2;
    private boolean tworzonyMaterial = false;

    public ProdukcjaStopien2(String nazwa, Zaklad zaklad, ProdukcjaStopien1 material1, ProdukcjaStopien1 material2) {
        this.zaklad = zaklad;
        this.material1 = material1;
        this.material2 = material2;
        this.nazwa = nazwa;
    }

    public void run() {
        while (true) {
            if(material_1 == false)
                material_1 = material1.pobierz();
            if(material_2 == false)
                material_2 = material2.pobierz();

            if (material_1 && material_2) {
                if (tworzonyMaterial == false) {
                    System.out.println(nazwa + " Produkuje czesci do: P31");
                    try {
                        zaklad.produkuj();
                    } catch (InterruptedException ex) {
                    }
                    tworzonyMaterial = true;
                    material_1 = false;
                    material_2 = false;
                } else {
                    try {
                        czekajNaPobranie();
                    } catch (InterruptedException ex) {
                    }
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }

    }

    public synchronized void czekajNaPobranie() throws InterruptedException {
        wait();
    }

    public synchronized boolean pobierz(String produkcja) {
        if (tworzonyMaterial) {
            tworzonyMaterial = false;
            notify();
            System.out.println("Pobrano: " + nazwa);
            return true;
        } else {
            return false;
        }
    }
}

class ProdukcjaStopien3 extends Thread {

    private String nazwa;
    private Zaklad zaklad;
    private ProdukcjaStopien2 material1;
    private ProdukcjaStopien2 material2;
    private boolean material_1;
    private boolean material_2;

    public ProdukcjaStopien3(String nazwa, Zaklad zaklad, ProdukcjaStopien2 material1, ProdukcjaStopien2 material2) {
        this.nazwa = nazwa;
        this.zaklad = zaklad;
        this.material1 = material1;
        this.material2 = material2;
    }

    public void run() {
        while (true) {
            if(material_1 == false)
                 material_1 = material1.pobierz(nazwa);
            if(material_2 == false)
                material_2 = material2.pobierz(nazwa);
            
            if (material_1 && material_2) {
                System.out.println(nazwa + " Produkuje gotowy produkt");
                try {
                    zaklad.produkuj();
                } catch (InterruptedException ex) {
                }
                System.out.println(nazwa + " wytworzyl gotowy produkt!!!!!");
                material_1 = false;
                material_2 = false;

            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }

        }
    }

}

public class Produkcja {

    public static void main(String[] args) {
        Zaklad zaklad = new Zaklad();

        ProdukcjaStopien1 p11 = new ProdukcjaStopien1("p11", zaklad, "p21");
        ProdukcjaStopien1 p12 = new ProdukcjaStopien1("p12", zaklad, "p21");
        ProdukcjaStopien1 p13 = new ProdukcjaStopien1("p13", zaklad, "p22");
        ProdukcjaStopien1 p14 = new ProdukcjaStopien1("p14", zaklad, "p22");

        ProdukcjaStopien2 p21 = new ProdukcjaStopien2("p21", zaklad, p11, p12);
        ProdukcjaStopien2 p22 = new ProdukcjaStopien2("p22", zaklad, p13, p14);

        ProdukcjaStopien3 p31 = new ProdukcjaStopien3("p31", zaklad, p21, p22);

        p11.start();
        p12.start();
        p13.start();
        p14.start();
        p21.start();
        p22.start();
        p31.start();
    }
}
