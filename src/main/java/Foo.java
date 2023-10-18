public class Foo {

    public void first() {
        System.out.print("first");
    }

    public void second() {
        System.out.print("second");
    }

    public void third() {
        System.out.print("third");
    }

    public static void main(String[] args) {

        Foo foo = new Foo();

        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                foo.first();
            }
        };

        Runnable taskB = new Runnable() {
            @Override
            public void run() {
                foo.second();
            }
        };

        Runnable taskC = new Runnable() {
            @Override
            public void run() {
                foo.third();
            }
        };

        Thread threadA = new Thread(taskA);
        Thread threadB = new Thread(taskB);
        Thread threadC = new Thread(taskC);

        threadB.start();
        threadA.start();
        threadC.start();
    }
}
