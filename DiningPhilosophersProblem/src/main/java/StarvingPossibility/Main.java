package StarvingPossibility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        int n = 5;
        Thread[] philosophers = new Thread[n];
        Semaphore[] forks = new Semaphore[n];
        int n_meals = 5;
        double[] averageWaitingTimes = new double[n];

        for(int i=0; i<n; i++){
            forks[i] = new Semaphore(1);
        }

        for(int i=0; i<n; i++){
            philosophers[i] = new Thread(new Philosopher(forks[i], forks[(i+1) % n], i, n_meals, averageWaitingTimes));
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
            /*File file = new File("file1.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }*/
            FileWriter writer = new FileWriter("file1.txt");
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
