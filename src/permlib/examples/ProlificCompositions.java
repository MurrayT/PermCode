package permlib.examples;

import permlib.utilities.StrongComposition;
import permlib.utilities.StrongCompositions;

import java.util.HashMap;
import java.util.Map;

public class ProlificCompositions {

    public static void main(String[] args) {
//        StrongComposition pattern = new StrongComposition(1,2,2,1);
        for (StrongComposition pattern : new StrongCompositions(8)) {
            if (StrongComposition.isInitiallyPersistent(pattern)) {
                StrongComposition sc = StrongComposition.prolificExtension(pattern);
                long occurrence_count = StrongComposition.occurrenceCount(pattern, sc);
                Map<Long, Integer> map = new HashMap<>();
                for (StrongComposition comp : new StrongCompositions(sc.value+1)) {
                    long occCount = StrongComposition.occurrenceCount(pattern, comp);
                    if (occCount == 19L) {
//                        System.out.println(comp);
                    }
                    map.put(occCount, map.getOrDefault(occCount, 0) + 1);
                }
                System.err.printf("The expansion of %s (%s) has %d occurrences. There are %d patterns with that many occurrences in length %d\n",
                        pattern.toString(), sc.toString(), occurrence_count, map.getOrDefault(occurrence_count, 0), sc.value + 1);

            }
        }
    }
}
