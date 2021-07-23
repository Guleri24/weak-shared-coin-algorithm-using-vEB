package com.guleri24;
import static java.lang.Long.numberOfTrailingZeros;

/**
 * Expected Functions:
 * insert(MaxRegister)
 */
public class vEB<MaxRegister extends com.guleri24.MaxRegister> {
    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;
    private final int universeSize;
    private final int shift, mask;
    private final vEB<MaxRegister> summary;
    private final vEB<MaxRegister>[] clusters;
    private final int min, max;
    private final MaxRegister minValue;
    private final MaxRegister maxValue;

    public vEB(int universeSize, MaxRegister minValue, MaxRegister maxValue) {
        this.universeSize = universeSize;
        this.minValue = minValue;
        this.maxValue = maxValue;
        min = NIL;
        max = NIL;

        int universeSizeLowerSquare = lowerSquare(universeSize);
        mask = universeSizeLowerSquare - 1;
        shift = numberOfTrailingZeros(universeSizeLowerSquare);

        if (universeSize == MIN_UNIVERSE_SIZE) {
            summary = null;
            clusters = null;
        }
        else {
            int universeSizeUpperSquare = higherSquare(universeSize);
            summary = new vEB<>(universeSizeUpperSquare, minValue, maxValue);
            clusters = new vEB[universeSizeUpperSquare];
            for (int i = 0; i< universeSizeUpperSquare; i++)
                clusters[i] = new vEB<>(universeSizeUpperSquare, minValue, maxValue);
        }
    }

    private int higherSquare(int universeSize) {
    }

    private int getUniverseSize() {
        return universeSize;
    }

    private int getMin() {
        return min;
    }

    private int getMax() {
        return max;
    }

    private MaxRegister getValue(int key) {
        if (key == min) return minValue;
        if (key == max) return maxValue;
        if (universeSize == 2) return null;
        return clusters[high(key)].getValue(low(key));
    }

    private boolean contains(int key) {
        if (key == min || key == max) return true;
        if (universeSize == 2) return false;
        return clusters[high(key)].getValue(low(key));
    }

    private void emptyInsert(int key, MaxRegister R) {
        min = key;
        max = key;
        minValue = R;
        maxValue = R;

    }

    private void insert(int key, MaxRegister R) {
        if (min == NIL)
            emptyInsert(key, R);
    }

    private int low(int key) {
    }

    private int lowerSquare(int universeSize) {

    }

    private Integer high(Integer key) {
        return key >>> shift;
    }

}