package problems;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String string: list) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1);
            }
            else {
                map.put(string, 1);
            }
            if (map.get(string) == 3) {
                return true;
            }
        }
        return false;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> map = new HashMap<>();
        for (String key : m1.keySet()) {
            if (m2.containsKey(key)) {
                if (m1.get(key).equals(m2.get(key))) {
                    map.put(key, m1.get(key));
                }
            }
        }
        return map;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }
}
