package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

public class Math {

    private static int power(int n, int k, int p){

    }
    public static int binomial(int n, int k){
        return 0;
    }

    public static Primes primes(int n){
        return new Primes(n);
    }

    private static class Primes implements Iterable<Integer> {

        private final int n;
        private final boolean[] sieve;

        public Primes(int n) {
            this.n = n;
            this.sieve = new boolean[n + 1];
            eratosthenes();
        }

        private void eratosthenes() {
            Arrays.fill(sieve, true);
            sieve[0] = false;
            sieve[1] = false;
            for (int factor = 0; factor * factor <= n; factor++) {
                if (sieve[factor]) {
                    for (int j = factor; factor * j <= n; j++) {
                        sieve[factor * j] = false;
                    }
                }
            }
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<Integer> iterator() {
            return IntStream.rangeClosed(1, n).filter(o -> sieve[o]).iterator();
        }
    }

    public static void main(String[] args) {
        for (int p: primes(5)){
            System.out.println(p);
        }
    }

}
