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
    private final Semaphore waiter;
    private int mealCounter = 0;
    private final int maxCounter;
    private long sumWaitingTime = 0;
    private final double[] averageWaitingTimes;

    public Philosopher(Semaphore fork1, Semaphore fork2, Semaphore arbiter, int number, int n_meals, double[] averageWaitingTimes){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
        this.waiter = arbiter;
        this.maxCounter = n_meals;
        this.averageWaitingTimes = averageWaitingTimes;
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
                    System.out.println("Waiting time: " + waitingTime);
                    sumWaitingTime += waitingTime;
                    System.out.println("Jem. ID: " + id);
                    /*eating_time = random.nextInt(MAX_EATING_TIME-MIN_EATING_TIME+1) + MIN_EATING_TIME;
                    System.out.println("Jem. ID: " + id + ". Eating time: " + eating_time);
                    try {
                        Thread.sleep(eating_time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    mealCounter++;
                    hungry = false;
                    System.out.println("Skończyłem jeść. ID: " + id);
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
}
