package complib.classes;

import complib.Composition;

import java.util.Iterator;
import java.util.function.Predicate;

public interface CompositionClassInterface {

    int MAXIMUM_STORED_LENGTH = 8;

    /**
     * Determine if the class contains a composition.
     *
     * @param d the composition
     * @return <code>true</code> if the class contains the composition
     *
     */
    boolean containsComposition(Composition d);

    /**
     * Provide an iterator for the compositions in the class lying in a range
     * of lengths.
     *
     * @param low the lower endpoint of the range
     * @param high the upper endpoint of the range
     * @return the iterator
     */
    Iterator<Composition> getIterator(int low, int high);

    /**
     * Provide an iterator for the compositions in the class lying in a range
     * of lengths, satisfying some additional property.
     *
     * @param low the lower endpoint of the range
     * @param high the upper endpoint of the range
     * @param prop the property
     * @return the iterator
     */
    Iterator<Composition> getRestrictedIterator(int low, int high,
                                                       Predicate<Composition> prop);
}
