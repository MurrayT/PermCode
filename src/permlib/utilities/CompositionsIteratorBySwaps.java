package permlib.utilities;

import java.util.*;

public class CompositionsIteratorBySwaps implements Iterator<int[]> {

    private final int n;
    private int[] comp;

    public CompositionsIteratorBySwaps(int n) {
        this.n = n;
        comp = new int[n];
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
        return comp[0] != n;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public int[] next() {
        //find the index of the first zero value in comp
        if (n==0){
            return new int[0];
        }
        int i,diff=n+1;
        for (i=0; i<comp.length && comp[i] != 0;i++);
        if (i==0){
            Arrays.fill(comp, 1);
        } else {
            if (i>1) {
                comp[i - 2]++; // Increment the second to last non-zero element
                diff = comp[--i]; // record the value of the last non-zero element so that can fill the right number of ones
                comp[i] = 0; // Throw away the last non-zero element;

                // Fill the array so our composition has the correct size
                if (diff > 1){
                    Arrays.fill(comp, i, i+diff-1, 1);
                }
            }
        }
        return Arrays.copyOfRange(comp, 0, i+diff-1);
    }

    public static void main(String[] args) {
        for (int l = 1; l<21; l++) {
            List<Long> times = new ArrayList<>();
            long startTime, endTime;
            for (int e = 0; e < 40; e++) {
                startTime = System.nanoTime();
                Iterator<int[]> c = new CompositionsIteratorBySwaps(l);
                while (c.hasNext()) {
                    c.next();
                }
                endTime = System.nanoTime();
                times.add(endTime - startTime);
            }
            long ttime = times.stream().reduce(Math::addExact).orElse(0L);
            long avgtime = ttime / times.size();
            System.out.println(avgtime);
        }
    }
}
