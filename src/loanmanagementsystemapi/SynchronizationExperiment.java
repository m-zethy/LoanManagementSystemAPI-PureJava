/*
package loanmanagementsystemapi;

public class SynchronizationExperiment {

    static int counter = 0;

    public static void main(String[] args)
            throws InterruptedException {

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter++;
            }
        };

        Thread threadA = new Thread(task);
        Thread threadB = new Thread(task);

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("Expected: 200000");
        System.out.println("Actual: " + counter);
    }
}
*/


package loanmanagementsystemapi;

public class SynchronizationExperiment {

    static int counter = 0;

    public static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args)
            throws InterruptedException {

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                increment();
            }
        };

        Thread threadA = new Thread(task);
        Thread threadB = new Thread(task);

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("Expected: 200000");
        System.out.println("Actual: " + counter);
    }
}
