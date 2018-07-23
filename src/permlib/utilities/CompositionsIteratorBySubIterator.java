package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositionsIteratorBySubIterator implements Iterator<int[]> {

    private final int n;
    private int k;

    private int[] composition;

    private CompositionsIteratorBySubIterator subIterator = null;

    CompositionsIteratorBySubIterator(int n) {
        this.n = n;
        k=0;
        composition = new int[n];
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
        if (n <= 0)
            return false;
        if (composition[0] == n)
            return subIterator.hasNext();
        return true;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public int[] next() {
        if (n==0)
            return new int[0];
        if (k == 0 || !subIterator.hasNext()){
            k+=1;
            subIterator = new CompositionsIteratorBySubIterator(n-k);
        }
        Arrays.fill(composition,0);
        composition[0] = k;
        int[] rest = subIterator.next();
        System.arraycopy(rest, 0, composition, 1, rest.length);
        int i;
        for (i=0; i<composition.length && composition[i]!=0; i++);
        return Arrays.copyOfRange(composition, 0, i);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Iterator<int[]> c = new CompositionsIteratorBySubIterator(5);
        while(c.hasNext()){
            System.out.println(Arrays.toString(c.next()));
        }
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime-startTime));
    }
}
