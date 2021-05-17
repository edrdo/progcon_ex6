package pc.stm;

import scala.concurrent.stm.japi.STM;

public class TestEx2 {

  public static void main(String[] args) throws InterruptedException {
    CABlockingQueue<Integer> a  = new CABlockingQueue<>(5); 
    CABlockingQueue<Integer> b  = new CABlockingQueue<>(5); 
    int N = 100;
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        a.add(i);
        System.out.println("added " + i);
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        STM.atomic(() -> b.add(a.remove()));
      }
    });
    Thread t3 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        Integer v = b.remove();
        System.out.println("removed " + v);
      }
    });
    t1.start();
    t2.start();
    t3.start();
    t1.join();
    t2.join();
    t3.join();
  }
}
