public class Semaphore implements ISemaphore{
    private boolean _state = true;

    public Semaphore(boolean initial_state){
        this._state = initial_state;
    }

    public boolean getState(){
        return _state;
    }

    @Override
    public synchronized void P() throws InterruptedException { // decrementation
        while(! _state){
            wait();
        }
        _state = false;
        notify();
    }

    @Override
    public synchronized void V() throws InterruptedException { // incrementation
        while (_state) {
            wait();
        }
        _state = true;
        notify();
    }
}
