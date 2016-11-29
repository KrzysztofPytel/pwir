package semafory;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Semafor extends Thread
{
    private String nazwa; 
    private static final Semaphore semafor = new Semaphore(1,true);
    
    Semafor(String nazwa)
    {
        this.nazwa = nazwa;
    }
    
    public void run()
    {
        for(;;)
        {
            try
            {
                semafor.acquire();
                System.out.println(nazwa + " w ogrodzie");
                Thread.sleep((int)(Math.random()*100));
            }
            catch (InterruptedException e)
            {
                
            }
            finally
            {
                System.out.println(nazwa + " wychodzi");
                semafor.release();
            }
        }
    }
    
}

public class zadanie1 
{
    public static void main(String[] args)
    {
        new Semafor("Ania").start();
        new Semafor("Adam").start();
    }
}
