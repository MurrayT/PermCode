package complib.classes;

import complib.Composition;
import complib.property.Universal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public class UniversalCompositionClass implements CompositionClassInterface, Iterable<Composition> {

    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;
    private int currentLength;
    private Iterator<Composition> currentLengthIterator;
    private Predicate<Composition> prop = new Universal();

    /**
     * The class of (effectively) all permutations.
     */
    public UniversalCompositionClass() {
        this(0, Integer.MAX_VALUE);
    }

    /**
     * The class of all permutations of length
     * <code>n</code>.
     *
     * @param n the length
     */
    public UniversalCompositionClass(int n) {
        this(n, n);
    }

    /**
     * The class of all permutations of lengths between
     * <code>minLength</code> and
     * <code>minLength</code>.
     *
     * @param minLength the minimum length
     * @param maxLength the maximum length
     *
     */
    public UniversalCompositionClass(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        setupIterator();
    }

    private void setupIterator() {
        this.currentLength = minLength;
        currentLengthIterator = new UniversalCompositionClass.CompositionsIterator(minLength).iterator();
    }

    /**
     * Determine if the class contains a composition.
     *
     * @param d the composition
     * @return <code>true</code> if the class contains the composition
     */
    @Override
    public boolean containsComposition(Composition d) {
        return true;
    }

    /**
     * Provide an iterator for the compositions in the class lying in a range
     * of lengths.
     *
     * @param low  the lower endpoint of the range
     * @param high the upper endpoint of the range
     * @return the iterator
     */
    @Override
    public Iterator<Composition> getIterator(int low, int high) {
        return getRestrictedIterator(low, high, new Universal());
    }

    /**
     * Provide an iterator for the compositions in the class lying in a range
     * of lengths, satisfying some additional property.
     *
     * @param low  the lower endpoint of the range
     * @param high the upper endpoint of the range
     * @param prop the property
     * @return the iterator
     */
    @Override
    public Iterator<Composition> getRestrictedIterator(int low, int high, Predicate<Composition> prop) {
        setupBoundedIterator(low, high);
        this.prop = prop;
        return iterator();
    }

    public void setupBoundedIterator(int low, int high) {
        from(low);
        to(high);
    }

    /**
     * Returns a class where the maximum length has been set (and the iterator
     * reinitialised).
     *
     * @param maxLength the maximum length
     * @return the class
     */
    public UniversalCompositionClass to(int maxLength) {
        this.maxLength = maxLength;
        setupIterator();
        return this;
    }

    /**
     * Returns a class where the minimum length has been set (and the iterator
     * reinitialised).
     *
     * @param minLength the minimum length
     * @return an iterator from permutations from the minimum length
     */
    public UniversalCompositionClass from(int minLength) {
        this.minLength = minLength;
        setupIterator();
        return this;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Composition> iterator() {
        return new Iterator<>() {
            boolean res;
            @Override
            public boolean hasNext() {

                while (res = !currentLengthIterator.hasNext()){
                    if (currentLength == maxLength) {
                        return false;
                    }
                    System.err.println("Incrementing length");

                    currentLength++;
                    currentLengthIterator = new UniversalCompositionClass.CompositionsIterator(currentLength).iterator();
                }
                return true;
//                if (currentLengthIterator.hasNext()) {
//                    return true;
//                }
//
//                return currentLengthIterator.hasNext();
            }

            @Override
            public Composition next() {
                return currentLengthIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove is not supported.");
            }
        };
    }


    public class CompositionsIterator implements Iterable<Composition> {

        private final int n;


        public CompositionsIterator(int n) {
            this.n = n;
        }


        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<Composition> iterator() {
            return new Iterator<>() {
                private int[] comp = new int[n];
                private int i = 0;
                private boolean done;

                @Override
                public boolean hasNext() {
                    if (n==0){
                        if (!done){
                            done = true;
                            if (prop.test(new Composition(comp))){
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        while (comp[0] != n){
                            makeNext();
                            System.err.println(Arrays.toString(comp));
                            if (prop.test(new Composition(comp))){
                                return true;
                            }
                        }

                    }
                    return false;
                }

                private void makeNext(){
                    if (i == 0) {
                        Arrays.fill(comp, 1);
                        i = n;
                    } else {
                        comp[i - 2]++; // Increment the second to last non-zero element
                        int diff = comp[--i]; // record the value of the last non-zero element so that can fill the right number of ones
                        comp[i] = 0; // Throw away the last non-zero element;

                        // Fill the array so our composition has the correct size
                        if (diff > 1) {
                            Arrays.fill(comp, i, i + diff - 1, 1);
                            i = i + diff - 1;
                        }
                    }
                }

                @Override
                public Composition next() {
                    //find the index of the first zero value in comp
                    if (n == 0) {
                        done = true;
                        return new Composition();
                    }

                    return new Composition(Arrays.copyOfRange(comp, 0, i));
                }
            };
        }
    }
}
