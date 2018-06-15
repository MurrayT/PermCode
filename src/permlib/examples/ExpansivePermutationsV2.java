package permlib.examples;

import permlib.PermUtilities;
import permlib.Permutation;
import permlib.Permutations;
import permlib.classes.PermutationClass;
import permlib.utilities.CombinationsIncluding;
import permlib.utilities.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ExpansivePermutationsV2 {

    static Iterable<Pair<Permutation, Integer>> indexedOnePointExtensions(Permutation p) {
        HashSet<Pair<Permutation,Integer>> result = new HashSet<>();
        for (int i = 0; i <= p.length(); i++) {
            for (int j = 0; j <= p.length(); j++) {
                result.add(Pair.of(PermUtilities.insert(p, i, j), i));
            }
        }
        return result;
    }

    static Set<Pair<Permutation, Integer>> indexedCovers(Permutation p, PermutationClass c) {
        Set<Pair<Permutation, Integer>> result = new HashSet<>();
        for (Pair<Permutation, Integer> q : indexedOnePointExtensions(p)) {
            if (c.containsPermutation(q.getFirst())) result.add(q);
        }
        return result;
    }

    static boolean occurrenceWithIndex(Pair<Permutation, Integer> pair, Permutation pattern){
        Permutation text = pair.getFirst();
        Integer index = pair.getSecond();
        for (int[] comb : new CombinationsIncluding(text.length(), pattern.length(), index)){
            if (text.patternAt(comb).equals(pattern))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        int high = 14;

        Permutation pattern = new Permutation("123");
        String[] basisArray = {"321"};
        Set<Permutation> basis = Arrays.stream(basisArray).map(Permutation::new).collect(Collectors.toCollection(HashSet::new));
        PermutationClass o = new PermutationClass(basis);

        Set<Permutation> nonexpansive = new HashSet<>();


        for (int permLength = 1; permLength <= high; permLength++) {
            PermutationClass c = new PermutationClass(basis);
            Set<Permutation> newBasis = new HashSet<>();
            for (Permutation q : new Permutations(c, permLength)) {
                boolean expands = true;
                for (Pair<Permutation, Integer> ext : indexedCovers(q, o)) {
                    expands = occurrenceWithIndex(ext, pattern);
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
        System.out.println("-----");
        basis.stream().sorted().forEach(System.out::println);

    }

}
