public class Counter {
    private int counter = 0;
    private ISemaphore semaphore;

    public Counter(int value, ISemaphore semaphore){
        this.counter = value;
        this.semaphore = semaphore;
    }

    public void increment() throws InterruptedException {
        semaphore.P();
        counter++;
        semaphore.V();
    }

    public void decrement() throws InterruptedException{
        semaphore.P();
        counter--;
        semaphore.V();
    }

    public int getCounter(){
        return counter;
    }
}
