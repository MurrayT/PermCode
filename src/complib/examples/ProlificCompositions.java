package complib.examples;


import complib.Composition;
import complib.Compositions;
import utilities.CombinationsIncluding;

import java.util.*;
import java.util.stream.Collectors;

public class ProlificCompositions {

    private static boolean isInitiallyPersistent(Composition p) {
        int[] elements = p.getElements();
        int length = p.length;
        if (elements[0] > 1 || elements[length - 1] > 1) {
            return false; // This is true persistence so is not interesting
        }
        int last = 0;
        for (int e : elements) {
            if (e > 1 && last > 1 && e == last) {
                return true;            }
            last = e;
        }
        return false;
    }

    static Composition condense(Composition me){
//        System.err.println(me);
        int i,j;
        for (i=0; i < me.length && me.getElements()[i]==1;i++); // Finds first non-one
        for (j=me.length-1; j >= 0  && me.getElements()[j]==1;j--); //Finds last non-one
        if (i==0) i=1;
        if (j==me.length-1) j=me.length-2;
        if (i>j){ i=1; j=-1;}
        return new Composition(Arrays.copyOfRange(me.getElements(), i-1, j+2));
    }


    static boolean isProlific(Composition text, Composition pattern) {
        List<Integer> textList = Arrays.stream(text.getElements()).boxed().collect(Collectors.toList());
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

    public static void main(String[] args) {
        Set<Composition> seen = new HashSet<>();

        for (int pattlen = 1; pattlen < 15; pattlen++) {
            for (Composition pattern : new Compositions(pattlen, x-> x.getElements()[0] == 1)) {
//                System.err.println(pattern);
                Composition condensed = condense(pattern);
                if (!seen.contains(condensed)) {
                    seen.add(condensed);
                    seen.add(Composition.reverse(condensed));

                    if (isInitiallyPersistent(pattern) && !isSingleBlocked(pattern)) {
                        Set<Composition> minimals = new HashSet<>();
                        boolean flag = false;
                        int length = pattlen;
                        while (length < 25 && !flag) {
                            for (Composition comp : new Compositions(length)) {
                                if (Composition.canCover(pattern, comp)) {
                                    if (isProlific(comp, pattern)) {
                                        minimals.add(comp);
//                                        System.out.printf("%9s,%15s\n", pattern, comp);
                                        flag = true;
                                    }
                                }
                            }
                            length++;
                        }
//                        if (minimals.size() > 1) {
                            System.out.printf("%9s", pattern);
                            for (Composition min :
                                    minimals) {
                                System.out.printf(",%15s", min);
                            }
                            System.out.println();
                        }
//                    }
                }
            }
        }
    }

    private static boolean isSingleBlocked(Composition pattern) {
        boolean seenStart = false;
        boolean maybeEnd = false;
        int[] elements = pattern.getElements();
        for (int element : elements) {
            if (!seenStart && element > 1) {
                seenStart = true;
            }
            if (seenStart && element == 1) {
                maybeEnd = true;
            }
            if (maybeEnd && element > 1) {
                return false;
            }
        }
        return true;
    }
}
