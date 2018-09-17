package permlib.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

public class Math {

    private static boolean[] sieve;
    private static int primeMax = 0;

    public static long gcd(int a, int b){
        int r=a%b;
        while (r!=0){
            a = b;
            b = r;
            r = a%b;
        }
        return b;
    }

    public static long gcd(long a, long b){
        long r=a%b;
        while (r!=0){
            a = b;
            b = r;
            r = a%b;
        }
        return b;
    }

    public static long binomial(int n, int k){
        if (k>n/2){
            k = n-k;
        }
        if (n<0 || k<0){
            return 0;
        }
        if (k==0){
            return 1;
        }
        long binom = n-k+1;
        System.err.println(binom);
        for (long i=2; i<=k; i++){
            binom *= n-k+i;
            System.err.println(binom);
            binom /= i;
            System.err.println(binom);
        }
        return binom;
    }
    public static Primes primes(int n){
        return new Primes(n);
    }

    private static class Primes implements Iterable<Integer> {

        private final int n;

        Primes(int n) {
            this.n = n;
            if (n>primeMax) {
                sieve = new boolean[n + 1];
                eratosthenes();
                primeMax = n;
            }
        }

        private void eratosthenes() {
            Arrays.fill(sieve, true);
            sieve[0] = false;
            sieve[1] = false;
            for (int factor = primeMax; factor * factor <= n; factor++) {
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
        System.out.println(binomial(13,4));
    }
}
