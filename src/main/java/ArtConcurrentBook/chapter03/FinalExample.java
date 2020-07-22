package ArtConcurrentBook.chapter03;

public class FinalExample {
    int                 i;  // 普通变量
    final int           j;  // final变量
    static FinalExample obj;

    // 构造函数
    public FinalExample() {
        i = 1; // 写普通域
        j = 2; // 写final域
    }

    // 写线程A执行
    public static void writer() { //д�߳�Aִ��
        obj = new FinalExample();
    }

    // 读线程B执行
    public static void reader() {
        FinalExample object = obj; // 读对象引用
        int a = object.i; // 读普通域
        int b = object.j; // 读final域
    }
}