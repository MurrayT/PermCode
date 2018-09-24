package complib.classes;

import complib.Composition;
import complib.property.HereditaryProperty;
import complib.property.Universal;


import java.util.*;
import java.util.function.Predicate;

public class CompositionClass implements CompositionClassInterface {

    private Set<Predicate<Composition>> properties;



    public CompositionClass(){
        properties = new HashSet<>();
    }


    public CompositionClass(Collection<Predicate<Composition>> preds){
        properties = new HashSet<>();
        properties.addAll(preds);
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

        PriorityQueue<Composition> queue;
        Composition queueNext = null;
        int high, low;
        Predicate<Composition> property;

        public CompositionClassIterator(int low, int high, Predicate<Composition> prop) {
            queue = new PriorityQueue<>();
            this.high = high;
            this.low = low;
            this.property = prop;
            queue.add(new Composition());
        }

        Collection<Composition> children(Composition comp){
            List<Composition> children = new ArrayList<>();
            for (var i = 1 ; i <= high-comp.value; i++) {
                var child = comp.concat(i);
                if (containsComposition(child) && (!(property instanceof HereditaryProperty) || property.test(child))){
                   children.add(child);
                }
            }
            return children;
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
            if (queueNext != null)
                return true;
            while (!queue.isEmpty()){
                Composition candidate = queue.poll();
                if (candidate.value < high) {
                    for (Composition q : children(candidate)) {
                        if (q.value <= high) {
                            queue.add(q);
                        }
                    }
                }
            }
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
