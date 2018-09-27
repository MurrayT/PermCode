package complib.property;

import complib.Composition;

import java.util.Collection;

public class NonProlific implements HereditaryProperty<Composition> {

    /**
     * Computes the basis of the class of Ts associated with this
     * property. If this is impossible, it is permissible to throw an exception.
     *
     * @return the basis for this property (if known).
     */
    @Override
    public Collection<Composition> getBasis() {
        return null;
    }

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
    @Override
    public Collection<Composition> getBasisTo(int n) {
        return null;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param composition the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Composition composition) {
        return false;
    }
}
