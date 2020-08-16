package ArtConcurrentBook.chapter05;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 10-15
 */

/**
 * 公平性锁保证了锁的获取按照FIFO的原则，而代价是进行大量的线程切换
 * 非公平锁虽然可能造成线程“饥饿”，但极少的线程切换，保证了其更大的吞吐量
 */
public class FairAndUnfairTest {

    private static Lock           fairLock   = new ReentrantLock2(true);
    private static Lock           unfairLock = new ReentrantLock2(false);
    private static CountDownLatch start;

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        start = new CountDownLatch(1);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Job(lock);
            thread.setName("" + i);
            thread.start();
        }
        start.countDown();
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
            }
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.println("Lock by [" + getName() + "], Waiting by " + ((ReentrantLock2) lock).getQueuedThreads());
                } finally {
                    lock.unlock();
                }
            }
        }

        public String toString() {
            return getName();
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        private static final long serialVersionUID = -6736727496956351588L;

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
