package Arbiter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        int n = 10;
        Thread[] philosophers = new Thread[n];
        Semaphore[] forks = new Semaphore[n];
        Semaphore waiter = new Semaphore(n-1);
        int n_meals = 150;
        double[] averageWaitingTimes = new double[n];

        for(int i=0; i<n; i++){
            forks[i] = new Semaphore(1);
        }

        for(int i=0; i<n; i++){
            philosophers[i] = new Thread(new Philosopher(forks[i], forks[(i+1) % n], waiter, i, n_meals, averageWaitingTimes));
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

        try {
            FileWriter writer = new FileWriter("../ChartsCreator/arbiter1.txt");
            for(int i=0; i<n; i++){
                writer.write(String.valueOf((averageWaitingTimes[i])) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
