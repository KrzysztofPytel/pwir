package ProducentKonsument;

import java.util.*;

class Towar
{
    private String produkt;
    private double cena;  
    private String[] produkty = {"Samochodzik", "Lalka", "Mleko", "Maslo", "Chleb", "Mydlo", "Proszek", "Woda"};
    
    public Towar() 
    {
        int rozmiar = produkty.length-1;
        
        produkt = produkty[(int)(Math.random() * (rozmiar-0+1) + 0)];
        cena = Math.round(Math.random() * 20);
    }
    
    public String toString() 
    {
        return produkt + " Cena: " + cena;
    }
}

class Pojemnik
{
    private int max;
    public Pojemnik(int max)
    {
        this.max = max;
    }
    
   
    LinkedList<String> pudelko = new LinkedList<String>();

    
    synchronized void get() 
    {
        if(pudelko.isEmpty())
        {
            try 
            {
                System.out.println ("---------------");
                System.out.println ("Konsument: **" + Thread.currentThread().getName() + "** czekam na produkcję...");
                System.out.println ("---------------");
                
                wait();
            } catch (InterruptedException e) {}
        }
        if(!pudelko.isEmpty())
        {
            int wolne = max-pudelko.size();
            System.out.println("Klient **" + Thread.currentThread().getName() + "** kupuje: " + pudelko.peekLast() + " pozostalo : " + wolne + " miejsc");
            pudelko.removeLast();

            notify();
        }
    }
    
    synchronized void put() 
    {
        Towar towar = new Towar();
        if(pudelko.size()==max) 
        {
            try 
            {
                System.out.println ("---------------");
                System.out.println ("Producent: czekam na klientow...");
                System.out.println ("---------------");
                wait();
            } catch (InterruptedException e) {}
        }
        
        pudelko.add(towar.toString());
        int wolne = max-pudelko.size();
        System.out.println("Klade do pudelka " + towar.toString() + " pozostalo: " + wolne + " miejsc");

        notify();
    }
}


class Producent implements Runnable
{
    private Pojemnik pojemnik;
    
    Producent(Pojemnik pojemnik)
    {
        this.pojemnik = pojemnik;
        new Thread(this, "Producent").start();
    }
    
    public void run()
    {
        for(int i = 0; ; i++)
        {
            
            pojemnik.put();
            try 
            {
                Thread.sleep((int)(Math.random() * 300));
            } catch (InterruptedException ex) {}
        }
    }
}

class Konsument implements Runnable
{
    private Pojemnik pojemnik;
    
    Konsument(Pojemnik pojemnik,int n)
    {
        this.pojemnik = pojemnik;
        new Thread(this, "Konsument"+n).start();
    }
    public void run()
    {
        for(int i = 0; ; i++)
        {
            pojemnik.get();
            try 
            {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException ex) {}
        }
    }
}
public class ProducentKonsument 
{
    public static void main(String[] args)
    {
        Pojemnik pojemnik = new Pojemnik(10);
        
        new Producent(pojemnik);
        new Konsument(pojemnik,1);
        new Konsument(pojemnik,2);
        new Konsument(pojemnik,3);
        new Konsument(pojemnik,4);
        
    }
}
