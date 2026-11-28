
package loanmanagementsystemapi;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerExperiment {

    static BlockingQueue<String> queue =
            new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {

            try {
                for (int i = 1; i <= 10; i++) {

                    String item = "Task " + i;

                    boolean verdict = queue.offer(item);
                    
                    if (!verdict) {
                    
                        System.out.println("Queue full. Task rejected.");
                    } else {
                    
                        System.out.println("Produced: " + item);
                    }

                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });

        Thread consumer = new Thread(() -> {

            try {
                for (int i = 1; i <= 5; i++) {

                    String item = queue.take();

                    System.out.println(
                        "Consumed: " + item
                    );

                    Thread.sleep(1500);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        });

        producer.start();
        consumer.start();
    }
}
