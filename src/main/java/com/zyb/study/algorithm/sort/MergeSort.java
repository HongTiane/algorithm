package com.zyb.study.algorithm.sort;

public class MergeSort extends AbstractSort {
    @Override
    protected void doSort(Comparable[] data) {
        mergeSort(data, 0, data.length - 1, (Comparable[]) new Object[data.length / 2]);
    }

    private void mergeSort(Comparable[] elements, int beginIndex, int endIndex, Comparable[] aux) {
        if(beginIndex >= endIndex) {
            return;
        }
        int middleIndex = (beginIndex + endIndex) / 2;
        mergeSort(elements, beginIndex, middleIndex, aux);
        mergeSort(elements, middleIndex + 1, endIndex, aux);

        merge(elements, beginIndex, middleIndex, endIndex, aux);
    }

    /**
     * 合并函数
     * @param data
     * @param beginIndex
     * @param middleIndex
     * @param endIndex
     * @param aux 辅助数组
     */
    private void merge(Comparable[] data, int beginIndex, int middleIndex, int endIndex, Comparable[] aux) {
        int tempaLen = middleIndex - beginIndex + 1;
        for(int i = 0; i < tempaLen; ++i) { // 将前半部分元素保存在辅助数组中，可减少元素复制，经过一次遍历后有序
            aux[i] = data[beginIndex + i];
        }

        int i = beginIndex;
        int j = 0;
        int k = middleIndex + 1;
        for(; j < tempaLen && k <= endIndex; ++i) {
            if(comparator.compare(aux[j], data[k]) > 0) {
                data[i] = data[k++];
            } else {
                data[i] = aux[j++];
            }
        }

        while(j < tempaLen) {
            data[i++] = aux[j++];
        }
    }
}
