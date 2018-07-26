package permlib.utilities;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A class that provides the compositions of an Integer {@code n}
 */
public class StrongCompositions implements Iterable<StrongComposition> {

    private final int n;

    public StrongCompositions(int n) {
        this.n = n;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<StrongComposition> iterator() {
        return new CompositionsIterator(n);
    }

    public static void main(String[] args) {
        StrongComposition pattern = new StrongComposition(1,2,2);
        long t1 = System.nanoTime();
        for (int length=1; length <= Long.valueOf(args[0]); length++){
            System.err.println(length);
            Map<Long, Integer> map = new HashMap<>();
            for (StrongComposition sc : new StrongCompositions(length)){
                long occCount = StrongComposition.occurrenceCount(pattern,sc);
                map.put(occCount, map.getOrDefault(occCount, 0)+1);
            }
            for (long j=0; j<=map.keySet().stream().max(Long::compare).orElse(0L);j++){
                System.out.printf("%04d, ", map.getOrDefault(j,0));
            }
            System.out.println();
        }
        long t2 = System.nanoTime();
        System.err.println("Execution time: "+ (t2-t1));
    }

}
