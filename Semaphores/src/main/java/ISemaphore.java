public interface ISemaphore {
    void P() throws InterruptedException;
    void V() throws InterruptedException;
}
