//zadanie 2

package zadanie2;

class Napis implements Runnable {

    private String napis;
    private int numer;
    private int pauza;

    public Napis(String napis, int pauza, int numer) {
        this.napis = napis;
        this.pauza = pauza;
        this.numer = numer;
    }

    public void run() {

            System.out.println(napis + numer);
            try {
                Thread.sleep(pauza);
            } catch (InterruptedException e) {
            }
        
    }
}

public class Zadanie2 {

    public static void main(String[] args) 
    {
        
        Napis[] watki = new Napis[10];
        Thread[] watek = new Thread[10];
        for (int i=0; i<10; i++)
        {
            watki[i] = new Napis("Jestem watek: ", 1000, i);
            watek[i] = new Thread(watki[i]);
        }
        for (int i = 0; i<10; i++)
        {
            watek[i].start();
        }

    }
}
