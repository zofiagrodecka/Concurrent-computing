public class Counter {
    private int counter;

    public void increment(){
        counter++;
    }

    public void increment_synchronized(){
        synchronized (this) {
            counter++;
        }
    }

    public void decrement(){
        counter--;
    }

    public void decrement_synchronized(){
        synchronized (this){
            counter--;
        }
    }

    public int getCounter(){
        return counter;
    }
}
