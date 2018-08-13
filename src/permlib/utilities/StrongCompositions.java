package permlib.utilities;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A class that provides the compositions of an Integer {@code n}
 */
public class StrongCompositions implements Iterable<StrongComposition> {

    private final int n;

    public StrongCompositions(int n) {
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
            for (StrongComposition pattern : new StrongCompositions(l)) {
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
                        for (StrongComposition sc : new StrongCompositions(length)) {
                            long occCount = StrongComposition.occurrenceCount(pattern, sc);
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
//        for (StrongComposition sc: new StrongCompositions(7)){
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
    public Iterator<StrongComposition> iterator() {
        return new CompositionsIterator(n);
    }

}
