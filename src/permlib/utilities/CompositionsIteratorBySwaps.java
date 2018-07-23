package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
        int i,sum=-1;
        for (i=0; i<comp.length && comp[i] != 0;i++);
        if (i==0){
            Arrays.fill(comp, 1);
        } else {
            if (i>1) {
                comp[i - 2]++; // Increment the second to last non-zero element
                comp[i-1] = 0;  // Throw away the last non-zero element;

                // Calculate the sum of the new non-zero parts
                sum = 0;
                for (int j=0; j<i; j++){
                    sum += comp[j];
                }
                // Fill the array so our composition has the correct size
                if (sum < n){
                    Arrays.fill(comp, i-1, i-1+n-sum, 1);
                }
            }

        }


        return Arrays.copyOfRange(comp, 0, i-1+n-sum);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Iterator<int[]> c = new CompositionsIteratorBySwaps(9);
        while(c.hasNext()){
            System.out.println(Arrays.toString(c.next()));
        }
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime-startTime));
    }
}
