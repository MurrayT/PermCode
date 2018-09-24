package complib;

import utilities.Combinations;
import utilities.Math;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Composition implements Comparable<Composition>{
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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Composition o) {
        if (this.value != o.value){
            return Integer.compare(this.value, o.value);
        }
        return Arrays.compare(elements, o.elements);

    }
}
