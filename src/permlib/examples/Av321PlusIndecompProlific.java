package permlib.examples;

import permlib.PermUtilities;
import permlib.Permutation;

import permlib.Permutations;
import permlib.classes.PermutationClass;
import utilities.CombinationsIncluding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class Av321PlusIndecompProlific {

    static boolean av321prolific(Permutation text, Permutation pattern) {
        for (Permutation ext : av321OnePointExtensions(text)) {
            if (!coveredBy(ext, pattern)) {
                return false;
            }
        }
        return true;
    }

    static boolean coveredBy(Permutation text, Permutation pattern) {
        boolean[] marked = new boolean[text.length()];
        int[] values = new int[pattern.length()];
        for (int i = 0; i < text.length(); i++) {
            if (!marked[i]) {
                for (int[] indices : new CombinationsIncluding(text.length(), pattern.length(), i)) {
                    Permutation selected = text.patternAt(indices);
                    if (selected.equals(pattern)) {
                        for (int index :
                                indices) {
                            marked[index] = true;
                        }
                    }
                }
                if (!marked[i]){
                    return false;
                }
            }
        }
        return true;
    }

    static Collection<Permutation> av321OnePointExtensions(Permutation text) {
        Collection<Permutation> result = new LinkedHashSet<>();
        for (int index = 0; index <= text.length(); index++) {
            for (int value = 0; value <= text.length(); value++) {
                boolean extendable = true;
                boolean upleft = false;
                int maxValue = value;
                for (int i = 0; i < index; i++) {
                    int currentValue = text.at(i);
                    if (currentValue >= value) {
                        upleft = true;
                        if (currentValue < maxValue) {
                            extendable = false;
                            break;
                        } else {
                            maxValue = currentValue;
                        }

                    }
                }
                if (extendable) {
                    maxValue = 0;
                    for (int i = index; i < text.length(); i++) {
                        int currentValue = text.at(i);
                        if (text.at(i) < value) {
                            if (upleft) {
                                extendable = false;
                                break;
                            }
                            if (currentValue < maxValue) {
                                extendable = false;
                                break;
                            } else {
                                maxValue = currentValue;
                            }
                        }
                    }
                    if (extendable) {
                        result.add(PermUtilities.insert(text, index, value));
                    }
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {

//        Permutation text = new Permutation("1 4 5 2 3 6 9 10 7 8 11");
//        Permutation pattern = new Permutation("145236");
//
//        1 a a 1
//        1 4 2 5 3 8 6 9 7 10
//        1 3 5 2 4 7 9 6 8 10
//        1 a 1 a 1
//        1 3 5 2 4 7 9 6 8 10
//        1 5 2 3 4 9 6 7 8 10
//        fail
//        1 4 5 2 3 8 9 6 7 10

//        av321OnePointExtensions(text).forEach(x -> System.err.println(x + ": " + coveredBy(x,pattern)));


        PermutationClass a321 = new PermutationClass("321");
        Permutations a321p = new Permutations(a321,1,6);

        for (Permutation alpha : a321p) {
            if (PermUtilities.PLUSINDECOMPOSABLE.isSatisfiedBy(alpha)) {
                Permutation firstHalf = PermUtilities.sum(Permutation.ONE, alpha);
                Permutation secondHalf = PermUtilities.sum(alpha, Permutation.ONE);
                Permutation pattern = PermUtilities.sum(Permutation.ONE, secondHalf);
                Permutation candidate = PermUtilities.sum(PermUtilities.sum(PermUtilities.sum(Permutation.ONE, alpha), alpha),Permutation.ONE);
                int lowerbound = pattern.length()-2;
                int upperbound = pattern.length()*3;
                Permutation likely = PermUtilities.sum(PermUtilities.sum(firstHalf,Permutation.ONE),secondHalf);
                if (av321prolific(candidate,pattern)){
                    System.out.println("alpha: " + alpha + ", " + "candidate: " + candidate + " simple: " + PermUtilities.SIMPLE.isSatisfiedBy(alpha));
                    lowerbound = candidate.length() - 3;
                    upperbound = candidate.length()-2;
                } else if (av321prolific(likely,pattern)){
                    System.out.println("alpha: " + alpha + ", " + "l: " + likely + " simple: " + PermUtilities.SIMPLE.isSatisfiedBy(alpha));
                    lowerbound = likely.length() - 3;
                    upperbound = likely.length()- 2;

                }else{
                    System.out.println("No candidate easily found for alpha " + alpha);
                }
                System.err.println(lowerbound + " " + upperbound);
                boolean found = false;
                for (int i = lowerbound; i < upperbound; i++) {
                    for (Permutation p: new Permutations(a321, i)) {
                        Permutation c = PermUtilities.sum(Permutation.ONE, PermUtilities.sum(p, Permutation.ONE));
                        if(av321prolific(c,pattern)){
                            found = true;
                            System.out.println("--- " + c);
                            break;
                        }
                    }
                    if (found) break;
                }

//                System.err.println(alpha + ",\t" + av321prolific(candidate,pattern) +",\t" + av321prolific(guaranteed,pattern) + " simple: " + PermUtilities.SIMPLE.isSatisfiedBy(alpha));
            }
        }
    }
}
