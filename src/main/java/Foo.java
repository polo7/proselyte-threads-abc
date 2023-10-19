import java.util.concurrent.CountDownLatch;

public class Foo {
    private CountDownLatch cdlB = new CountDownLatch(1);
    private CountDownLatch cdlC = new CountDownLatch(1);

    void first() {
        System.out.print("first");
        cdlB.countDown();
    }

    void second() {
        try {
            cdlB.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("second");
        cdlC.countDown();
    }

    void third() {
        try {
            cdlC.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("third");
    }


    public static void main(String[] args) {
        Foo foo = new Foo();
        Foo foo2 = new Foo();

        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                foo.first();
                foo2.third();
            }
        };

        Runnable taskB = new Runnable() {
            @Override
            public void run() {
                foo.second();
                foo2.second();
            }
        };

        Runnable taskC = new Runnable() {
            @Override
            public void run() {
                foo.third();
                foo2.first();
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
