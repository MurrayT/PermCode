package complib.property;

import complib.Composition;

import java.util.function.Predicate;

public class Involves implements Predicate<Composition> {

    private final Composition pattern;
    private final int[] patternElements;

    public Involves(Composition pattern){
        this.pattern = pattern;
        this.patternElements = pattern.getElements();
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
        return test(composition.getElements(), patternElements);
    }

    public static boolean test(int[] container, int[] pattern) {
        int patternLayerToMatch = 0;
        int containerLayerToLook = 0;
        while (containerLayerToLook <= container.length - pattern.length + patternLayerToMatch) {
            if (container[containerLayerToLook] >= pattern[patternLayerToMatch]) {
                patternLayerToMatch++;
            }
            if (patternLayerToMatch == pattern.length) {
                return true;
            }
            containerLayerToLook++;
        }
        return false;
    }
}
