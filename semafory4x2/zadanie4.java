package semafory4x2;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Fork {
    public Semaphore fork = new Semaphore(1);
    public int id;

    Fork(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean take() {
        return fork.tryAcquire();
    }

    public void putDown() {
        fork.release();
    }
}

class Philosopher extends Thread {

    private Fork fork_low;
    private Fork fork_high;
    private String name;

    Philosopher(Fork fork_low, Fork fork_high, String name) {
        this.fork_low = fork_low;
        this.fork_high = fork_high;
        this.name = name;
        
       // System.out.println("*****   " + name + " fork_low: " + fork_low.getId());
       // System.out.println("*****   " + name + " fork_high: " + fork_high.getId());
    }

    public void run() {

        try {
            sleep(1000);
        } catch (InterruptedException ex) {
        }

        while (true) {
            eat();
        }
    }
    
    private void eat(){
        if(fork_low.take()){
            //System.out.println("++++++ " + name + " picking up (low) : " + fork_low.getId());
            if(fork_high.take()){
                //System.out.println("++++++ " + name + " picking up (high) : " + fork_high.getId());
                try {
                    System.out.println(name + " EATING!!!!!");
                    sleep(2000); // eating;
                } catch (InterruptedException ex) { }
                
                fork_high.putDown();
                //System.out.println("----- " + name + " put down (high) : " + fork_high.getId());
                try {
                    sleep(100);
                } catch (InterruptedException ex) { }
                fork_low.putDown();  
               // System.out.println("----- " + name + " put down (low) : " + fork_low.getId());
            }
            else{
                fork_low.putDown();
               // System.out.println("----- " + name + " put down (low) : " + fork_low.getId());
                try {
                    sleep(1000);
                } catch (InterruptedException ex) { }
            }
        }
    }
}

public class zadanie4 {

    public static void main(String[] args) {
        String[] names = {"Plato", "Aristotle", "Cicero", "Confucius", "Eratosthenes"};
        Fork[] fork = new Fork[5];
        Philosopher[] philosopher = new Philosopher[5];

        for (int i = 0; i < fork.length; i++) {
            fork[i] = new Fork(i);
        }

        for (int i = 0; i < philosopher.length; i++) {

            if (i != philosopher.length - 1) {
                philosopher[i] = new Philosopher(fork[i], fork[i + 1], names[i]);
                philosopher[i].start();
            } else {
                philosopher[i] = new Philosopher(fork[i], fork[0], names[i]);
                philosopher[i].start();
            }
        }
    }
}
