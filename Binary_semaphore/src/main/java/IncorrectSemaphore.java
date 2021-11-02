public class IncorrectSemaphore implements ISemaphore{
    private boolean _state = true;
    private int _waiting = 0; // czeka

    public IncorrectSemaphore(boolean initial_state){
        this._state = initial_state;
    }

    public boolean getState(){
        return _state;
    }

    @Override
    public synchronized void P() throws InterruptedException { // decrementation
        if(!_state){
            _waiting ++;
            wait();
        }
        _state = false;
        notify();
    }

    @Override
    public synchronized void V(){ // incrementation
        if(_waiting > 0){
            notify();
            _waiting--;
        }
        _state = true;
    }
}
