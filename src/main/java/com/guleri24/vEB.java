package com.guleri24;

import static java.lang.Long.numberOfTrailingZeros;
import static java.lang.Math.*;

/**
 * Expected Functions:
 * insert(Key, MaxRegister)
 */
public class vEB<MaxRegister extends com.guleri24.MaxRegister> {
    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;
    private final int universeSize;
    private final int shift, mask;
    private final vEB<MaxRegister> summary;
    private final vEB[] clusters;
    private int min;
    private int max;
    private int minValue;
    private int maxValue;

    public vEB(int universeSize) {
        this.universeSize = universeSize;
        min = NIL;
        max = NIL;

        int universeSizeLowerSquare = lowerSquare(universeSize);
        mask = universeSizeLowerSquare - 1;
        shift = numberOfTrailingZeros(universeSizeLowerSquare);

        if (universeSize == MIN_UNIVERSE_SIZE) {
            summary = null;
            clusters = null;
        } else {
            int universeSizeUpperSquare = upperSquare(universeSize);
            summary = new vEB<>(universeSizeUpperSquare);
            clusters = new vEB[universeSizeUpperSquare];
            for (int i = 0; i < universeSizeUpperSquare; i++)
                clusters[i] = new vEB<>(universeSizeUpperSquare);
        }
    }

    private int getMin() {
        return min;
    }

    private int getMax() {
        return max;
    }

    private void emptyInsert(int key, MaxRegister R) {
        min = key;
        max = key;
        minValue = R.getTotal();
        maxValue = R.getTotal();

    }

    public void insert(int key, MaxRegister R) {
        if (min == NIL)
            emptyInsert(key, R);
        else {

            // Not Required for out use as in problem the key is growing
            if (key < min) {
                // Swapping
                int tmpK = key;
                key = min;
                min = tmpK;
                R.setTotal(minValue);
                minValue = R.getTotal();
            }

            if (universeSize > 2)
                if (clusters[high(key)].getMin() == NIL) {
                    summary.insert(high(key), R);
                } else
                    clusters[high(key)].insert(low(key), R);
            if (key > max) {
                max = key;
                maxValue = R.getTotal();
            }
        }
    }


    private int low(int x) {
        return x & mask;
    }

    private Integer high(Integer x) {
        return x >>> shift;
    }

    private int lowerSquare(int i) {
        return (int) pow(2.0, floor((log(i) / log(2.0)) / 2.0));
    }

    private int upperSquare(int i) {
        return (int) pow(2.0, ceil((log(i) / log(2.0)) / 2.0));
    }

    // Todo : Root Max-Register
    public int rootMaxRegister() {
        return summary.max;
    }
}