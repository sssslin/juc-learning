/**
 * 
 */
package ArtConcurrentBook.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 10-10
 */

/**
 * state使用AQS中的state，在ReentrantLock（可重入锁）中，state取值范围 > 0
 * 非重入锁取值范围 0 或 1
 * 在这个demo中，取值范围是[0, 1, 2]
 */
public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -7889272986162341211L;

        //初始化同步状态为2，表示没有线程对资源进行访问
        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero.");
            }
            setState(count);
        }

        //获取同步状态：使用循环CAS操作来保证设置status的线程安全
        public int tryAcquireShared(int reduceCount) {
            for (;;) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    // 结果 < 0,表示获取锁失败；
                    // =0，表示获取锁成功，但无剩余锁；
                    // = 1，表示获取锁成功，还有剩余锁；
                    return newCount;
                }
            }
        }

        //释放同步状态：因为可能两个线程同时进行释放，需要使用循环CAS来保证安全
        public boolean tryReleaseShared(int returnCount) {
            for (;;) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    public void lock() {
        /* 状态加1，sync.acquireShared(1);这个方法是AQS里的方法，
         * 该方法会调用我们重写的tryAcquireShared(arg)方法，尝试获取锁，
         * 如果返回值小于0，则表示获取不成功，那么就会调用doAcquireShared(arg)
         * 方法，将该线程加入到队列中，以ACS方法加入队列，会无限循环直至加入队列成功*/
        sync.acquireShared(1);
    }

    public void unlock() {
        /*状态减1，sync.releaseShared(1);会调用我们重写的tryReleaseShared(arg)
         * 方法，进行释放锁操作，直到释放锁成功，释放成功返回true，
         * 会调用doReleaseShared()方法唤醒后继节点*/
        sync.releaseShared(1);
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
