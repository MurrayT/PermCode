package permlib.utilities;

import java.util.Iterator;

class CombinationsIterator implements Iterator<int[]> {


    final int n;
    final int k;
    int[] c = null;

    public CombinationsIterator(int n, int k) {
        this.n =n;
        this.k = k;
    }

    /**
     * Returns true if there is a next combination in the set.
     */
    @Override
    public boolean hasNext() {
        return k <= n && (c == null || (k > 0 && c[0] < n - k));
    }

    /**
     * Returns the next combination in the set.
     */
    @Override
    public int[] next() {
        if (c == null) {
            createFirstCombination();
        } else {
            update();
        }
        return c;
    }

    /**
     * An operation to remove a combination from the set. This is not
     * supported in this context.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported.");
    }

    void createFirstCombination() {
        c = new int[k];
        for (int i = 0; i < k; i++) {
            c[i] = i;
        }
    }

    void update() {
        int i = 1;
        while (i <= k && c[k - i] == n - i) {
            i++;
        }
        if (i > k) {
            return;
        }
        c[k - i]++;
        for (int j = k - i + 1; j < k; j++) {
            c[j] = c[j - 1] + 1;
        }
    }
}
