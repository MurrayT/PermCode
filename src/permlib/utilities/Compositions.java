package permlib.utilities;


import java.util.Arrays;
import java.util.Iterator;

/**
 * A class that provides the compositions of an Integer {@code n}
 */
public class Compositions implements Iterable<int[]> {

    private final int n;

    public Compositions(int n) {
        this.n = n;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<int[]> iterator() {
        return new CompositionsIteratorBySwaps(n);
    }

    public static void main(String[] args) {
        Compositions x = new Compositions(5);
        int count = 0;
        for (int[] comp: x){
            System.out.println(Arrays.toString(comp));
            count++;
        }
        System.out.println(count);
    }

}
