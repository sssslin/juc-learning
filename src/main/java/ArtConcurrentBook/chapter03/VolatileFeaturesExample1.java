package ArtConcurrentBook.chapter03;

class VolatileFeaturesExample1 {
    // 64位的long型普通变量
    long vl = 0L;

    // 对单个的普通变量的写用同一个锁同步
    public synchronized void set(long l) {//�Ե�������ͨ������д��ͬһ����ͬ��
        vl = l;
    }

    // 普通方法调用
    public void getAndIncrement() {
        // 调用已同步的方法
        long temp = get();
        // 普通写操作
        temp += 1L;
        // 调用已同步的写方法
        set(temp);
    }

    public synchronized long get() {
        // 对单个的普通变量的读用同一个锁同步
        return vl;
    }
}
