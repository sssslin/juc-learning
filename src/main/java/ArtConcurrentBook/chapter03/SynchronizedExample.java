package ArtConcurrentBook.chapter03;

class SynchronizedExample {
    int     a    = 0;
    boolean flag = false;

    // 获取锁
    public synchronized void writer() {
        a = 1;
        flag = true;
    }
    // 释放锁

    // 获取锁
    public synchronized void reader() { 
        if (flag) {
            int i = a;

        }
    }
    // 获取锁
}
