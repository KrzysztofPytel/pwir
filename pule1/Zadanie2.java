
package pule1;

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;

class Klient implements Runnable
{    
    private String nazwa;
    
    public Klient(String nazwa) 
    { 
        this.nazwa = nazwa;   
    }
    
    public void run()
    {
        try {
            System.out.println(nazwa+" Wchodzi");
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Siada");
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Wybiera");
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Zamawia");
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Konsumuje");
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Placi");
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        
        try {
            System.out.println(nazwa+" Wychodzi");
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }
}

public class Zadanie2 
{
    public static void main(String[] args) 
    { 
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i < 40; i++) 
        { 
            Runnable klient = new Klient("K" + i); 
            executor.execute(klient); 
        } 
        executor.shutdown(); 
        while(!executor.isTerminated()) 
        {

        } 
    }
}
