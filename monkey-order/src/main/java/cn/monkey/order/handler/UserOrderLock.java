package cn.monkey.order.handler;

public interface UserOrderLock {

    boolean tryLock(String orderId,String uid);

    int SUCCESS = 0;
    int ORDER_NOT_EXISTS = 1;
    int BAD_UID = 2;

    /**
     *
     * @param orderId
     * @param uid
     * @return 0: success,  1: order not exists 2:  bad uid
     */
    int unlock(String orderId, String uid);
}
