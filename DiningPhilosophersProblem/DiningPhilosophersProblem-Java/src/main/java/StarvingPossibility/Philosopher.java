package StarvingPossibility;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private final int MIN_EATING_TIME = 1000;
    private final int MAX_EATING_TIME = 2000; // ms
    private boolean hungry = true;
    private final Semaphore fork1;
    private final Semaphore fork2;
    private final int id;
    private final Random random = new Random();
    private final int maxCounter;
    private long sumWaitingTime = 0;
    private final double[] averageWaitingTimes;

    public Philosopher(Semaphore fork1, Semaphore fork2, int number, int n_meals, double[] averageWaitingTimes){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
        this.maxCounter = n_meals;
        this.averageWaitingTimes = averageWaitingTimes;
    }

    private double getAverageWaitingTime(){
        return (double)sumWaitingTime/maxCounter;
    }

    public void run(){
        int eating_time;
        long startTime;
        long endTime;
        long waitingTime;
        int mealCounter = 0;

        startTime = System.currentTimeMillis();
        while(mealCounter < maxCounter){
            try {
                fork1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            if(fork2.tryAcquire()){
                endTime = System.currentTimeMillis();
                waitingTime = endTime - startTime;
                System.out.println("ID: " + id + ". Waiting time: " + waitingTime);
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
                startTime = System.currentTimeMillis();
            }
            fork1.release();
            hungry = true;
        }
        averageWaitingTimes[id] = getAverageWaitingTime();
    }
}

