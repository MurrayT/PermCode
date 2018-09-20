package complib.classes;

import complib.Composition;
import complib.property.Universal;


import java.util.*;
import java.util.function.Predicate;

public class CompositionClass implements CompositionClassInterface {

    private Map<Integer,Set<Composition>> storedCompositions;
    private Set<Predicate<Composition>> properties;



    public CompositionClass(){
        storedCompositions = new TreeMap<>();
        properties = new HashSet<>();
        calculateStoredCompositions();
    }


    public CompositionClass(Collection<Predicate<Composition>> preds){
        storedCompositions = new TreeMap<>();
        properties = new HashSet<>();
        properties.addAll(preds);
    }

    private void calculateStoredCompositions() {
        // Initialise at size 0
        storedCompositions.put(0, new LinkedHashSet<>());
        storedCompositions.get(0).add(new Composition());

        for (int i = 1; i <= MAXIMUM_STORED_LENGTH; i++){
            storedCompositions.put(i, new LinkedHashSet<>());
            for (int j = 1; j<= i; j++){
                for (Composition pref: storedCompositions.get(i-j)){
                    Composition next = pref.concat(j);
                    if (containsComposition(next)){
                        storedCompositions.get(i).add(next);
                    }
                }
            }

        }

    }

    /**
     * Determine if the class contains a composition.
     *
     * @param comp the composition
     * @return <code>true</code> if the class contains the composition
     */
    @Override
    public boolean containsComposition(Composition comp) {
        for (Predicate<Composition> prop : properties) {
            if (!prop.test(comp)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<Composition> getIterator(int low, int high) {
        return getRestrictedIterator(low, high, new Universal());
    }

    @Override
    public Iterator<Composition> getRestrictedIterator(int low, int high, Predicate<Composition> prop) {
        return new CompositionClassIterator(low, high, prop);
    }

    public Iterator<Composition> getIterator(int n) {
        return getIterator(n, n);
    }

    public Iterator<Composition> getRestrictedIterator(int n, Predicate<Composition> prop) {
        return getRestrictedIterator(n, n, prop);
    }

    private class CompositionClassIterator implements Iterator<Composition> {

        Composition next = null;
        int low, high;
        Predicate<Composition> restrictingProperty;
        Queue<Iterator<Composition>> iterators = new ArrayDeque<>();
        Iterator<Composition> currentIterator;

        public CompositionClassIterator(int low, int high, Predicate<Composition> prop) {
            this.low = low;
            this.high = high;
            this.restrictingProperty = prop;
            for (int i = low; i <= high && i <= MAXIMUM_STORED_LENGTH; i++) {
                iterators.add(storedCompositions.get(i).iterator());
            }
            if (high > MAXIMUM_STORED_LENGTH) {
                iterators.add(new StackIterator());
            }
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
            do {
                if (next != null) {
                    return true;
                }
                if (currentIterator == null) {
                    if (iterators.isEmpty()) {
                        return false;
                    }
                    currentIterator = iterators.poll();
                }
                while (currentIterator.hasNext()) {
                    next = currentIterator.next();
                    if ((currentIterator instanceof StackIterator) || restrictingProperty.test(next)) {
                        return true;
                    }
                }
                currentIterator = null;
            } while (true);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Composition next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No elements remaining in permutation class");
            }
            Composition result = next;
            next = null;
            return result;
        }

        private class StackIterator implements Iterator<Composition>{

            Deque<Composition> stack;
            Composition stackNext = null;

            public StackIterator() {
                stack = new ArrayDeque<>();
                for (Composition p : storedCompositions.get(MAXIMUM_STORED_LENGTH)) {
                    stack.add(new Composition(p));
                }
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
            public Composition next() {
                return null;
            }
        }
    }
}
