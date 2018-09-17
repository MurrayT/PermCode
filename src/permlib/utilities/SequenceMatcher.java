package permlib.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

public class SequenceMatcher {

    private final List<Integer> pattern;
    private final BiPredicate<Integer, Integer> predicate;

    SequenceMatcher(List<Integer> pattern, BiPredicate<Integer, Integer> p){
        this.pattern = new ArrayList<>(pattern);
        this.predicate = p;
    }

    List<Integer> longestPrefix(List<Integer> text){
        int textIndex = 0;
        int patternIndex = 0;
        int textSize = text.size();
        int patternSize = pattern.size();
        List<Integer> result = new ArrayList<>();
        while (textIndex < textSize && patternIndex < patternSize){
            if (predicate.test(pattern.get(patternIndex), text.get(textIndex))){
                result.add(pattern.get(patternIndex));
                patternIndex++;
            }
            textIndex++;
        }
        return result;
    }

    List<Integer> longestSuffix(List<Integer> text){
        int textIndex = text.size()-1;
        int patternIndex = pattern.size()-1;
        List<Integer> result = new ArrayList<>();
        while (textIndex >= 0 && patternIndex >= 0){
            if (predicate.test(pattern.get(patternIndex), text.get(textIndex))){
                result.add(pattern.get(patternIndex));
                patternIndex--;
            }
            textIndex--;
        }
        Collections.reverse(result);
        return result;
    }

    Integer prefixSuffixOverlapToIndex(List<Integer> text, int gap){
        var prefix = longestPrefix(text.subList(0, gap));
        var suffix = longestSuffix(text.subList(gap, text.size()));
        System.err.println(prefix);
        System.err.println(suffix);
        System.out.println(prefix.size());
        boolean flag;

        for (int startIndex=0; startIndex<prefix.size(); startIndex++){
            System.out.println("startIndex " + startIndex);
            flag = true;
            for (int endIndexOffset=0; startIndex+endIndexOffset<prefix.size(); startIndex++){
                if (!prefix.get(startIndex+endIndexOffset).equals(suffix.get(endIndexOffset))) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                return startIndex-1;
            }
        }
        return prefix.size();
    }

    boolean matches(List<Integer> text){
        int patternLayerToMatch = 0;
        int textLayerToLook = 0;
        int textSize = text.size();
        int patternSize = pattern.size();

        while (textLayerToLook <= textSize - patternSize + patternLayerToMatch && patternLayerToMatch < patternSize) {
            if (predicate.test(pattern.get(patternLayerToMatch), text.get(textLayerToLook))) {
                patternLayerToMatch++;
            }
            if (patternLayerToMatch == patternSize) {
                return true;
            }
            textLayerToLook++;
        }
        return false;
    }

    public static void main(String[] args) {
        SequenceMatcher pattern = new SequenceMatcher(Arrays.asList(1,2,2,1,2,1), (patt, text) -> patt<=text);
//        System.out.println(pattern.longestPrefix(Arrays.asList(1,3,2,4,5,6,5,1)));
//        System.out.println(pattern.longestSuffix(Arrays.asList(1,3,2,4,5,6,5,1)));
//        System.out.println(pattern.matches(Arrays.asList(1,3,2,4,5,6,5,1)));
        System.out.println(pattern.prefixSuffixOverlapToIndex(Arrays.asList(1,3,2,4,5,6,5,1), 0));
        System.out.println(Arrays.asList(1,2,2,1,2,1).subList(pattern.prefixSuffixOverlapToIndex(Arrays.asList(1,3,2,4,5,6,5,1), 0),6));

        // 1,3,2,4,5,6,5,1
        // 1,2,2,1,-,-,-,-
        // -,-,-,-,2,1,2,1

    }
}
