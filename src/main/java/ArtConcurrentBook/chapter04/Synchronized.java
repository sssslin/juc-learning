package ArtConcurrentBook.chapter04;

/**
 * 6-10
 * 以下两种方式，都是对一个对象的监视器（monitor）进行获取，而这个获取过程是排他的
 * 也就是同一时刻只能有一个线程获取到由synchronized所保护对象的监视器
 */
public class Synchronized {
    public static void main(String[] args) {
        // 对synchronized Class对象进行加锁
        // 同步代码块实现方式：monitor enter和 monitor exit
        synchronized (Synchronized.class) {

        }
        // 静态同步方法，对Synchronized Class对象进行加锁
        // 同步代码块实现方式：通过ACC_SYNCHRONIZED来完成的
        m();
    }

    public static synchronized void m() {
    }
}
