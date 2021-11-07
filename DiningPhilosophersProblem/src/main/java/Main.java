import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args){
        int n = 5;
        Thread[] philosophers = new Thread[n];
        Semaphore[] forks = new Semaphore[n];

        for(int i=0; i<n; i++){
            forks[i] = new Semaphore(1);
        }

        for(int i=0; i<n-1; i++){
            philosophers[i] = new Thread(new Philosopher(forks[i], forks[i+1], i));
        }
        philosophers[n-1] = new Thread(new Philosopher(forks[n-1], forks[0], n-1));

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
    }
}
