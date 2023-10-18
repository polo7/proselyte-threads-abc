import java.util.concurrent.CountDownLatch;

public class Foo {
    void first() {
        System.out.print("first");
    }

    void second() {
        System.out.print("second");
    }

    void third() {
        System.out.print("third");
    }


    public static void main(String[] args) {

        Foo foo = new Foo();
        CountDownLatch cdlB = new CountDownLatch(1);
        CountDownLatch cdlC = new CountDownLatch(1);

        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                foo.first();
                cdlB.countDown();
            }
        };

        Runnable taskB = new Runnable() {
            @Override
            public void run() {
                try {
                    cdlB.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                foo.second();
                cdlC.countDown();
            }
        };

        Runnable taskC = new Runnable() {
            @Override
            public void run() {
                try {
                    cdlC.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                foo.third();
            }
        };

        Thread threadA = new Thread(taskA);
        Thread threadB = new Thread(taskB);
        Thread threadC = new Thread(taskC);

        threadB.start();
        threadC.start();
        threadA.start();
    }
}
