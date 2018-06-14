package permlib.utilities;

public class ImmutablePair<F,S> extends Pair<F,S> {

    private final F first;
    private final S second;

    /**
     * <p>Obtains an immutable pair of two objects inferring the generic types.</p>
     *
     * @param first the first element, may be null
     * @param second the second element, may be null
     * @param <F> the first element type
     * @param <S> the second element type
     * @return a pair formed from the two parameters, not null
     */
    public static <F,S> ImmutablePair<F,S> of(final F first, final S second){
        return new ImmutablePair<>(first, second);
    }

    /**
     * Create a new pair instance
     *
     * @param first the left value, may be null
     * @param second the right value, may be null
     */
    public ImmutablePair(F first, S second) {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public F getFirst() {
        return first;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S getSecond() {
        return second;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<S, F> reverse() {
        return new ImmutablePair<>(second, first);
    }
}
