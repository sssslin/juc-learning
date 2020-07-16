package atomic;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author junfeng.ling
 * @date 2020/7/16 12:41
 * @Description:
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();

        // 创建线程集合
        List<Thread> ts = new ArrayList<Thread>(600);

        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        cas.count();
                        cas.safeCount();
                    }
                }
            });
            // 将线程添加到集合中
            ts.add(t);
        }

        // 依次将线程启动
        for (Thread t : ts) {
            t.start();
        }

        // 等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 最终的结果是：i < 1000000; atomicI = 1000000
        // 这个现象出现的原因是  CPU缓存导致的可见性问题  ,cpu将值读取到缓存中进行进一步计算，最终回写到内存里，有可能
        System.out.println(cas.i);
        System.out.println(cas.atomicI.get());
    }

    // 使用cas实现线程安全计数器
    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if(suc) {
                break;
            }
        }
    }

    // 非线程安全的计数器
    // 由于 线程切换带来的原子性问题，导致实际执行累加的次数小于理论次数
    private void count() {
        i++;
    }
}
