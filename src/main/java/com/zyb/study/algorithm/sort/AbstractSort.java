package com.zyb.study.algorithm.sort;

import java.util.Comparator;
import java.util.Objects;

public abstract class AbstractSort<T extends Comparable<? super T>> {

    protected static final Comparator comparator = Comparator.nullsFirst(Comparator.naturalOrder());

    public void sort(final T[] data) {
        Objects.requireNonNull(data);
        doSort(data);
    }

    protected abstract void doSort(final T[] data);

    protected void swap(final Object[] elements, final int firstIndex, final int secondIndex) {
        Object temp = elements[firstIndex];
        elements[firstIndex] = elements[secondIndex];
        elements[secondIndex] = temp;
    }

    /**
     * 判断{@code i < j}
     * @param i
     * @param j
     * @return
     */
    protected boolean less(final Comparable i, final Comparable j) {
        return comparator.compare(i, j) < 0;
    }

    /**
     * 判断{@code i > j}
     * @param i
     * @param j
     * @return
     */
    protected boolean great(final Comparable i, final Comparable j) {
        return comparator.compare(i, j) > 0;
    }

}
