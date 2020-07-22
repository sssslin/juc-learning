package ArtConcurrentBook.chapter03;

/**
 * 在x86处理器中，仅会对写-读操作做重排序
 * 在x86中，JMM仅需在volatile写后面插入一个
 * storeload屏障即可实现正确的volatile写-读内存语义
 */
class VolatileBarrierExample {
    int          a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        int i = v1; // 第一个volatile读
        int j = v2; // 第二个volatile读
        a = i + j; //  普通写
        v1 = i + 1; // 第一个volatile写
        v2 = j * 2; // 第二个volatile写
    }
    // 其他方法
}
