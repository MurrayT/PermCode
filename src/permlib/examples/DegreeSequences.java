/*
 * What can we say about ordered degree sequences for permutations?
 */
package permlib.examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import permlib.PermUtilities;
import permlib.Permutation;
import permlib.Permutations;
import permlib.classes.PermClassInterface;
import permlib.classes.PermutationClass;
import permlib.classes.UniversalPermClass;
import permlib.processor.PermCounter;

/**
 *
 * @author Michael Albert
 */
public class DegreeSequences {
    
    static final Permutation[] BAD = new Permutation[] {
        new Permutation("43512"),
        new Permutation("21534"),
        new Permutation("23154"),
        new Permutation("45132")
    };

    public static void main(String[] args) {
        // findLots(5,2);
        badClassSUD();
    }
    
    public static void badClassSUD() {
        
        PermutationClass c = new PermutationClass(BAD);
        for(int n = 6; n <= 12; n++) {
            System.out.println("Length " + n);
            for(Permutation p : new Permutations(c, n)) {
                int[] ds = degreeSequenceArray(p);
                ArrayList<Permutation> ps = permsFromDegreeSequence(ds);
                if (ps.size() > 1) {
                   System.out.print(p);
                   for(Permutation q : ps) {
                        if (!q.equals(p)) System.out.print(", " + q);
                   }
                   System.out.println();
                }
            }
        
        }
        
        
    }

    public static void countDegreeSequences(int k) {
        for (int n = 1; n <= k; n++) {
            HashSet<ArrayList<Integer>> ds = new HashSet<>();
            for (Permutation p : new Permutations(n)) {
                ArrayList<Integer> pd = degreeSequence(p);
                if (ds.contains(pd)) {
                    System.out.println(p + " " + pd);
                }
                ds.add(pd);
            }
            // System.out.print(ds.size() + ", ");
        }
    }

    public static void maxDegreeThree() {
        HashSet<Permutation> basis = new HashSet<>();
        for (Permutation p : new Permutations(5)) {
            ArrayList<Integer> dp = degreeSequence(p);
            if (dp.contains(4)) {
                // System.out.println(p);
                basis.add(p);
            }
        }
        PermutationClass c = new PermutationClass(basis);
        for (int n = 1; n <= 10; n++) {
            PermCounter counter = new PermCounter();
            c.processPerms(n, counter);
            System.out.print(counter.getCount() + ", ");
        }
        System.out.println();
    }

    public static ArrayList<Integer> degreeSequence(Permutation p) {
        ArrayList<Integer> result = new ArrayList<>();
        int[] d = new int[p.elements.length];
        for (int i = 0; i < p.elements.length; i++) {
            for (int j = i + 1; j < p.elements.length; j++) {
                if (p.elements[i] > p.elements[j]) {
                    d[i]++;
                    d[j]++;
                }
            }
        }
        for (int di : d) {
            result.add(di);
        }
        return result;
    }
    
    public static int[] degreeSequenceArray(Permutation p) {
        int[] d = new int[p.elements.length];
        for (int i = 0; i < p.elements.length; i++) {
            for (int j = i + 1; j < p.elements.length; j++) {
                if (p.elements[i] > p.elements[j]) {
                    d[i]++;
                    d[j]++;
                }
            }
        }
        return d;
    }

    public static Permutation core(Permutation p, int k) {
        ArrayList<Integer> d = degreeSequence(p);
        ArrayList<Integer> toKeep = new ArrayList<Integer>();
        int ki = 0;
        for (int c : d) {
            if (c >= k) {
                toKeep.add(ki);
            }
            ki++;
        }
        if (toKeep.size() == p.length()) {
            return p;
        }
        int[] keep = new int[toKeep.size()];
        int i = 0;
        for (int c : toKeep) {
            keep[i++] = c;
        }
        return core(PermUtilities.subpermutation(p, keep), k);
    }

    private static HashMap<ArrayList<Integer>, ArrayList<Permutation>> doMap(int i) {
        return doMap(i, new UniversalPermClass());
    }

    private static HashMap<ArrayList<Integer>, ArrayList<Permutation>> doMap(int i, PermClassInterface c) {
        HashMap<ArrayList<Integer>, ArrayList<Permutation>> map = new HashMap<>();
        for (Permutation p : new Permutations(c, i)) {
            ArrayList<Integer> dp = degreeSequence(p);
            if (!map.containsKey(dp)) {
                map.put(dp, new ArrayList<Permutation>());
            }
            map.get(dp).add(p);
        }
        return map;
    }

    private static void showMap(int n) {
        HashMap<ArrayList<Integer>, ArrayList<Permutation>> map = doMap(n);
        for (ArrayList<Integer> ds : map.keySet()) {
            System.out.print(ds + " ");
            for (Permutation p : map.get(ds)) {
                System.out.print(p + " ");
            }
            System.out.println();
        }
    }

    private static void findMax(int n) {
        HashMap<ArrayList<Integer>, ArrayList<Permutation>> map = doMap(n);
        int max = 0;
        for (ArrayList<Integer> key : map.keySet()) {
            max = Math.max(max, map.get(key).size());
        }
        System.out.println(n + " " + max);
        for (ArrayList<Integer> key : map.keySet()) {
            ArrayList<Permutation> ps = map.get(key);
            if (ps.size() == max) {
                for (Permutation p : ps) {
                    System.out.print(p + ", ");
                }
                System.out.println();
            }

        }
        System.out.println();
    }

    private static void findLots(int n, int k) {
        for (Permutation p : new Permutations(n)) {
            if (p.elements[0] == 0) continue;
            if (p.compareTo(p.reverse()) > 0) continue;
            if (p.compareTo(p.complement()) > 0) continue;
            if (p.compareTo(p.complement().reverse()) > 0) continue;
            int[] d = degreeSequenceArray(p);
            ArrayList<Permutation> ps = permsFromDegreeSequence(d);
            if (ps.size() >= k) {
                k = (ps.size() > k) ? ps.size() : k;
                boolean good = true;
                for (Permutation q : ps) {
                    if (p.compareTo(q) > 0) {
                        good = false;
                    }
                }
                if (good) {
                    System.out.println(ps.size());
                    for (Permutation q : ps) {
                        System.out.println(q);
                    }
                    System.out.println();
                }
            }
        }
    }

    private static void doAll(int i) {
        try {
            PrintWriter out = new PrintWriter("Out" + i + ".txt");
            for (Permutation p : new Permutations(i)) {
                ArrayList<Integer> dp = degreeSequence(p);
                out.print("[");
                for (int d : dp) {
                    out.print(d + ",");
                }
                out.print("] ");
                out.println(degreeSequence(p) + " " + p);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Oops");
        }

    }

    private static ArrayList<Permutation> permsFromDegreeSequence(int[] d) {
        ArrayList<Permutation> result = new ArrayList<>();
        if (d.length == 1) {
            if (d[0] == 0) {
                result.add(Permutation.ONE);
                return result;
            } else {
                return result;
            }
        }

        // Look for max position and try
        for (int i = 0; i < d.length; i++) {
            if (d[i] == d.length - 1 - i) {
                int[] nd = new int[d.length - 1];
                for (int j = 0; j < i; j++) {
                    nd[j] = d[j];
                }
                for (int j = i; j < nd.length; j++) {
                    nd[j] = d[j + 1] - 1;
                }
                for (Permutation p : permsFromDegreeSequence(nd)) {
                    result.add(p.insert(i, d.length - 1));
                }
            }
        }
        return result;
    }

}
