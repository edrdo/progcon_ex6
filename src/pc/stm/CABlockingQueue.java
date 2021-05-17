package pc.stm;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.TArray;
import scala.concurrent.stm.japi.STM;

public class CABlockingQueue<E> {
  
  private final Ref.View<Integer> size; 
  private final Ref.View<Integer> head;
  private final TArray.View<E> array;

  public CABlockingQueue(int capacity) {
    if (capacity <= 0) 
      throw new IllegalArgumentException();
    size = STM.newRef(0);
    head = STM.newRef(0);
    array = STM.newTArray(capacity);
  }

  public int size() {
    return size.get();
  }

  public void add(E elem) {
    STM.atomic(() -> {
      // TODO - Pseudo-code for implementation
      // 1. retry if size == array.length
      // 2. array[(head + size) % array.length] = elem
      // 3. size++
    });
  }

  public E remove() {
    return STM.atomic(() -> {
      // TODO - Pseudo-code for implementation
      // 1. retry if size == 0
      // 2. elem = array[head]
      // 3. head = (head + 1) % array.length
      // 4. size--
      // 5. return elem
      return null;
    });
  }
}
