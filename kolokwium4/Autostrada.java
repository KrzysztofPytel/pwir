/*
Po sześciopasmowej autostradzie jadą samochody. 
W pewnym miejscu mamy zwężenie do dwóch pasów (czyli jednocześnie mogą jechać dwa samochody). 
Dodatkowo co jakiś czas na autostradzie pojawia się karetka. Zaimplementuj powyższy system przyjmując, że:
- samochody jadą sobie autostradą, po pewnym czasie dojeżdżają do zwężenia, tylko dwa mogą jednocześnie je pokonać
- jeśli na autostradzie pojawi się karetka, wszystkie samochody zatrzymują się i czekają, 
aż karetka przejedzie przez autostradę, po jej przejeździe samochody jadą dalej.
Zaplanuj poszczególne klasy/wątki. 
Przeprowadź symulację działania autostrady tworząc np. 50 samochodów. 
Dobierz odpowiednie parametry uśpienia wątków, tak by symulacja była łatwa do interpretacji i wyświetlenia na ekranie. 
Wykorzystaj poznane zagadnienia. 
*/
package kolokwium4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Karetka extends Thread{
    public boolean karetka;
    public Karetka(){
        karetka = false;
    }
    public void run()
    {
        while(true){
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
            }
            System.out.println("IO IO IO IO IO IO");
            karetka = true;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            }
            karetkaPojechala();
        }
    }    
    public synchronized void hamuj() throws InterruptedException{
        wait();
    }
    public synchronized void karetkaPojechala(){
            notifyAll();
            System.out.println("karetka sobie pojechala");
            karetka = false;
    }

}
class Pas{
    public String nazwa;
    private Karetka karetka;
    private int kilometryAutostrada = 100;
    private int kilometryZwezenie = 50;
    
    public Pas(String nazwa, Karetka karetka){
        this.nazwa = nazwa;
        this.karetka = karetka;
    }

    public void jedzAutostrada() throws InterruptedException{
        for(int i = 0; i < kilometryAutostrada; i++){
            if(karetka.karetka==true)
                karetka.hamuj();
            Thread.sleep(10);
        }
    }
    
    public void jedzZwezeniem() throws InterruptedException{
        for(int i = 0; i < kilometryZwezenie; i++){
            if(karetka.karetka==true){
                karetka.hamuj();
            }
            Thread.sleep(10);
        }
    }
    

}

class Zwezenie{
    Semaphore zwezenie = new Semaphore(2, true);
    
    public void przejedz() throws InterruptedException{
        zwezenie.acquire();
    }
    
    public void przejechal(){
        zwezenie.release();
    }
}

class Samochod extends Thread{
    private Pas pas;
    private String nazwa;
    private Zwezenie zwezenie;
    
    public Samochod(Pas pas,String nazwa, Zwezenie zwezenie){
        this.pas = pas;
        this.nazwa = nazwa;
        this.zwezenie = zwezenie;
    }
    public void run(){
        System.out.println(nazwa + " wjezdza na pas: " + pas.nazwa);
        try {
            pas.jedzAutostrada();
        } catch (InterruptedException ex) {
        }
        try {
            zwezenie.przejedz();
        } catch (InterruptedException ex) {
        }
        System.out.println(nazwa + " chce przejechac zwezenie");
        try {
            pas.jedzZwezeniem();
        } catch (InterruptedException ex) {
        }
        zwezenie.przejechal();
        System.out.println(nazwa + " przejechal przez zwezenie");
        
    }
}
public class Autostrada {
    public static void main(String[] args) throws InterruptedException{
        int pas;
        Karetka karetka = new Karetka();
        karetka.start();
        Zwezenie zwezenie = new Zwezenie();
        Pas pas1 = new Pas("pas1",karetka);
        Pas pas2 = new Pas("pas2",karetka);
        Pas pas3 = new Pas("pas3",karetka);
        Pas pas4 = new Pas("pas4",karetka);
        Pas pas5 = new Pas("pas5",karetka);
        Pas pas6 = new Pas("pas6",karetka);
        
        for(int i = 0; i<50;i++){
           pas = ThreadLocalRandom.current().nextInt(1, 6 + 1);
           if(karetka.karetka==true)
               karetka.hamuj();
           if(pas == 1)
               new Samochod(pas1, "s"+i, zwezenie).start();
           else if(pas == 2)
               new Samochod(pas2, "s"+i, zwezenie).start();
           else if(pas == 3)
               new Samochod(pas3, "s"+i, zwezenie).start();
           else if(pas == 4)
               new Samochod(pas4, "s"+i, zwezenie).start();
           else if(pas == 5)
               new Samochod(pas5, "s"+i, zwezenie).start();
           else if(pas == 6)
               new Samochod(pas6, "s"+i, zwezenie).start();
           
           Thread.sleep(500);
        }
    }
}
