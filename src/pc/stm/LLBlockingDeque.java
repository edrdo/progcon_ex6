package pc.stm;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class LLBlockingDeque<E> {

  private static class Node<T> {
    T value;
    Ref.View<Node<T>> prev = STM.newRef(null);
    Ref.View<Node<T>> next = STM.newRef(null);
  }

  private final Ref.View<Integer> size; 
  private final Ref.View<Node<E>> head;
  private final Ref.View<Node<E>> tail;

  public LLBlockingDeque() {
    size = STM.newRef(0);
    head = STM.newRef(null);
    tail = STM.newRef(null);
  }

  public int size() {
    return size.get();
  }
  
  public void addFirst(E elem) {
    // TODO
  }
  
  public void addLast(E elem) {
    STM.atomic(() -> {
      Node<E> prevTail = tail.get();
      Node<E> newNode = new Node<>();
      newNode.value = elem;
      newNode.prev.set(prevTail);
      tail.set(newNode);
      if (prevTail != null)
        prevTail.next.set(newNode);
      else 
        head.set(newNode);
      STM.increment(size, 1);
    });
  }
  
  public E removeFirst() {
    return STM.atomic(() -> {
      if (size.get() == 0) 
        STM.retry();
      Node<E> node = head.get();
      Node<E> nextHead = node.next.get();
      E value = node.value;
      head.set(nextHead);
      if (nextHead != null) {
        nextHead.prev.set(null);
        node.next.set(null);
      }
      else { 
        tail.set(null);
      }
      STM.increment(size, -1);
      return value;
    });
  }
  
  public E removeLast() {
    // TODO
    return null;
  }
}
