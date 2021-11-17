package Arbiter;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PhilosopherArbiter implements Runnable{
    private final int MIN_EATING_TIME = 10;
    private final int MAX_EATING_TIME = 20; // ms
    private boolean hungry = true;
    private final Semaphore fork1;
    private final Semaphore fork2;
    private final int id;
    private final Random random = new Random();
    private final Semaphore waiter;
    private int mealCounter = 0;
    private final int maxCounter;
    private long sumWaitingTime = 0;
    private final double[] averageWaitingTimes;
    private boolean debug = false;

    public PhilosopherArbiter(Semaphore fork1, Semaphore fork2, Semaphore arbiter, int number, int n_meals, double[] averageWaitingTimes){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
        this.waiter = arbiter;
        this.maxCounter = n_meals;
        this.averageWaitingTimes = averageWaitingTimes;
    }

    public PhilosopherArbiter(Semaphore fork1, Semaphore fork2, Semaphore arbiter, int number, int n_meals, double[] averageWaitingTimes, boolean debug){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
        this.waiter = arbiter;
        this.maxCounter = n_meals;
        this.averageWaitingTimes = averageWaitingTimes;
        this.debug = debug;
    }

    public double getAverageWaitingTime(){
        return (double)sumWaitingTime/maxCounter;
    }

    public void run(){
        int eating_time;
        long startTime;
        long endTime;
        long waitingTime;

        while(mealCounter < maxCounter){
            try {
                startTime = System.currentTimeMillis();
                waiter.acquire();
                fork1.acquire();
                fork2.acquire();
                    endTime = System.currentTimeMillis();
                    waitingTime = endTime - startTime;
                    sumWaitingTime += waitingTime;
                    eating_time = random.nextInt(MAX_EATING_TIME-MIN_EATING_TIME+1) + MIN_EATING_TIME;
                    if(debug){
                        System.out.println("Jem. ID: " + id + ". Eating time: " + eating_time);
                    }
                    try {
                        Thread.sleep(eating_time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mealCounter++;
                    hungry = false;
                    if(debug){
                        System.out.println("Skończyłem jeść. ID: " + id);
                    }
                fork2.release();
                fork1.release();
                waiter.release();
                hungry = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        averageWaitingTimes[id] = getAverageWaitingTime();
    }

    public static void main(String[] args){
        int n = 5;
        Thread[] philosophers = new Thread[n];
        Semaphore[] forks = new Semaphore[n];
        Semaphore waiter = new Semaphore(n-1);
        int n_meals = 15;
        double[] averageWaitingTimes = new double[n];

        for(int i=0; i<n; i++){
            forks[i] = new Semaphore(1);
        }

        for(int i=0; i<n; i++){
            philosophers[i] = new Thread(new PhilosopherArbiter(forks[i], forks[(i+1) % n], waiter, i, n_meals, averageWaitingTimes, true));
        }

        for(int i=0; i<n; i++){
            philosophers[i].start();
        }

        try{
            for(int i=0; i<n; i++){
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Waiting times:");
        for(int i=0; i<n; i++){
            System.out.println(averageWaitingTimes[i]);
        }
    }
}
