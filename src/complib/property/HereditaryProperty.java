package complib.property;

import java.util.Collection;
import java.util.function.Predicate;

import complib.Composition;
import permlib.Permutation;

/**
 * A hereditary property of combinatorial objects T. This extends the basic interface for
 * properties by methods for basis computation.
 *
 * It can also be used as a
 * marker that a property is known to be hereditary. This occurs typically in a
 * depth first search of Ts where child nodes are extensions of parent
 * nodes. If the parent node does not have the property then it should be
 * impossible for any descendant to have the property.
 *
 * @author Michael Albert
 */
public interface HereditaryProperty<T> extends Predicate<T> {


    /**
     * Computes the basis of the class of Ts associated with this
     * property. If this is impossible, it is permissible to throw an exception.
     *
     * @return the basis for this property (if known).
     */
    Collection<T> getBasis();

    /**
     * Returns the elements of the basis of the class of Ts associated
     * with this property up to a given length. This method should always
     * succeed. It is permissible, though not desirable to simply return the
     * entire basis if this is feasible, including some Ts which may
     * be longer than demanded.
     *
     * @param n the length
     * @return the Ts of length at most <code>n</code> that belong
     * to the basis of the class associated with this property.
     */
    Collection<T> getBasisTo(int n);
}
