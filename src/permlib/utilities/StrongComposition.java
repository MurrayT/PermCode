package permlib.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrongComposition {
    private final int value;
    private final int[] elements;
    private final int length;

    public StrongComposition(int... n) {
        elements = n;
        value = IntStream.of(elements).sum();
        length = elements.length;
    }

    public StrongComposition(Integer[] n) {
        elements = Arrays.stream(n).mapToInt(i->i).toArray();
        value = IntStream.of(elements).sum();
        length = elements.length;
    }


    public StrongComposition() {
        elements = new int[0];
        value = 0;
        length = 0;
    }

    /**
     * Constructor that creates a permutation from a string. The string may be
     * white space separated in which case each token is treated as the value of
     * an element, or not, in which case each token is treated as a digit. In
     * the latter case you can use any characters you like in the input string.
     * If they are normal digits they will be ordered normally, but in general
     * the character ordering will be used. Ignoring punctuation symbols, this
     * is 0-9, upper case letters, lower case letters.
     *
     * @param input the string representing this permutation
     */
    public StrongComposition(String input) {
        input = input.trim();
        if (input.equals("")) {
            elements = new int[0];
            value = 0;
            length = 0;
            return;
        }
        int[] values;
        String[] stringValues = input.split(" ");
        if (stringValues.length == 1) {
            values = new int[input.length()];
            for (int i = 0; i < values.length; i++) {
                values[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        } else {
            values = new int[stringValues.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }
        this.elements = values;
        this.value = IntStream.of(elements).sum();
        this.length = elements.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof StrongComposition) && Arrays.equals(((StrongComposition) obj).elements, this.elements);
    }


    public static boolean contains(StrongComposition pattern, StrongComposition container){
        int patternLayerToMatch = 0;
        int containerLayerToLook = 0;
        while (containerLayerToLook <= container.length - pattern.length + patternLayerToMatch){
            if (container.elements[containerLayerToLook] >= pattern.elements[patternLayerToMatch]) {
                patternLayerToMatch++;
            }
            if (patternLayerToMatch == pattern.length){
                return true;
            }
            containerLayerToLook++;
        }
        return false;
    }

    public static List<int[]> occurrences(StrongComposition pattern, StrongComposition container){
        List<int[]> result = new ArrayList<>();

        for (int[] comb: new Combinations(container.length, pattern.length)){
            boolean match = true;
            for (int i=0; i<comb.length; i++) {
                if (!(match = container.elements[comb[i]] >= pattern.elements[i])) {
                    break;
                }
            }
            if (match){
                result.add(Arrays.copyOf(comb,comb.length));
            }
        }
        return result;
    }

    public static long occurrenceCount(StrongComposition pattern, StrongComposition container){
        long count = 0;
        for (int[] occ: occurrences(pattern, container)){
            long partial = 1;
            for (int i=0; i < occ.length; i++){
                partial *= Math.binomial(container.elements[occ[i]], pattern.elements[i]);
            }
            count += partial;
        }
        return count;
    }

    public static void main(String[] args) {
        StrongComposition sc1 = new StrongComposition(1,3,3,2,5,7);
        StrongComposition sc2 = new StrongComposition(new int[]{1,2,4,1});
//        System.out.println(occurrences(sc2, sc1).stream().map(Arrays::toString).collect(Collectors.toList()));

        StrongComposition sc3 = new StrongComposition(1,2,2,1);
        StrongComposition sc4 = new StrongComposition(1,4,3,2);
        System.out.println(occurrences(sc3, sc4).stream().map(Arrays::toString).collect(Collectors.toList()));
        System.out.println(occurrenceCount(sc3, sc4));

        StrongComposition pattern = new StrongComposition(1,2,1);
        StrongComposition text = new StrongComposition(4,2,1);

        System.out.println(StrongComposition.occurrenceCount(pattern,text));
    }
}
