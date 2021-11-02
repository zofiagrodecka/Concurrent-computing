public class Counter {
    private int counter = 0;
    private ISemaphore semaphore;
    private boolean debug = false;

    public Counter(int value, ISemaphore semaphore){
        this.counter = value;
        this.semaphore = semaphore;
    }

    public Counter(int value, ISemaphore semaphore, boolean debug){
        this.counter = value;
        this.semaphore = semaphore;
        this.debug = debug;
    }

    public void increment() throws InterruptedException {
        if(debug)
            System.out.println("Wątek 1 czeka na wejście do monitora");

        semaphore.P();

        if(debug)
            System.out.println("Obudzony wątek: 1");

        counter++;
        semaphore.V();
    }

    public void decrement() throws InterruptedException{
        if(debug)
            System.out.println("Wątek 2 czeka na wejście do monitora");

        semaphore.P();

        if(debug)
            System.out.println("Obudzony wątek: 2");

        counter--;
        semaphore.V();
    }

    public int getCounter(){
        return counter;
    }
}
