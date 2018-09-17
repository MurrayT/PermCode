package permlib.utilities;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrongComposition {
    public final int value;
    private final int[] elements;
    public final int length;

    public StrongComposition(int... n) {
        elements = n;
        value = IntStream.of(elements).sum();
        length = elements.length;
    }

    public StrongComposition(Integer[] n) {
        elements = Arrays.stream(n).mapToInt(i -> i).toArray();
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

    public static boolean contains(StrongComposition pattern, StrongComposition container) {
        return contains(pattern.elements, container.elements);
    }

    public static boolean contains(int[] pattern, int[] container) {
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

    public static List<int[]> occurrences(StrongComposition pattern, StrongComposition container) {
        List<int[]> result = new ArrayList<>();

        for (int[] comb : new Combinations(container.length, pattern.length)) {
            boolean match = true;
            for (int i = 0; i < comb.length; i++) {
                if (!(match = container.elements[comb[i]] >= pattern.elements[i])) {
                    break;
                }
            }
            if (match) {
                result.add(Arrays.copyOf(comb, comb.length));
            }
        }
        return result;
    }

    public static long occurrenceCount(StrongComposition pattern, StrongComposition container) {
        long count = 0;
        for (int[] occ : occurrences(pattern, container)) {
            long partial = 1;
            for (int i = 0; i < occ.length; i++) {
                partial *= Math.binomial(container.elements[occ[i]], pattern.elements[i]);
            }
            count += partial;
        }
        return count;
    }

    public int[] getElements() {
        return Arrays.copyOf(elements, elements.length);
    }

    public static boolean canCover(StrongComposition pattern, StrongComposition text){
        return occurrences(pattern, text).stream().flatMapToInt(Arrays::stream).boxed().collect(Collectors.toSet()).size() == text.length;
    }

    public static boolean canCover(StrongComposition pattern, StrongComposition text, Boolean b) {
        Set<Integer> toMatch = IntStream.range(0, text.length).boxed().collect(Collectors.toSet());
//        System.err.println(toMatch);
        if (pattern.length > text.length)
            return false;

        for (int[] comb : new Combinations(text.length, pattern.length)) {
            boolean match = true;
            for (int i = 0; i < comb.length; i++) {
                if (!(match = text.getElements()[comb[i]] >= pattern.getElements()[i])) {
                    break;
                }
            }
            if (match) {
//                System.err.println(Arrays.toString(comb));
                toMatch.removeAll(Arrays.stream(comb).boxed().collect(Collectors.toSet()));
            }
            if (toMatch.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public Integer[] getBoxedElements(){
        return Arrays.stream(elements).boxed().toArray(Integer[]::new);
    }

    public static StrongComposition reverse(StrongComposition me){
        List<Integer> list = Arrays.asList(me.getBoxedElements());
        Collections.reverse(list);
        return new StrongComposition(list.toArray(new Integer[0]));
    }

    @Override
    public String toString() {
        String delim = "";
        int biggestPart = Collections.max(Arrays.asList(getBoxedElements()));
        if (biggestPart > 9){
            delim="_";
        }
        return Arrays.stream(elements).mapToObj(String::valueOf).collect(Collectors.joining(delim))+delim;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof StrongComposition) && Arrays.equals(((StrongComposition) obj).elements, this.elements);
    }
}
