package pule3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


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
class Zadanie implements Callable<Integer> 
{

    private int poczatek;
    private int koniec;
    private int[] tablica;

    public Zadanie(int[] tablica, int poczatek, int koniec) 
    {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.tablica = tablica;
    }

    public Integer call() throws Exception 
    {
        int wynik = 0;

        for (int i = poczatek; i < koniec; i++) 
        {
            wynik += tablica[i];
            
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ex)
            {
                
            }
        }
        System.out.println(Thread.currentThread().getName());
        System.out.println("Liczyłem sumę od zakresu: " + poczatek + " do zakresu: "
                + koniec + " Wynik: " + wynik);
        return wynik;
    }
}

public class Zadanie3 
{

    public static void main(String[] args) 
    {
        Stoper stoper = new Stoper();
        int suma = 0;
        int[] tablicaLiczb = new int[10000];

        for (int i = 0; i < tablicaLiczb.length; i++) 
        {
            tablicaLiczb[i] = (int) (Math.random() * 11);
        }
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        Zadanie[] zadania = new Zadanie[1000];

        int podzial = tablicaLiczb.length / zadania.length;
        stoper.start();
        
        for (int i = 0; i < zadania.length; i++) 
        {
            zadania[i] = new Zadanie(tablicaLiczb, (i * podzial), ((i + 1) * podzial));
            Future<Integer> future = executor.submit(zadania[i]);
            list.add(future);

        }    
        for(Future<Integer> future : list)
        {
            try
            {
                suma += future.get();
            }
            catch (Exception e)
            {
                
            }
        }
        
        
        executor.shutdown();

        while (!executor.isTerminated()) 
        {
            
        }
        
        stoper.stop();
        System.out.println(stoper.toString());
        System.out.println("Wszystkie wątki zakończyły pracę.");
        System.out.println(suma);
    }
}
