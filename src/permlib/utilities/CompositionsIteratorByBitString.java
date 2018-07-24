package permlib.utilities;

import java.util.*;

public class CompositionsIteratorByBitString implements Iterator<int[]> {

    private final int n;
    private long k;
    private int[] comp;

    public CompositionsIteratorByBitString(int n) {
        this.n = n;
        k=0;
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
        comp = new int[n];
        int block = 0;
        for (int shift = 0; shift < n; shift++){
            comp[block]++;
            if ((k>>shift & 1) == 0){
                block++;
            }
        }
        k++;
        return Arrays.copyOfRange(comp, 0 , block);
    }

    public static void main(String[] args) {
        for (int l = 1; l<21; l++) {
            List<Long> times = new ArrayList<>();
            long startTime, endTime;
            for (int e = 0; e < 40; e++) {
                startTime = System.nanoTime();
                Iterator<int[]> c = new CompositionsIteratorByBitString(l);
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
