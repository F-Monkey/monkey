package cn.monkey.state.scheduler.strategy;

class BlockingWaitingStrategy implements WaitingStrategy {

    private final Object LOCK;

    BlockingWaitingStrategy() {
        this.LOCK = new Object();
    }

    @Override
    public void await()  {
        synchronized (this.LOCK) {
            try {
                this.LOCK.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void signalAllWhenBlocking() {
        synchronized (this.LOCK) {
            this.LOCK.notifyAll();
        }
    }
}
