package permlib.examples;

import permlib.PermUtilities;
import permlib.Permutation;
import permlib.Permutations;
import permlib.classes.PermutationClass;
import permlib.utilities.*;

import java.util.*;
import java.util.stream.Collectors;

public class ExpansivePermutations {

    static Set<Permutation> onePointExtensions(Permutation p, PermutationClass c) {
        Set<Permutation> result = new HashSet<>();
        for (Permutation q : PermUtilities.onePointExtensions(p)) {
            if (c.containsPermutation(q)) result.add(q);
        }
        return result;
    }

    static int occurrenceCount(Permutation text, Permutation pattern){
        int result = 0;
        for (int[] comb : new Combinations(text.length(), pattern.length())){
            if (text.patternAt(comb).equals(pattern))
                result += 1;
        }
        return result;
    }

    public static void main(String[] args) {

        int high = 10;

        Permutation pattern = new Permutation("123");
        PermutationClass c = new PermutationClass("321");

        Set<Permutation> nonexpansive = new HashSet<>();
        Set<Permutation> expansive = new HashSet<>();
        Map<Permutation, Integer> occurrenceMap = new HashMap<>();


        for (Permutation q: new Permutations(c,0,high)){
            int os;
            if (!(occurrenceMap.containsKey(q))){
                os = occurrenceCount(q, pattern);
                occurrenceMap.put(q, os);
            } else {
                os = occurrenceMap.get(q);
            }
            List<Boolean> expands = new ArrayList<>();
            for (Permutation ext : onePointExtensions(q,c)){
                int extOccs = occurrenceCount(ext, pattern);
                if (!(occurrenceMap.containsKey(q))){
                    occurrenceMap.put(ext, extOccs);
                }
                expands.add(extOccs > os);
            }
            if (expands.contains(false))
                nonexpansive.add(q);
            else
                expansive.add(q);
        }
        List<Integer> lengths = nonexpansive.stream().sorted().map(Permutation::length).collect(Collectors.toList());
        lengths.stream().distinct().forEach(l-> System.out.print(Integer.toString(Collections.frequency(lengths,l)) + ", "));
        System.out.println();
        nonexpansive.stream().sorted().forEach(System.out::println);
        System.out.println("-----");
        expansive.stream().sorted().forEach(System.out::println);
    }

}
