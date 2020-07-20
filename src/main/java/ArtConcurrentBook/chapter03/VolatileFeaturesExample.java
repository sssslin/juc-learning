package ArtConcurrentBook.chapter03;

/**
 * 对volatile变量的单个读/写,看成是使用同一个锁对
 * 这些个读/写操作做了同步
 */
class VolatileFeaturesExample {

    // 使用volatile声明64位的long型变量
    volatile long vl = 0L;

    public void set(long l) {
        // 单个volatile变量的写
        vl = l;
    }

    public void getAndIncrement() {
        // 复合（多个）volatile变量的读/写
        vl++;
    }

    public long get() {
        // 单个volatile变量的读
        return vl;
    }
}
