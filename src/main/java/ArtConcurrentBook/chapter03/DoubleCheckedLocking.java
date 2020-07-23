package ArtConcurrentBook.chapter03;

public class DoubleCheckedLocking { //1
    private static Instance instance; //2

    public static Instance getInstance() { //3
        if (instance == null) { //4:第一次检查
            synchronized (DoubleCheckedLocking.class) { //5:加锁
                if (instance == null) //6：第二次检查
                    instance = new Instance(); //7:问题的根源出在这里

                /**
                 * 第七步可以分解为以下的伪代码
                 *（第二步和第三步可能会重排序）
                 * memory = allocate();// 分配对象的内存空间
                 * ctorInstance(memory);// 设置instance指向刚分配的内存地址
                 * 注意，此时对象还没有被初始化
                 * instance = memory;// 初始化对象
                 */
            } //8
        } //9
        return instance; //10
    } //11

    static class Instance {
    }
}
