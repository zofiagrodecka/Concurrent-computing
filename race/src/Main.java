import java.sql.SQLOutput;

public class Main {

    public static void main(String[] args){
        int n = 100000000;
        Counter counter = new Counter();
        Runnable incr = () -> {
            for(int i=0; i<n; i++){
                counter.increment();
            }
        };

        Runnable decr = () ->{
            for(int i=0; i<n; i++){
                counter.decrement();
            }
        };

        Thread thread1 = new Thread(incr);
        Thread thread2 = new Thread(decr);

        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(counter.getCounter());
        System.out.println("Time without synchronization: " + (endTime-startTime) + "ms");

        Counter counter_synchr = new Counter();
        Runnable incr_synchr = () -> {
            for(int i=0; i<n; i++){
                counter_synchr.increment_synchronized();
            }
        };

        Runnable decr_synchr = () ->{
            for(int i=0; i<n; i++){
                counter_synchr.decrement_synchronized();
            }
        };

        Thread thread1_synchr = new Thread(incr_synchr);
        Thread thread2_synchr = new Thread(decr_synchr);

        long startTime_synchr = System.currentTimeMillis();
        thread1_synchr.start();
        thread2_synchr.start();

        try {
            thread1_synchr.join();
            thread2_synchr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime_synchr = System.currentTimeMillis();
        System.out.println(counter_synchr.getCounter());
        System.out.println("Time with synchronization: " + (endTime_synchr-startTime_synchr) + "ms");
    }
}
