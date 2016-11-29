package zadanie3;

class Watek extends Thread 
{
    private String tekst;
    private int liczbaTabulacji;
    
    public Watek(String tekst, int liczbaTabulacji) 
    {
        this.tekst = tekst;
        this.liczbaTabulacji = liczbaTabulacji;
    }
    
    public void run()
    {
        for(int i = 0;i<500;i++)
        {
            Zadanie3.tabulacje(tekst, liczbaTabulacji, i);
                        try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        
    }
}


public class Zadanie3
{

    public synchronized static void tabulacje(String watek, int liczbaTabulacji, int iteracja)
    {
        for(int i=0;i<liczbaTabulacji;i++)
            System.out.print('\t');
        System.out.println(watek+iteracja);

    }
                
    public static void main(String[] args)
    {
        new Watek("Marek", 1).start();
        new Watek("Kasia", 2).start();
        new Watek("Andrzej", 3).start();
        new Watek("Natalia", 4).start();
        
    }
}
