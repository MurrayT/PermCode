package permlib.utilities;

import java.util.*;

public class CombinationsIncluding extends Combinations {

    final private Iterator<int[]> iterator;

    /**
     * A constructor that creates a set of combinations by specifying the length
     * and upper bound.
     *
     * @param n The upper bound of the combinations (exclusive).
     * @param k The length of the combinations.
     * @param indices The indexes that must be included
     */
    public CombinationsIncluding(int n, int k, Integer... indices) {
        super(n, k);
        this.iterator = new CombinationsIncludingIterator(n,k,indices);
    }

    @Override
    public Iterator<int[]> iterator() {
        return iterator;
    }

    public static void main(String[] args) {
        Combinations cs = new CombinationsIncluding(8, 4, 2,4);
        for (int[] c : cs) {
            System.out.println(Arrays.toString(c));
        }
    }
}
