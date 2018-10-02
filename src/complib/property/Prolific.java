package complib.property;

import complib.Composition;
import utilities.CombinationsIncluding;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Prolific implements Predicate<Composition> {

    Composition pattern;

    public Prolific(Composition pattern){
        this.pattern = pattern;
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
        List<Integer> textList = Arrays.stream(composition.getElements()).boxed().collect(Collectors.toList());
        for (int gap = 0; gap <= textList.size(); gap++) {
            List<Integer> textListCopy = new ArrayList<>(textList);
            textListCopy.add(gap, 1); // For each gap we try to put in a singleton layer
            if ((gap > 0 && textListCopy.get(gap - 1) > 1) || // If the layer before is of size one we don't need to check
                    (gap < textList.size() && textListCopy.get(gap + 1) > 1)) { // If the layer after is size one we don't need to check
                boolean match = true;
                for (int[] comb : new CombinationsIncluding(textListCopy.size(), pattern.length, gap)) { //Go through the combinations of layers including the gap layer
                    match = true;
                    for (int i = 0; i < comb.length; i++) {
                        if (!(match = textListCopy.get(comb[i]) >= pattern.getElements()[i])) {     // If we fail on a layer for a combination we don't check that comb any further
                            break;
                        }
                    }
                    if (match) {
                        break;  // If we match we don't need to check any more combinations with this gap
                    }
                }
                if (!match) {
                    return false; // If we fail on a gap we are done.
                }
            }
        }
        return true;
    }

}
