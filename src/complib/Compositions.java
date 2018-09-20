package complib;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A class that provides the compositions of an Integer {@code n}
 */
public class Compositions implements Iterable<Composition> {

    private final int n;

    public Compositions(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        Path folder = FileSystems.getDefault().getPath(args[2]);
        try {
            if (!Files.exists(folder)) {
                Files.createDirectory(folder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (int l = 1; l <= Integer.valueOf(args[0]); l++) {
            int l = Integer.valueOf(args[0]);{
            for (Composition pattern : new Compositions(l)) {
//        StrongComposition pattern = new StrongComposition(args[1]);
                System.err.println(pattern);
                Path outfile = FileSystems.getDefault().getPath(folder.toString(), "compositionsCounts" + pattern);
                try {
                    BufferedWriter writer = Files.newBufferedWriter(outfile, StandardCharsets.UTF_8);
                    System.err.println(outfile);
                    long t1 = System.nanoTime();
                    for (int length = 1; length <= Long.valueOf(args[1]); length++) {
                        System.err.println(length);
                        Map<Long, Integer> map = new HashMap<>();
                        for (Composition sc : new Compositions(length)) {
                            long occCount = Composition.occurrenceCount(pattern, sc);
                            map.put(occCount, map.getOrDefault(occCount, 0) + 1);
                        }
                        for (long j = 0; j <= map.keySet().stream().max(Long::compare).orElse(0L); j++) {
                            System.out.printf("%06d, ", map.getOrDefault(j, 0));
                            writer.write(String.format("%06d, ", map.getOrDefault(j, 0)));
                        }
                        writer.newLine();
                        System.out.println();
                    }
                    long t2 = System.nanoTime();
                    System.err.println("Execution time: " + (t2 - t1));
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        for (StrongComposition sc: new Compositions(7)){
//            if (StrongComposition.occurrenceCount(new StrongComposition(1,2,1), sc) == 4){
//                System.out.println(sc);
//            }
//        }

    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Composition> iterator() {
        return new CompositionsIterator(n);
    }

    public static class CompositionsIterator implements Iterator<Composition> {

        private final int n;
        private int[] comp;
        private int i;

        public CompositionsIterator(int n) {
            this.n = n;
            comp = new int[n];
            i =0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return comp[0] != n;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Composition next() {
            //find the index of the first zero value in comp
            if (n==0){
                return new Composition();
            }
            if (i==0){
                Arrays.fill(comp, 1);
                i = n;
            } else {
                comp[i - 2]++; // Increment the second to last non-zero element
                int diff = comp[--i]; // record the value of the last non-zero element so that can fill the right number of ones
                comp[i] = 0; // Throw away the last non-zero element;

                // Fill the array so our composition has the correct size
                if (diff > 1){
                    Arrays.fill(comp, i, i+diff-1, 1);
                    i = i+diff-1;
                }
            }
            return new Composition(Arrays.copyOfRange(comp, 0, i));
        }

    }
}
