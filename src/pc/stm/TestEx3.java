package pc.stm;

import scala.concurrent.stm.japi.STM;

public class TestEx3 {
  public static void main(String[] args) throws Exception {
    //test1();
    test2();
  }
  static void test1() throws Exception {
    LLBlockingDeque<Integer> a  = new LLBlockingDeque<>(); 
    LLBlockingDeque<Integer> b  = new LLBlockingDeque<>(); 
    int N = 100;
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        a.addLast(i);
        System.out.println("added " + i);
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        STM.atomic(() -> b.addLast(a.removeFirst()));
      }
    });
    Thread t3 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        Integer v = b.removeFirst();
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
  
  static void test2() throws Exception {
    LLBlockingDeque<Integer> a  = new LLBlockingDeque<>(); 
    LLBlockingDeque<Integer> b  = new LLBlockingDeque<>(); 
    int N = 100;
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        a.addFirst(i);
        System.out.println("added " + i);
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        STM.atomic(() -> b.addFirst(a.removeLast()));
      }
    });    
    Thread t3 = new Thread(() -> {
      for (int i = 0; i < N; i++) {
        Integer v = b.removeLast();
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
