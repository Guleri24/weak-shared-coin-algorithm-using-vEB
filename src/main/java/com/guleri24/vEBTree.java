package com.guleri24;

import java.util.*;

import static java.lang.Long.numberOfTrailingZeros;
import static java.lang.Math.*;


public class vEBTree<E> {
    private static final int MIN_UNIVERSE_SIZE = 2;
    private static final int NIL = -1;

    private final vEB<E> root;

    public vEBTree(int universeSize) {
        root = new vEB<>(universeSize);
    }

    private static int lowerSquare(int i) {
        return (int) pow(2.0, floor((log(i) / log(2.0)) / 2.0));
    }

    private static int upperSquare(int i) {
        return (int) pow(2.0, ceil((log(i) / log(2.0)) / 2.0));
    }

    public E put(Integer key, E value) {
        if (key == null || value == null)
            throw new NullPointerException();
        if (key < 0 || key > (root.universeSize - 1))
            throw new IndexOutOfBoundsException(key + " is not an element in the universe [0," + root.universeSize + "[");
        E oldValue = root.contains(key) ? root.getValue(key) : null;
        if (oldValue != null)
            root.remove(key);
        root.insert(key, value);
        return oldValue;
    }


    public Collection<E> values() {
        List<E> values = new LinkedList<>();
        if (root.getMin() == NIL) return values;
        for (Integer key : keySet()) {
            values.add(root.getValue(key));
        }
        return values;
    }

    public Set<Integer> keySet() {
        Set<Integer> keys = new LinkedHashSet<>();
        if (root.getMin() == NIL) return keys;
        int key = root.getMin();
        while (key != NIL) {
            keys.add(key);
            key = root.successor(key);
        }
        return keys;
    }

    private static final class vEB<E> {

        private final int universeSize;
        private final int shift, mask;
        private final vEB<E> summary;
        private final vEB<E>[] clusters;
        private int min;
        private int max;
        private E minValue;
        private E maxValue;

        vEB(int universeSize) {
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
                for (int i = 0; i < universeSizeUpperSquare; i++) {
                    clusters[i] = new vEB<>(universeSizeLowerSquare);
                }
            }
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

        private E getValue(int key) {
            if (key == min) return minValue;
            if (key == max) return maxValue;
            if (universeSize == 2) return null;
            return clusters[high(key)].getValue(low(key));
        }

        private boolean contains(int key) {
            if (key == min || key == max) return true;
            if (universeSize == 2) return false;
            return clusters[high(key)].contains(low(key));
        }

        private int successor(int key) {
            if (universeSize == 2)
                if (key == 0 && max == 1) return 1;
                else return NIL;
            if (min != NIL && key < min) return min;
            int maxLow = clusters[high(key)].getMax();
            if (maxLow != NIL && low(key) < maxLow) return index(high(key), clusters[high(key)].successor(low(key)));
            int successorCluster = summary.successor(high(key));
            if (successorCluster == NIL) return NIL;
            return index(successorCluster, clusters[successorCluster].getMin());
        }

        private int predecessor(int key) {
            if (universeSize == 2)
                if (key == 1 && min == 0) return 0;
                else return NIL;
            if (max != NIL && key > max) return max;
            int minLow = clusters[high(key)].getMin();
            if (minLow != NIL && low(key) > minLow) return index(high(key), clusters[high(key)].predecessor(low(key)));
            int predecessorCluster = summary.predecessor(high(key));
            if (predecessorCluster == NIL)
                if (min != NIL && key > min) return min;
                else return NIL;
            return index(predecessorCluster, clusters[predecessorCluster].getMax());
        }

        private void emptyInsert(int key, E value) {
            min = key;
            max = key;
            minValue = value;
            maxValue = value;
        }

        private void insert(int key, E value) {
            if (min == NIL)
                emptyInsert(key, value);
            else {
                if (key < min) {
                    int tmpK = key;
                    E tmpV = value;
                    key = min;
                    min = tmpK;
                    value = minValue;
                    minValue = tmpV;
                }
                if (universeSize > 2)
                    if (clusters[high(key)].getMin() == NIL) {
                        summary.insert(high(key), value);
                        clusters[high(key)].emptyInsert(low(key), value);
                    } else
                        clusters[high(key)].insert(low(key), value);
                if (key > max) {
                    max = key;
                    maxValue = value;
                }
            }
        }

        private void remove(int key) {
            if (min == max) {
                min = NIL;
                max = NIL;
                minValue = null;
                maxValue = null;
            } else if (universeSize == 2) {
                if (key == 0) {
                    minValue = maxValue;
                    min = 1;
                } else
                    min = 0;
                maxValue = minValue;
                max = min;
            } else {
                if (key == min) {
                    int firstCluster = summary.getMin();
                    key = index(firstCluster, clusters[firstCluster].getMin());
                    minValue = getValue(key);
                    min = key;
                }
                clusters[high(key)].remove(low(key));
                if (clusters[high(key)].getMin() == NIL) {
                    summary.remove(high(key));
                    if (key == max) {
                        int summaryMax = summary.getMax();
                        if (summaryMax == NIL) {
                            maxValue = minValue;
                            max = min;
                        } else {
                            int m = index(summaryMax, clusters[summaryMax].getMax());
                            maxValue = getValue(m);
                            max = m;
                        }
                    }
                } else if (key == max) {
                    int m = index(high(key), clusters[high(key)].getMax());
                    maxValue = getValue(m);
                    max = m;
                }
            }
        }

        /**
         * high(x) = floor( x / lowerSquare(universe) )
         * <p>
         * shift is a power of 2 (Math.pow(2,shift) = universeSizeLowerSquare)
         * x / u  =  x >>> shift   (if 2^shift=u)
         */
        private int high(int x) {
            return x >>> shift;
        }

        /**
         * low(x) = x % lowerSquare(universe)
         * <p>
         * x % y = (x & (y − 1))
         */
        private int low(int x) {
            return x & mask;
        }

        /**
         * index(x, y) = x times lowerSquare(universe) + y
         * <p>
         * x times u  =  x << shift    (if 2^shift=u)
         * x + y  =  x | y    (only true if x&y=0  (which is always the case if you have (x << shift) as left operand and (y a mask) as right operand))
         */
        private int index(int x, int y) {
            return (x << shift) | (y & mask);
        }
    }
}