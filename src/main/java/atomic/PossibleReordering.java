package atomic;

/**
 * @author junfeng.ling
 * @date 2020/7/17 9:46
 * @Description:
 */
public class PossibleReordering {

    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100000; i++) {
            testPossibleReordering();
        }
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("(" + x + "," + y + ")");
    }

    private static void testPossibleReordering() {
    }
}