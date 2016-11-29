package zmiennik;

class Schowek
{
    private int wartosc;
    
    public synchronized int zmien()
    {
        wartosc +=10;
        return wartosc;
    }
    
    public synchronized String toString()
    {
        return ("Wartosc w schowku: " + wartosc);
    }
}

class Zmiennik implements Runnable
{
    Schowek schowek = new Schowek();
    
    public void run ()
    {
        for(int i=0;i<100;i++)
        {
            schowek.zmien();
            System.out.println(schowek.toString());
        }
    }
}


public class Zadanie4 
{
    public static void main (String[] args)
    {
        Zmiennik watek1 = new Zmiennik();
        Thread zmiennik1 = new Thread(watek1);
        zmiennik1.start();
        
        Zmiennik watek2 = new Zmiennik();
        Thread zmiennik2 = new Thread(watek2);
        zmiennik2.start();
        
        
    }
}
