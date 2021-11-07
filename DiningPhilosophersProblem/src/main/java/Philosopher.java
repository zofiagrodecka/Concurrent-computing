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

    public Philosopher(Semaphore fork1, Semaphore fork2, int number){
        this.fork1 = fork1;
        this.fork2 = fork2;
        this.id = number;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void run(){
        int eating_time;

        while(hungry){
            try {
                fork1.acquire();
                //System.out.println("Podniosłem fork1. ID: " + id);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            if(fork2.tryAcquire()){
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
            fork1.release();
            hungry = true;
        }
    }

    public void rest(){
        fork1.release();
        hungry = true;
    }
}

