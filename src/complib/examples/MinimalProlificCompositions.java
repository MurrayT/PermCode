package complib.examples;

import complib.Composition;
import complib.property.InvolvedIn;
import complib.property.Prolific;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MinimalProlificCompositions {

    public static void main(String[] args) {
        var pattern = new Composition(1,2,2,1,2,4,1);
        var result = new HashSet<Composition>();
        var candidates = new TreeSet<Composition>();

        candidates.add(new Composition(1,4,4,4,4,4,4,4,4,1));
        var seen = new HashSet<>(candidates);

        while (!candidates.isEmpty()){
            var m = candidates.pollLast();

            if (result.stream().anyMatch(new InvolvedIn(m))){
                continue;
            }
            var children = m.onePointDeletions().stream().filter(new Prolific(pattern)).collect(Collectors.toSet());
            if (children.size() > 0) {
                candidates.addAll(children.stream().filter(composition -> !seen.contains(composition)).collect(Collectors.toSet()));
                seen.addAll(children);
            } else {
                result.add(m);
            }
        }
        System.out.println(result);
    }
}
