package golibroda;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class Stoper 
{
    private long start;
    private long stop;
    private String nazwa;

    public Stoper() 
    {
        this("");
    }

    public Stoper(String nazwa) 
    {
        this.nazwa = nazwa;
    }

    public void start() 
    {
        start = System.currentTimeMillis();
    }

    public void stop() 
    {
        stop = System.currentTimeMillis();
    }

    public double pobierzWynik() 
    {
        return (stop - start) / 1000.0;
    }

public String toString(){

return nazwa + ": " + this.pobierzWynik() + " s.";

}

}
class Poczekalnia
{
    private final int MAX;
    
    public Poczekalnia (int MAX)
    {
        this.MAX = MAX;
    }
    
    ArrayList<String> poczekalnia = new ArrayList<String>();
    

    synchronized boolean sprawdz()
    {
        return poczekalnia.isEmpty();
    }
    
    synchronized void usiadz()
    {
        if (poczekalnia.size()!=MAX)
        {
            if(poczekalnia.isEmpty())
            {
                System.out.println("Budze fryzjera");
                notify();
            }    
            
            poczekalnia.add("Klient"+poczekalnia.size());
            System.out.println("Klient siada, stan poczekalni: " + poczekalnia);

        } 
        
        else if(poczekalnia.size()==MAX)
        {
            System.out.println("Poczekalnia pelna. Klient rezygnuje");
        }
    }
    synchronized void spij()
    {
        try 
        {
            while(poczekalnia.isEmpty())
            {
                System.out.println("Fryzjer idzie spac");
                wait();
            }
        } 
        catch (InterruptedException e) {}
    }
    
    void strzyz() 
    {  
        try 
        {
                poczekalnia.remove(0);
                System.out.println("Kielnt strzyzony, stan poczekalni: " + poczekalnia);
                Thread.sleep((int) (Math.random() * (5000-2000+1000) + 2000));

        } 
        catch (InterruptedException e){}
        
    }
    
}

class Golibroda implements Runnable
{
    private Poczekalnia poczekalnia;
    private boolean pustaPoczekalnia = true;
    
    Golibroda(Poczekalnia poczekalnia)
    {
        this.poczekalnia = poczekalnia;
        new Thread(this, "Klient").start();
    }   
    
    public void run()
    {
        
        while(true)
        {
            pustaPoczekalnia = poczekalnia.sprawdz();
            
            if (pustaPoczekalnia == true) 
            {
                poczekalnia.spij();
            }

            if (pustaPoczekalnia == false) 
            {
                poczekalnia.strzyz();
            }
        }
    }
}


class Klient implements Runnable
{
    private Poczekalnia poczekalnia;
    
    Klient(Poczekalnia poczekalnia)
    {
        this.poczekalnia = poczekalnia;
        new Thread(this, "Klient").start();
    }
    
    public void run()
    {

            try {
                for(int i = 0; ;i++)
                {
                    poczekalnia.usiadz();
                    Thread.sleep((int) (Math.random() * (5000-2000+1000) + 200));
                }
            } catch (InterruptedException ex) {}
       
    }
}

public class Zadanie5 
{
    public static void main(String[] args)
    {
        Poczekalnia poczekalnia = new Poczekalnia(10);
        
        new Golibroda(poczekalnia);
        new Klient(poczekalnia);
    }
}
