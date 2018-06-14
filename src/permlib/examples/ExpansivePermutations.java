package permlib.examples;

import permlib.PermUtilities;
import permlib.Permutation;
import permlib.Permutations;
import permlib.classes.PermutationClass;
import permlib.utilities.*;

import java.util.*;
import java.util.stream.Collectors;

public class ExpansivePermutations {

    public static Iterable<Pair<Permutation, Integer>> onePointExtensions(Permutation p) {
        HashSet<Pair<Permutation,Integer>> result = new HashSet<>();
        for (int i = 0; i <= p.length(); i++) {
            for (int j = 0; j <= p.length(); j++) {
                result.add(Pair.of(PermUtilities.insert(p, i, j), j+1));
            }
        }
        return result;
    }

    static Set<Permutation> covers(Permutation p, PermutationClass c) {
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
        long t0 = System.currentTimeMillis();
        int high = 14;

        Permutation pattern = new Permutation("123");
        String[] basisArray = {"321"};
        Set<Permutation> basis = Arrays.stream(basisArray).map(Permutation::new).collect(Collectors.toCollection(HashSet::new));
        PermutationClass o = new PermutationClass(basis);

        Set<Permutation> nonexpansive = new HashSet<>();
        Map<Permutation, Integer> occurrenceMap = new HashMap<>();


        for (int permLength = 1; permLength <= high; permLength++) {
            PermutationClass c = new PermutationClass(basis);
            Set<Permutation> newBasis = new HashSet<>();
            for (Permutation q : new Permutations(c, permLength)) {
                int os;
                if (!(occurrenceMap.containsKey(q))) {
                    os = occurrenceCount(q, pattern);
                    occurrenceMap.put(q, os);
                } else {
                    os = occurrenceMap.get(q);
                }
                boolean expands = true;
                for (Permutation ext : covers(q, o)) {
                    int extOccs;
                    if (!(occurrenceMap.containsKey(ext))) {
                        extOccs = occurrenceCount(ext, pattern);
                        occurrenceMap.put(ext, extOccs);
                    } else {
                        extOccs = occurrenceMap.get(ext);
                    }
                    expands = (extOccs > os);
                    if(!expands) break;
                }
                if (!expands)
                    nonexpansive.add(q);
                else
                    newBasis.add(q);
            }
            basis.addAll(newBasis);
        }
        long t1 = System.currentTimeMillis();
        System.out.println("Elapsed Time: " + Long.toString(t1-t0));
        List<Integer> lengths = nonexpansive.stream().sorted().map(Permutation::length).collect(Collectors.toList());
        lengths.stream().distinct().forEach(l-> System.out.print(Integer.toString(Collections.frequency(lengths,l)) + ", "));
        System.out.println();
//        nonexpansive.stream().sorted().forEach(System.out::println);
        System.out.println("-----");
        basis.stream().sorted().forEach(System.out::println);

    }

}
