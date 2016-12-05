/*
Mamy supermarket, w którym dostępne są 4 kasy. 
Po markecie krążą klienci (napisz odpowiednią klasę, która będzie przechowywała nazwę klienta K1, K2 itd. 
oraz liczbę produktów w jego koszyku – wartość losowa).

Klienci będą tworzeni co jakiś czas i będą podążać ze swoimi zakupami do kasy. 
Wybór kasy uzależniony jest od długości kolejki do kasy oraz od liczby towarów w koszykach klientów czekających do kas. 
Klient wybiera kasę na podstawie:

- najkrótszej kolejki,
- albo najmniejszej liczby towarów, które są w koszykach do danej klasy.

Napisz aplikację symulująca działanie supermarketu z kasami. 
Utwórz odpowiednie klasy i wątki. Dobierz odpowiednie parametry uśpienia wątków, 
tak aby symulacja generowała w miarę rozsądne wyniki (łatwe do zaobserwowania).
Przedstaw te wyniki na ekranie tak aby można było obserwować stany kas 
(każdy klient jest obsługiwany przez jakiś czas uzależniony od liczby towarów, które ma w koszyku). 
Wykorzystaj poznane zagadnienia
*/

package kolokwium2;

import java.util.Random;

class Kasa {

    private int kolejka;

    public Kasa() {
        kolejka = 0;
    }

    public synchronized void podejdz() {
        kolejka++;
    }
    

    public void obsluz(int liczba) {
        try {
            Thread.sleep(100 * liczba);
        } catch (InterruptedException ex) {
        }
    }

    public int sprawdz() {
        return kolejka;
    }

    public synchronized void odejdz() {
        kolejka--;
    }
}

class Klient extends Thread {

    private int liczbaZakupow;
    private String name;
    private Kasa k1;
    private Kasa k2;
    private Kasa k3;
    private Kasa k4;

    Klient(String name, int liczbaZakupow, Kasa k1, Kasa k2, Kasa k3, Kasa k4) {
        this.name = name;
        this.liczbaZakupow = liczbaZakupow;
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
    }
    
    public void stan()
    {
        System.out.print("Kasa1: " + k1.sprawdz());
        System.out.print(", Kasa2: " + k2.sprawdz());
        System.out.print(", Kasa3: " + k3.sprawdz());
        System.out.print(", Kasa4: " + k4.sprawdz());
    }

    public void run() {
        if (k1.sprawdz() <= k2.sprawdz() && k1.sprawdz() <= k3.sprawdz() && k1.sprawdz() <= k4.sprawdz()) {
            stan();
            System.out.println(" ["+name+"]: Podchodze do kasy 1...");
            k1.podejdz();
            k1.obsluz(liczbaZakupow);
            k1.odejdz();
        } else if (k2.sprawdz() <= k1.sprawdz() && k2.sprawdz() <= k3.sprawdz() && k2.sprawdz() <= k4.sprawdz()) {
            stan();
            System.out.println(" ["+name+"]: Podchodze do kasy 2...");
            k2.podejdz();
            k2.obsluz(liczbaZakupow);
            k2.odejdz();
        } else if (k3.sprawdz() <= k1.sprawdz() && k3.sprawdz() <= k2.sprawdz() && k3.sprawdz() <= k4.sprawdz()) {
            stan();
            System.out.println(" ["+name+"]: Podchodze do kasy 3...");
            k3.podejdz();
            k3.obsluz(liczbaZakupow);
            k3.odejdz();
        } else if (k4.sprawdz() <= k1.sprawdz() && k4.sprawdz() <= k2.sprawdz() && k4.sprawdz() <= k3.sprawdz()) {
            stan();
            System.out.println(" ["+name+"]: Podchodze do kasy 4...");
            k4.podejdz();
            k4.obsluz(liczbaZakupow);
            k4.odejdz();
        }
    }
}

public class Zadanie2 {

    public static void main(String[] args) {
        Random r = new Random();
        Kasa k1 = new Kasa();
        Kasa k2 = new Kasa();
        Kasa k3 = new Kasa();
        Kasa k4 = new Kasa();
        
        Klient[] klienci = new Klient[1000];

        for (int i = 0; i < klienci.length; i++) {
            klienci[i] = new Klient("K" + i, r.nextInt(30 - 10 + 1) + 10, k1, k2, k3, k4);
        }
        
        for(int i = 0 ; i< klienci.length; i++) {
            klienci[i].start();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {}
        }
    }
}
