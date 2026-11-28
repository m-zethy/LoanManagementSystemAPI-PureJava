package loanmanagementsystemapi;

public class ConcurrencyExperiment {

    public static void main(String[] args) {
    
        TaskA taskA = new TaskA();
        TaskB taskB = new TaskB();
        
        Thread threadA = new Thread(taskA);
        Thread threadB = new Thread(taskB);
        
        threadA.start();
        threadB.start();
    }
    
}

class  TaskA implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(250);
            print();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public TaskA() {}
    
    public void print() throws InterruptedException {
    
        for (int i = 0; i < 5; i++) {

            System.out.println(Thread.currentThread().getName() + ": A");
            Thread.sleep(500);
        }
    }
   
}

class  TaskB implements Runnable {

    @Override
    public void run() {
        try {
            print();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public TaskB() {}
    
    public void print() throws InterruptedException {
    
        for (int i = 0; i < 5; i++) {

            System.out.println(Thread.currentThread().getName() + ": B");
            Thread.sleep(500);
        }
    }
   
}