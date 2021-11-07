package Arbiter;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private final int MIN_EATING_TIME = 1000;
    private final int MAX_EATING_TIME = 2000; // ms
    private final int MIN_FULL_TIME = 500;
    private final int MAX_FULL_TIME = 100;
    private boolean hungry = true;
    private final Semaphore fork1;
    private final Semaphore fork2;
    private final int id;
    private final Random random = new Random();
    private Semaphore waiter;

    public Philosopher(Semaphore fork1, Semaphore fork2, Semaphore arbiter, int number){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
        this.waiter = arbiter;
    }

    public void run(){
        int eating_time;

        while(hungry){
            try {
                //System.out.println("Kelner " + waiter.availablePermits() );
                waiter.acquire();
                //System.out.println("Kelner po acquire " + waiter.availablePermits() );
                fork1.acquire();
                //System.out.println("ID: " + id + ". Podnoszę " + id + " widelec");

                //System.out.println("Kelner " + waiter.availablePermits() );
                waiter.tryAcquire();
                if(fork2.tryAcquire()){
                    //System.out.println("ID: " + id + ". Podnoszę drugi " + id + " widelec");
                    eating_time = random.nextInt(MAX_EATING_TIME-MIN_EATING_TIME+1) + MIN_EATING_TIME;
                    System.out.println("Jem. ID: " + id + ". Eating time: " + eating_time);
                    try {
                        Thread.sleep(eating_time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hungry = false;
                    System.out.println("Skończyłem jeść. ID: " + id);
                    fork2.release();

                }
                waiter.release();
                fork1.release();
                waiter.release();
                hungry = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
