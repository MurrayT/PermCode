package complib;

import utilities.Combinations;
import utilities.Math;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Composition {
    public final int value;
    private final int[] elements;
    public final int length;

    public Composition(int... n) {
        elements = n;
        value = IntStream.of(elements).sum();
        length = elements.length;
    }

    public Composition(Integer[] n) {
        elements = Arrays.stream(n).mapToInt(i -> i).toArray();
        value = IntStream.of(elements).sum();
        length = elements.length;
    }


    public Composition() {
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
    public Composition(String input) {
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

    public Composition(Composition p) {
        elements = Arrays.copyOf(p.elements,p.length);
        length = p.length;
        value = p.value;
    }

    public Composition concat(Composition other){
        int[] newElements = Arrays.copyOf(elements, this.length+other.length);
        for (int i = 0; i < other.length; i++){
            newElements[this.length+i] = other.elements[i];
        }
        return new Composition(newElements);
    }

    public Composition concat(int other){
        int[] newElements = Arrays.copyOf(elements, this.length+1);
        newElements[this.length] = other;
        return new Composition(newElements);
    }

    public static boolean contains(Composition pattern, Composition container) {
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

    public static List<int[]> occurrences(Composition pattern, Composition container) {
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

    public static long occurrenceCount(Composition pattern, Composition container) {
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

    public static boolean canCover(Composition pattern, Composition text){
        return occurrences(pattern, text).stream().flatMapToInt(Arrays::stream).boxed().collect(Collectors.toSet()).size() == text.length;
    }

    public static boolean canCover(Composition pattern, Composition text, Boolean b) {
        Set<Integer> toMatch = IntStream.range(0, text.length).boxed().collect(Collectors.toSet());
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

    public static Composition reverse(Composition me){
        List<Integer> list = Arrays.asList(me.getBoxedElements());
        Collections.reverse(list);
        return new Composition(list.toArray(new Integer[0]));
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
        return (obj instanceof Composition) && Arrays.equals(((Composition) obj).elements, this.elements);
    }
}
