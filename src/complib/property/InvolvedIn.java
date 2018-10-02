package complib.property;

import complib.Composition;

import java.util.function.Predicate;

public class InvolvedIn implements Predicate<Composition> {

    private final Composition container;
    private final int[] containerElements;

    public InvolvedIn(Composition container) {
        this.container = container;
        this.containerElements = container.getElements();
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param pattern the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Composition pattern) {
        return Involves.test(containerElements, pattern.getElements());
    }

}
