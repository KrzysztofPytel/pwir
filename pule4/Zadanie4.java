
package pule4;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class LiczbaPierwsza implements Callable<Integer>
{
    private int liczba;
    private int dzielnik;
    
    public LiczbaPierwsza(int liczba, int dzielnik)
    {
        this.liczba = liczba;
        this.dzielnik = dzielnik;
    }
    
    public Integer call() throws Exception 
    {
        double modulo = liczba%dzielnik;
        if(modulo==0)
        {
            return 0;
        }
        else
        {
            return 1;
        }     
    }
    
}
public class Zadanie4 
{
    public static void main (String[] args)
    {
        Scanner pobierzLiczbe = new Scanner(System.in); 
        int liczba = pobierzLiczbe.nextInt();
        int suma = 0;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        LiczbaPierwsza[] sprawdz = new LiczbaPierwsza[liczba];

        for(int i = 1; i<sprawdz.length ; i++ )
        {
            sprawdz[i] = new LiczbaPierwsza(liczba,i);
            Future<Integer> future = executor.submit(sprawdz[i]);
            
            try 
            {  
                suma+=future.get();
            }
            catch (InterruptedException | ExecutionException ex) 
            {
                System.out.println("Blad");
            }
        }
        executor.shutdown();
        
        while(!executor.isTerminated()) {} 
        System.out.println("Wszystkie wątki zakończyły pracę.");
        
        if(suma == liczba-2)
            System.out.println("Liczba jest pierwsza");
        else
            System.out.println("Liczba nie jest pierwsza");
    }
}
