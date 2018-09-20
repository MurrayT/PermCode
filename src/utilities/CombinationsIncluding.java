package utilities;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    static class CombinationsIncludingIterator extends CombinationsIterator {
        private final int sn;
        private int s;
        private List<Integer> indices;
        private List<Integer> others;

        CombinationsIncludingIterator(int n, int k, Integer[] indices) {
            super(n-indices.length,k-indices.length);
            this.indices = Arrays.asList(indices);
            others = IntStream.range(0,n).filter(x->!this.indices.contains(x)).boxed().collect(Collectors.toList());
            this.s = k;
            this.sn = n;
        }


        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return indices.stream().noneMatch(x->x>=sn) && s <= sn && (c == null || (k > 0 && c[0] < n - k));
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public int[] next() {
            if (c == null) {
                createFirstCombination();
            } else {
                update();
            }
            return selectValues();
        }

        private int[] selectValues() {
            List<Integer> values = new ArrayList<>();
            for (int i: c)
                values.add(others.get(i));
            values.addAll(indices);
            int[] comb = values.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(comb);
            return comb;
        }

        /**
         * An operation to remove a combination from the set. This is not
         * supported in this context.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported.");
        }

    }
}
