package permlib.utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;

public class Math {

    private static boolean[] sieve;
    private static int primeMax = 0;

    private static int binomialPower(int n, int k, int p){
        int E=0;
        int r = 0;
        if (p > n-k)
            return 1;
        if (p > n/2)
            return 0;
        if (p*p > n){
            if (n%p < k%p)
                return 1;
        }
        int a, b;
        while (n>0){
            a = n%p;
            n = n/p;
            b = k%p + r;
            k = k/p;
            if (a<b){
                E++;
                r = 1;
            } else {
                r = 0;
            }
        }
        return E;
    }

    public static long binomial(int n, int k){
        if (k>n/2){
            k = n-k;
        }
        long binomial=1;
        for (int p: primes(n)){
            int N = n;
            int K = k;
            int r = 0;
            if (p > N-K){
                binomial *= p;
                continue;
            }
            if (p > N/2) {
                continue;
            }
            if (p*p > N){
                if (N%p < K%p) {
                    binomial *= p;
                    continue;
                }
            }
            while (N>0){
                if (N%p<K%p + r){
                    binomial=binomial*p;
                    r = 1;
                } else {
                    r = 0;
                }
                N = N/p;
                K = K/p;
            }
        }
        return binomial;
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
        int count = 0;
        for (int p: primes(25)){
            int pow = binomialPower(25,6, p);
            if (pow > 0){
                System.out.println(p + ": " +pow);
                count++;
            }
        }
        System.out.println("count: "+count);

        System.out.println(binomial(25,6));
    }

}
