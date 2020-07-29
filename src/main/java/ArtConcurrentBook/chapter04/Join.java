package ArtConcurrentBook.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 6-13
 * 这段代码用到了Thread.join()，对于这个API的用法，可以查看下面这篇博客
 * https://www.cnblogs.com/duanxz/p/5038471.html
 *
 * 当线程终止时，会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线
 * 程。
 * 这段话来自Thread.join()的JavaDoc
 */
public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有一个前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }

        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}