package problems;

/**
 * See the spec on the website for example behavior.
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        if (array.length == 0) {
            return "[]";
        }
        String str = "[";
        str += array[0];
        for (int i = 1; i <= array.length - 1; i++) {
            str += (", " + array[i]);
        }
        str += "]";
        return str;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        if (array == null) {
            return null;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[array.length - 1 - i];
        }
        return result;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        if (array.length > 0) {
            int temp = array[array.length - 1];
            for (int i = 0; i < array.length - 1; i++) {
                array[array.length - 1 - i] = array[array.length - 2 - i];
            }
            array[0] = temp;
        }
        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}
