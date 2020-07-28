package ArtConcurrentBook.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 6-9
 */
// 此方法提供了线程优雅停止的方式，通过Boolean + 线程暂停的方式完成了这种效果
public class Shutdown {
    public static void main(String[] args) throws Exception {

        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        // 睡眠1秒，main线程对CountThread进行中断，使CountThread能够感知中断而结束
        Long start = System.currentTimeMillis();
        System.out.println(start + ""  + Thread.currentThread());

        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();

        long after = System.currentTimeMillis() - start;
        System.out.println( after + ""  +Thread.currentThread() );
        // 经过打印，确实，暂停的是当前的主线程，当主线程在睡眠的期间，cpu的时间片切换到其他线程，其他线程就有足够的时间接受到信息

        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        // 睡眠1秒，main线程对Runner two进行消费，使CountThread能够感知on为false
        TimeUnit.SECONDS.sleep(1);
        two.cancel();
    }

    private static class Runner implements Runnable {
        private long i;

        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
