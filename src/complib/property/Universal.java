package complib.property;

import java.util.ArrayList;
import java.util.Collection;

import complib.Composition;
import permlib.Permutation;

/**
 * The universal property satisfied by all permutations.
 *
 * @author Michael Albert
 */
public class Universal<T> implements HereditaryProperty<T> {

    /**
     * This property is always true
     * @param p the permutation to be tested
     * @return <code>true</code>
     */
    @Override
    public boolean test(T p) {
        return true;
    }

    /**
     * This property is always true
     *
     * @param values the array to be tested
     * @return <code>true</code>
     */
    public boolean test(int[] values) {
        return true;
    }

    @Override
    public String toString() {
        return "Universal";
    }

    @Override
    public Collection<T> getBasis() {
        return new ArrayList<T>();
    }

    @Override
    public Collection<T> getBasisTo(int n) {
        return getBasis();
    }
}
