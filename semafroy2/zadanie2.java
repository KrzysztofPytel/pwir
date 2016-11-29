package semafroy2;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Stan extends Thread
{
    public Stan()
    {
        setDaemon(true);
    }
    
    public void run()
    {
        for(;;)
        {
            System.out.println(" Wolnych miejsc: " + Parking.semafor.availablePermits() + " czeka w kolejce: " + Parking.semafor.getQueueLength());
            try 
            {
                Thread.sleep(5000);
            } 
            catch (InterruptedException e) 
            {
                
            }
        }
    }
}
class Parking extends Thread
{
    private String nazwaSamochodu; 
    public static final Semaphore semafor = new Semaphore(10);
    int stan;
    int prawdopodbienstwo = 75;
    
    Parking(String nazwaSamochodu)
    {
        this.nazwaSamochodu = nazwaSamochodu;
    }
    
    public void run()
    {
        try
        {
            stan = semafor.availablePermits();
            
            if (stan == 0)
            {
                if ((int) (Math.random() * 100) > prawdopodbienstwo)
                {
                    semafor.tryAcquire();
                    System.out.println(nazwaSamochodu + " odjezdza na inny parking");
                }
                else
                {
                    semafor.acquire();
                    System.out.println(nazwaSamochodu + " czeka na wolne miejsce");
                    sleep((int) (Math.random() * 10000));
                    System.out.println(nazwaSamochodu + " opuszcza parking");
                    semafor.release();
                }
            }
            
            else
            {
                System.out.println(nazwaSamochodu + " wjezdza na parking");
                semafor.acquire();
                sleep((int) (Math.random() * 10000));
                System.out.println(nazwaSamochodu + " opuszcza parking");
                semafor.release();
            }
            
        }
        catch (InterruptedException e)
        {

        }
    }
}

public class zadanie2 
{
    public static void main(String[] args)
    {
        new Stan().start();
        for(int i=1;i<100;i++)
        {
            new Parking("Samochod"+i).start();
            try
            {
                Thread.sleep((int) (Math.random() * 2000));
            } 
            catch (InterruptedException ex) 
            {
                
            }
        }
    }
            
}
