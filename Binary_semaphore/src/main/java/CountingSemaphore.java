public class CountingSemaphore implements ISemaphore{
    private int _value = 0;
    private int _waiting = 0;
    private Semaphore next_operation = new Semaphore(true); // wyklucza wykonywanie innych operacji na semaforze w trakcie danej operacji
    private Semaphore can_decrement; // wpuszcza, gdy wcześniej semafor ogólny był podniesiony, więc da się go opuścić

    public CountingSemaphore(int initial_state){
        this._value = initial_state;
        if(initial_state > 0){
            can_decrement = new Semaphore(true);
        }
        else{
            can_decrement = new Semaphore(false);
        }
    }

    public int getState() {
        return _value;
    }

    @Override
    public void P() throws InterruptedException { // decrementation
        can_decrement.P();
        next_operation.P();
        _value--;
        if(_value > 0){
            can_decrement.V();
        }
        next_operation.V();
    }

    @Override
    public void V(){ // incrementation
        try {
            next_operation.P();
        } catch (InterruptedException e){
            e.printStackTrace();
            System.exit(-1);
        }

        _value++;
        if(_value > 0){
            can_decrement.V();
        }
         next_operation.V();
    }
}
