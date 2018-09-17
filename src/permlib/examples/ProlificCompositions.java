package permlib.examples;

import permlib.utilities.CombinationsIncluding;
import permlib.utilities.StrongComposition;
import permlib.utilities.StrongCompositions;

import java.util.*;
import java.util.stream.Collectors;

public class ProlificCompositions {

    private static boolean isInitiallyPersistent(StrongComposition p) {
        int[] elements = p.getElements();
        int length = p.length;
        if (elements[0] > 1 || elements[length - 1] > 1) {
            return false; // This is true persistence so is not interesting
        }
        int last = 0;
        for (int e : elements) {
            if (e > 1 && last > 1 && e == last) {
                return true;
            }
            last = e;
        }
        return false;
    }

    static StrongComposition condense(StrongComposition me){
        System.err.println(me);
        int i,j;
        for (i=0; i < me.length && me.getElements()[i]==1;i++); // Finds first non-one
        for (j=me.length-1; j >= 0  && me.getElements()[j]==1;j--); //Finds last non-one
        if (i==0)
        System.err.println(i + ", " + j);
        System.err.println("___");
        return new StrongComposition();
    }


    static boolean isProlific(StrongComposition text, StrongComposition pattern) {
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
//        condense(new StrongComposition(1,1,1,2,2,1,2,3,4,5,1,1));
        Set<StrongComposition> seen = new HashSet<>();
//        for (int pattlen = 1; pattlen < 13; pattlen++) {
//            for (StrongComposition pattern : new StrongCompositions(pattlen)) {
                StrongComposition pattern = new StrongComposition(1,2,2,1,1,1,2,2,1);
                int pattlen = pattern.value;
                StrongComposition condensed = pattern;
                if (!seen.contains(condensed)) {
                    seen.add(condensed);
                    seen.add(StrongComposition.reverse(condensed));

                    if (isInitiallyPersistent(pattern)) {
                        boolean flag = false;
                        int length = pattlen;
                        while (length < 20 && !flag) {
                            for (StrongComposition comp : new StrongCompositions(length)) {
                                if (StrongComposition.canCover(pattern, comp, true)) {
                                    if (isProlific(comp, pattern)) {
                                        System.out.printf("%9s,%15s\n", pattern, comp);
                                        flag = true;
                                    }
                                }
                            }
                            length++;
                        }
                    }
                }
            }
        }
//    }
//}
