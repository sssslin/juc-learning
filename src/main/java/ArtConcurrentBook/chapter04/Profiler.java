package ArtConcurrentBook.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * 6-15
 *
 * 这个demo用到的ThreadLocal其实核心在于WeakReference
 * 但是在《并发编程的艺术》这本书中，并没有详细的讲解这个部门，
 * 需要通过learningJDK这个项目仔细研究这个东西
 */
public class Profiler {

    public static void main(String[] args) throws Exception {
        // 只要在同一个线程内，分别调用begin()和end()方法即可计算出执行方法耗时
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    // 第一次get()方法调用时会进行初始化(如果set方法没有调用)，每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

}
