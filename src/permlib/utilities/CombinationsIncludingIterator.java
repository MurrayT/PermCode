package permlib.utilities;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CombinationsIncludingIterator extends CombinationsIterator{
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
