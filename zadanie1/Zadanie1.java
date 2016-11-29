//zadanie 1 / 3
package zadanie1;

class Napis extends Thread 
{

    private String napis;
    private int pauza;
    private int numer;

    public Napis(String napis, int pauza, int numer) 
    {
        this.napis = napis;
        this.pauza = pauza;
        this.numer = numer;
    }

    public void run() 
    {

            System.out.print("jestem" + napis + numer + '\n');
            try 
            {
                sleep(pauza);
            } catch (InterruptedException e) {
            }
        }
    
}

public class Zadanie1 
{

    public static void main(String[] args) 
    {
        Napis[] watki = new Napis[10];
        for(int i = 0; i<10; i++)
        {
            watki[i] = new Napis("Watek: ", 1000, i);


        }
        for(int i = 0; i<10;i++)
        {
            watki[i].start();
                        if ( i == 3)
            {
                watki[i].interrupt();
                System.out.println("Watek " + i + " przerwany");
            }
        }

    }
}
