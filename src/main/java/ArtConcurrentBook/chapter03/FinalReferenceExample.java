package ArtConcurrentBook.chapter03;

public class FinalReferenceExample {
    final int[] intArray; //final是引用类型
    static FinalReferenceExample obj;

    // 构造函数
    public FinalReferenceExample() {
        intArray = new int[1]; //1
        intArray[0] = 1; //2
    }

    // 写线程A执行
    public static void writerOne() { //д�߳�Aִ��
        obj = new FinalReferenceExample(); //3
    }

    // 写线程B执行
    public static void writerTwo() { //д�߳�Bִ��
        obj.intArray[0] = 2; //4
    }

    // 读线程C执行
    public static void reader() {
        if (obj != null) { //5
            int temp1 = obj.intArray[0]; //6
        }
    }
}
