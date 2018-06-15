package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CombinationsIncluding extends Combinations {

    private class CombinationsIncludingIterator implements Iterator<int[]> {
        private int n;
        private int k;
        private int[] indexCombination = null;
        private List<Integer> indices;
        private List<Integer> others;
        int iSize;

        CombinationsIncludingIterator(int n, int k, Integer[] indices) {
            this.indices = Arrays.asList(indices);
            others = IntStream.range(0,n).filter(x->this.indices.contains(x)).boxed().collect(Collectors.toList());
            this.iSize = indices.length;
            this.n = n;
            this.k = k;
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
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public int[] next() {
            int[] c = null;
            if (indexCombination == null) {
                c = createFirstCombination();
            } else {
                c = update();
            }
            return c;
        }

        private int[] createFirstCombination() {
            indexCombination = new int[k];
            for (int i = 0; i < k-iSize; i++) {
                indexCombination[i] = i;
            }
            
            }
        }

        private int[] update() {

            return new int[0];
        }
    }

    final private Integer[] indices;
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
        this.indices = indices;
        this.iterator = new CombinationsIncludingIterator(n,k,indices);
    }

    @Override
    public Iterator<int[]> iterator() {
        return iterator;
    }
}
