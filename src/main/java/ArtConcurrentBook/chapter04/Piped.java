package ArtConcurrentBook.chapter04;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 6-12
 */
public class Piped {

    public static void main(String[] args) throws Exception {
        // 管道输入/输出流 和 普通的文件输入/输出流 或者 网络输入/输出流 不同之处在于
        // 它主要用于线程之间的数据
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {
            }
        }
    }
}
