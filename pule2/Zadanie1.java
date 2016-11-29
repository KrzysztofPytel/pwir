package pule2;

import java.util.concurrent.*;
import java.net.*;
import java.io.*;

class Sprawdz implements Runnable
{
    private String host;
    
    public Sprawdz(String host)
    {
        this.host = host;
    }
    
    public void run()
    {
        Socket socket = null;
        boolean reachable = false;
        try
        {
            socket = new Socket(host,80);
            reachable = true;
        }
        catch (UnknownHostException e){}
        catch (IOException e){}
        finally
        {
            if (socket != null)
                try
                {
                    socket.close();
                }
                catch(IOException e){}
        }
        if (reachable == true)
            System.out.println(host + " jest osiagalny");
        else
            System.out.println(host + " jest nieosiagalny");
    }
}

public class Zadanie1
{
    public static void main(String[] args)
    {
        String[] tablicaHostow = {"onet.pl", "kwejk.pl", "9gag.com", "gazeta.pl", "wp.pl", "piekielni.org",
            "tororor.com", "yahoo.pl" + "koko.net", "kukuryku.com",
            "patataj.org", "trompka.com", "wujek.tw", "trololololo.com"};
        
        ExecutorService exec = Executors.newFixedThreadPool(3);
        
        for(int i = 0; i<tablicaHostow.length;i++)
        {
            Runnable wateczek = new Sprawdz(tablicaHostow[i]);
            exec.execute(wateczek);
        }
        
        exec.shutdown();;
        
        while(!exec.isTerminated())
        {
            
        }
        System.out.println("Wszystkie watki zakonczyly prace");
    }
}


