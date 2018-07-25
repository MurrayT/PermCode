package permlib.utilities;


import java.util.Iterator;

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
        StrongCompositions x = new StrongCompositions(6);
        int count = 0;
        for (StrongComposition comp: x){
            System.out.println(comp);
            count++;
        }
        System.out.println(count);
    }

}
