package com.zyb.study.algorithm.sort;

public class HeadSort extends AbstractSort {
    @Override
    protected void doSort(Comparable[] data) {
        // 建堆
        initHead(data);
        // 排序
        int currentIndex = data.length -1;
        while(currentIndex > 0) {
            swap(data, 0, currentIndex--);
            sink(data, 0, currentIndex);
        }
    }

    /**
     * 初始化堆
     * @param data
     */
    private void initHead(Comparable[] data) {
        int currentIndex = data.length / 2 - 1;
        while(currentIndex >= 0) {
            sink(data, currentIndex--, data.length - 1);
        }
    }

    /**
     * 由上至下的堆有序化（下沉）
     * @param data
     * @param beginIndex
     * @param maxIndex
     */
    private void sink(final Comparable[] data, final int beginIndex, final int maxIndex) {
        int currentIndex = beginIndex;
        int largerIndex = currentIndex * 2 + 1;
        while(largerIndex <= maxIndex) {
            if(largerIndex < maxIndex && less(data[largerIndex], data[largerIndex + 1])) {
                largerIndex++;
            }
            if(less(data[largerIndex], data[currentIndex])) {
                break;
            }
            swap(data, currentIndex, largerIndex);
            currentIndex = largerIndex;
            largerIndex = currentIndex * 2 + 1;
        }
    }

    /**
     * 由下至上的堆有序化（上浮）
     * @param data
     * @param beginIndex
     */
    private void swim(final Comparable[] data, final int beginIndex) {
        int currentIndex = beginIndex;
        while(currentIndex > 0) {
            int parrentIndex = (currentIndex - 1) / 2;
            if(!less(data[parrentIndex], data[currentIndex])) {
                break;
            }
            swap(data, parrentIndex, currentIndex);
            currentIndex = parrentIndex;
        }
    }
}
