package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        if (list.front != null && list.front.next != null && list.front.next.next != null) {
            ListNode temp1 = list.front;
            ListNode temp2 = list.front.next;
            ListNode temp3 = list.front.next.next;

            temp1.next = temp3.next;
            temp2.next = temp1;
            temp3.next = temp2;
            list.front = temp3;
        }
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list != null && list.front != null) {
            ListNode temp = list.front;
            ListNode current = list.front;
            while (current.next != null) {
                current = current.next;
            }
            current.next = temp;
            list.front = temp.next;
            temp.next = null;
        }
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        ListNode result = new ListNode(0);
        ListNode tail = result;
        ListNode currentA = a.front;
        ListNode currentB = b.front;

        while (currentA != null) {
            tail.next = new ListNode(currentA.data);
            tail = tail.next;
            currentA = currentA.next;
        }

        while (currentB != null) {
            tail.next = new ListNode(currentB.data);
            tail = tail.next;
            currentB = currentB.next;
        }
        return new LinkedIntList(result.next);
    }
}
