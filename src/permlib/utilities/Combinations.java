package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * A class that provides an iterator for combinations of
 * <code>k</code> elements from
 * <code>[0...(n-1)]</code>.
 *
 * @author M Albert, M Belton
 */
public class Combinations implements Iterable<int[]> {

    public static int[] complement(int n, int[] c) {
       int[] result = new int[n - c.length];
       int cIndex = 0;
       int rIndex = 0;
       for(int i = 0; i < n; i++) {
           if (cIndex < c.length && c[cIndex] == i) {
               cIndex++;
           } else {
               result[rIndex++] = i;
           }
       }
       return result;
    }

    public static int[] random(int n, int k, Random R) {
        int[] result = new int[k];
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = i;
        }
        for (int i = 0; i < k; i++) {
            int j = i + R.nextInt(n - i);
            int t = c[i];
            c[i] = c[j];
            c[j] = t;
            result[i] = c[i];
        }
        Arrays.sort(result);
        return result;
    }

    public static int[] random(int n, int k) {
        return random(n, k, new Random());
    }

    private int n;
    private int k;

    /**
     * A constructor that creates a set of combinations by specifying the length
     * and upper bound.
     *
     * @param n The upper bound of the combinations (exclusive).
     * @param k The length of the combinations.
     */
    public Combinations(int n, int k) {
        this.n = n;
        this.k = k;
    }

    /**
     * Defines an iterator as an anonymous class that iterates through all
     * possible combinations in the created set.
     *
     * @return an iterator of type combination.
     */
    @Override
    public Iterator<int[]> iterator() {
        return new CombinationsIterator(n,k);
    }

    public static void main(String[] args) {
        Combinations cs = new Combinations(6, 3);
        for (int[] c : cs) {
            System.out.println(Arrays.toString(c));
        }
    }

    /**
     * Updates
     * <code>c</code> to be the next combination of the same length on [0,n).
     *
     * @param c the combination to update
     * @param n the upper bound (exclusive) for the combination
     * @return
     * <code>true</code> if
     * <code>c</code> was not the lex-last combination.
     *
     */
    public static boolean nextCombination(int[] c, int n) {

        int m = n - 1;
        int index = c.length - 1;
        while (index >= 0 && c[index] == m) {
            m--;
            index--;
        }

        if (index < 0) {
            return false;
        }

        c[index]++;
        for (int i = index + 1; i < c.length; i++) {
            c[i] = c[i - 1] + 1;
        }

        return true;
    }

    /**
     * Returns the first combination of the given length. That is, the numbers
     * <code>0</code> through
     * <code>length-1</code>.
     *
     * @param length the length
     * @return the integer array
     * <code>{0, 1, ..., length-1}</code>
     */
    public static int[] firstCombination(int length) {
        int[] result = new int[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }
} // End of Combinations class.