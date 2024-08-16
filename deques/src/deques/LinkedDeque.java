package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        front.prev = null;
        back.prev = front;
        back.next = null;
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        newNode.next = front.next;
        newNode.prev = front;
        front.next.prev = newNode;
        front.next = newNode;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        newNode.next = back;
        back.prev.next = newNode;
        newNode.prev = back.prev;
        back.prev = newNode;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> firstNode = front.next;
        firstNode.next.prev = front;
        front.next = firstNode.next;
        return firstNode.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> lastNode = back.prev;
        lastNode.prev.next = back;
        back.prev = lastNode.prev;
        return lastNode.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> currNode;
        if (index < size / 2) {
            currNode = front.next;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = back.prev;
            for (int i = size - 1; i > index; i--) {
                currNode = currNode.prev;
            }
        }
        return currNode.value;
    }

    public int size() {
        return size;
    }
}
