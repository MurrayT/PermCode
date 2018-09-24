package complib;


import complib.classes.CompositionClassInterface;
import complib.classes.UniversalCompositionClass;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

/**
 * A class that provides the compositions of an Integer {@code n}
 */
public class Compositions implements Iterable<Composition> {

    private CompositionClassInterface c;
    private int low;
    private int high;
    private Predicate<Composition> prop;

    /**
     * Create new collection of permutations.
     *
     * @param c permutation class
     * @param low lower length bound
     * @param high upper length bound
     */
    public Compositions(CompositionClassInterface c, int low, int high) {
        this(c, low, high, null);
    }

    /**
     * Create new collection of permutations.
     *
     * @param c permutation class
     * @param length length of permutation
     * @param prop property of to impose on class
     */
    public Compositions(CompositionClassInterface c, int length, Predicate<Composition> prop) {
        this(c, length, length, prop);
    }

    /**
     * Create new collection of permutations.
     *
     * @param c permutation class
     * @param length length of permutation
     */
    public Compositions(CompositionClassInterface c, int length) {
        this(c, length, length);
    }

    /**
     * Create new collection of permutations.
     *
     * @param low lower length bound
     * @param high upper length bound
     * @param prop property of to impose on class
     */
    public Compositions(int low, int high, Predicate<Composition> prop) {
        this(new UniversalCompositionClass(high), low, high, prop);
    }

    /**
     * Create new collection of permutations.
     *
     * @param low lower length bound
     * @param high upper length bound
     */
    public Compositions(int low, int high) {
        this(new UniversalCompositionClass(high), low, high);
    }

    /**
     * Create new collection of permutations.
     *
     * @param length length of permutation
     * @param prop property of to impose on class
     */
    public Compositions(int length, Predicate<Composition> prop) {
        this(new UniversalCompositionClass(length), length, length, prop);
    }

    /**
     * Create new collection of permutations.
     *
     * @param length length of permutation
     */
    public Compositions(int length) {
        this(new UniversalCompositionClass(length), length);
    }

    /**
     * Create new collection of permutations.
     *
     * @param c permutation class
     * @parCompositionsam low lower length bound
     * @param high upper length bound
     * @param prop property of to impose on class
     */
    public Compositions(CompositionClassInterface c, int low, int high, Predicate<Composition> prop) {
        this.c = c;
        this.low = low;
        this.high =  high;
        this.prop = prop;
    }

    /**
     * Returns the iterator for this class.
     *
     * @return the iterator for this class
     */
    @Override
    public Iterator<Composition> iterator() {
        if(prop == null) {
            return c.getIterator(low, high);
        }
        return c.getRestrictedIterator(low, high, prop);
    } //End of Iterator

    public static void main(String[] args) {

        for (Composition x: new Compositions(0,5, x->x.value > 0 && x.getElements()[0]>1 )){
            System.out.println(x);
        }
//        Path folder = FileSystems.getDefault().getPath(args[2]);
//        try {
//            if (!Files.exists(folder)) {
//                Files.createDirectory(folder);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        for (int l = 1; l <= Integer.valueOf(args[0]); l++) {
//            int l = Integer.valueOf(args[0]);{
//            for (Composition pattern : new Compositions(l)) {
////        StrongComposition pattern = new StrongComposition(args[1]);
//                System.err.println(pattern);
//                Path outfile = FileSystems.getDefault().getPath(folder.toString(), "compositionsCounts" + pattern);
//                try {
//                    BufferedWriter writer = Files.newBufferedWriter(outfile, StandardCharsets.UTF_8);
//                    System.err.println(outfile);
//                    long t1 = System.nanoTime();
//                    for (int length = 1; length <= Long.valueOf(args[1]); length++) {
//                        System.err.println(length);
//                        Map<Long, Integer> map = new HashMap<>();
//                        for (Composition sc : new Compositions(length)) {
//                            long occCount = Composition.occurrenceCount(pattern, sc);
//                            map.put(occCount, map.getOrDefault(occCount, 0) + 1);
//                        }
//                        for (long j = 0; j <= map.keySet().stream().max(Long::compare).orElse(0L); j++) {
//                            System.out.printf("%06d, ", map.getOrDefault(j, 0));
//                            writer.write(String.format("%06d, ", map.getOrDefault(j, 0)));
//                        }
//                        writer.newLine();
//                        System.out.println();
//                    }
//                    long t2 = System.nanoTime();
//                    System.err.println("Execution time: " + (t2 - t1));
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }


}
