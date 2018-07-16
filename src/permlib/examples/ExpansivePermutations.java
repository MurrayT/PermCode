package permlib.examples;

import permlib.PermUtilities;
import permlib.Permutation;
import permlib.Permutations;
import permlib.classes.PermutationClass;
import permlib.utilities.Combinations;
import permlib.utilities.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ExpansivePermutations {

    static Set<Permutation> covers(Permutation p, PermutationClass c) {
        Set<Permutation> result = new HashSet<>();
        for (Permutation q : PermUtilities.onePointExtensions(p)) {
            if (c.containsPermutation(q)) result.add(q);
        }
        return result;
    }

    static int occurrenceCount(Permutation text, Permutation pattern) {
        int result = 0;
        for (int[] comb : new Combinations(text.length(), pattern.length())) {
            if (text.patternAt(comb).equals(pattern))
                result += 1;
        }
        return result;
    }

    static int occurrenceCount(Permutation text, Set<Permutation> patterns) {
        int result = 0;
        for (Permutation pattern : patterns) {
            for (int[] comb : new Combinations(text.length(), pattern.length())) {
                if (text.patternAt(comb).equals(pattern))
                    result += 1;
            }
        }
        return result;
    }

    static Pair<Set<Permutation>, Set<Permutation>> generateExample(int basisSize, int basisMinLen, int basisMaxLen, int expandingSize, int expandingMinLen, int expandingMaxLen) {
        System.err.println("Generating example");
        Set<Permutation> basis = new HashSet<>();
        Random r = new Random();
        PermutationClass t;
        while (basis.size() < basisSize) {
            t = new PermutationClass(basis);
            Permutation p;
            int elementLength = r.ints(basisMinLen, basisMaxLen + 1).findFirst().orElse(-1);
            if (elementLength > 0) {
                p = PermUtilities.randomPermutation(elementLength);
                if (t.containsPermutation(p)) {
                    basis.add(p);
                }
            }
        }
        t = new PermutationClass(basis);
        System.err.println("Basis: " + basis);
        boolean oneplusclosed = true;
        boolean oneminusclosed = true;
        boolean plusoneclosed = true;
        boolean minusoneclosed = true;
        for (Permutation p : basis) {
            int plen = p.length();
            if (p.at(0) == 0)
                oneplusclosed = false;
            if (p.at(0) == plen-1)
                oneminusclosed = false;
            if (p.at(plen - 1) == plen-1)
                plusoneclosed = false;
            if (p.at(plen - 1) == 0)
                minusoneclosed = false;
        }



        Set<Permutation> expandingSet;
        do {
            expandingSet = new HashSet<>();
            while (expandingSet.size() < expandingSize) {
                Permutation p;
                int elementLength = r.ints(expandingMinLen, expandingMaxLen + 1).findFirst().orElse(-1);
                if (elementLength > 0) {
                    List<Permutation> lenperms = new ArrayList<>(t.getPerms(elementLength));
                    Collections.shuffle(lenperms);
                    p = lenperms.get(0);
                    expandingSet.add(p);
                    int plen = p.length();
                    if (p.at(0) == 0)
                        oneplusclosed = false;
                    if (p.at(0) == plen-1)
                        oneminusclosed = false;
                    if (p.at(plen - 1) == plen-1)
                        plusoneclosed = false;
                    if (p.at(plen - 1) == 0)
                        minusoneclosed = false;
                }
            }
        } while (oneplusclosed || oneminusclosed || minusoneclosed || plusoneclosed);
        System.err.println("Expanding set: " + expandingSet);
        return Pair.of(basis, expandingSet);
    }

    public static void main(String[] args) {
        // TODO: build table for 1+alpha+1, 1+alpha/alpha+1 etc.
        long t0 = System.currentTimeMillis();
        int high = 14;
        Set<Permutation> ogbasis;
        Set<Permutation> basis;
        Set<Permutation> patterns;

//        Pair<Set<Permutation>, Set<Permutation>> example = generateExample(4, 3,6, 3, 4, 5);
//        ogbasis = example.getFirst();

//        String[] basisArray = {"231"};
//        Set<Permutation> basis = Arrays.stream(basisArray).map(Permutation::new).collect(Collectors.toCollection(HashSet::new));

        ogbasis = Set.of(new Permutation("231"), new Permutation("312"));
        PermutationClass o = new PermutationClass(ogbasis);

//         patterns = example.getSecond();
//        Permutation pattern = new Permutation("123");


        for (int pattlen = 0; pattlen < 7; ++pattlen) {
            for (Permutation pattern : o.getPerms(pattlen)) {
                patterns = new HashSet<>(List.of(pattern.insert(pattlen, pattlen).insert(0, 0)));

                basis = new HashSet<>(ogbasis);


//        System.out.println(patterns);
//        System.out.println("(" + String.join(",", patterns) + ")-expansive in Av(" + String.join(",",basisArray) +")");

                Set<Permutation> nonexpansive = new HashSet<>();
                Map<Permutation, Integer> occurrenceMap = new HashMap<>();


                for (int permLength = 1; permLength <= high; permLength++) {
                    System.err.println("Permutations of Length " + permLength);
                    PermutationClass c = new PermutationClass(basis);
                    Set<Permutation> newBasis = new HashSet<>();
                    for (Permutation q : new Permutations(c, permLength)) {
                        int os;
                        if (!(occurrenceMap.containsKey(q))) {
                            os = occurrenceCount(q, patterns);
                            occurrenceMap.put(q, os);
                        } else {
                            os = occurrenceMap.get(q);
                        }
                        boolean expands = true;
                        for (Permutation ext : covers(q, o)) {
                            int extOccs;
                            if (!(occurrenceMap.containsKey(ext))) {
                                extOccs = occurrenceCount(ext, patterns);
                                occurrenceMap.put(ext, extOccs);
                            } else {
                                extOccs = occurrenceMap.get(ext);
                            }
                            expands = (extOccs > os);
                            if (!expands) break;
                        }
                        if (!expands)
                            nonexpansive.add(q);
                        else {
                            System.err.println(q);
                            newBasis.add(q);
                        }

                    }
                    basis.addAll(newBasis);
                }
                System.err.println();
                long t1 = System.currentTimeMillis();
                System.out.println("==========");
                System.out.println("π = " + pattern);
                System.out.println(patterns + "-expansive in Av" + ogbasis);
                System.out.println("Elapsed Time: " + Long.toString(t1 - t0));
                List<Integer> lengths = nonexpansive.stream().sorted().map(Permutation::length).collect(Collectors.toList());
                lengths.stream().distinct().forEach(l -> System.out.print(Integer.toString(Collections.frequency(lengths, l)) + ", "));
                System.out.println();
                System.out.println("-----");
                System.out.println(basis.size() + " basis elements");
                basis.stream().sorted().forEach(System.out::println);
            }
        }
    }
}
