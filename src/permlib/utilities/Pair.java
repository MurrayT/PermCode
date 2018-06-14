package permlib.utilities;

import java.util.Objects;

/**
 * <p>A pair consisting of two elements.</p>
 *
 * <p>This class is an abstract implementation that defines the basic API.
 * It refers to the elements as 'first' and 'second'</p>
 *
 * <p>Subclasses may be mutable or immutable.
 * There is no restriction on the type of stored objects, so an immutable Pair containing mutable types is effectively
 * mutable</p>
 *
 * @param <F> the first element type
 * @param <S> the second element type
 *
 * @author Murray Tannock
 */
public abstract class Pair<F,S> implements Comparable<Pair<F,S>> {

    /**
     * <p>Obtains an immutable pair of two objects inferring the generic types.</p>
     *
     * @param first the first element, may be null
     * @param second the second element, may be null
     * @param <F> the first element type
     * @param <S> the second element type
     * @return a pair formed from the two parameters, not null
     */
    public static <F, S> Pair<F, S> of(final F first, final S second){
        return new ImmutablePair<>(first, second);
    }


    /**
     * <p>Gets the first element of the pair</p>
     *
     * @return the first element, may be null
     */
    public abstract F getFirst();

    /**
     * <p>Gets the second element of the pair</p>
     *
     * @return the second element, may be null
     */
    public abstract S getSecond();

    /**
     * <p>Compares the pair based on the first element and then the second element.
     * The types must be {@code Comparable}</p>
     *
     * @param other the other pair, not null
     * @return negative if this pair is less than other, zero if equal, positive if this pair is greater than other
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(final Pair<F, S> other){
        if (getFirst() instanceof Comparable){
            int firstCompare = ((Comparable<F>) getFirst()).compareTo(other.getFirst());
            if (firstCompare != 0)
                return firstCompare;
        }
        if (getSecond() instanceof Comparable){
            return ((Comparable<S>) getSecond()).compareTo(other.getSecond());
        }
        if ((!(getFirst() instanceof Comparable)) && (!(getSecond() instanceof Comparable))){
            throw new AssertionError("Neither element is comparable");
        }
        return 0;
    }

    /**
     * <p>Compares this pair to another based on the two elements</p>
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the pair are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj instanceof Pair){
            final Pair<?, ?> other = (Pair<?,?>) obj;
            return Objects.equals(getFirst(), other.getFirst()) &&
                    Objects.equals(getSecond(), other.getSecond());
        }
        return false;
    }

    /**
     * <p>Returns the hash code for this pair.
     * Following the definition in (@code Map.Entry}</p>
     *
     * @return the hash code value for this pair
     */
    @Override
    public int hashCode(){
        return (getFirst() == null ? 0: getFirst().hashCode()) ^
                (getSecond() == null ? 0 : getSecond().hashCode());
    }

    /**
     * <p>Returns a String representation of this pair. This implementation returns an open parenthesis followed by
     * the string representation of the first element followed by a comma character (",") followed by the string
     * representation of the second element followed by a closing parenthesis.</p>
     *
     * @return a String representation of theis pair
     */
    @Override
    public String toString(){
        return "(" + getFirst() + ',' + getSecond() +")";
    }

    /**
     * <p>Returns the pair given by reversing the order of the elements</p>
     * @return the pair given by reversing the order of the elements.
     */
    public abstract Pair<S,F> reverse();
}
