public class Semaphore implements ISemaphore{
    private boolean _state = true;
    private int _waiting = 0; // czeka

    public Semaphore(boolean initial_state){
        this._state = initial_state;
    }

    public boolean getState(){
        return _state;
    }

    @Override
    public synchronized void P() throws InterruptedException { // decrementation
        if(!_state){
            _waiting ++;
            while(! _state){
                wait();
            }
        }
        _state = false;
        notifyAll();
    }

    @Override
    public synchronized void V() { // incrementation
        /*while (_state) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        _state = true;
        notifyAll();*/

        if(_waiting > 0){
            notify();
            _waiting--;
        }
        _state = true;
    }
}
