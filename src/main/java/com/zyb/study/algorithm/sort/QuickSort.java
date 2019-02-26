package com.zyb.study.algorithm.sort;

import java.util.Objects;

public class QuickSort extends AbstractSort {
    @Override
    protected void doSort(Comparable[] data) {
        quickSort(data, 0, data.length -1);
    }

    private void quickSort(Comparable[] data, int beginIndex, int endIndex) {
        if(beginIndex >=  endIndex) {
            return;
        }

        int pivot = partition(data, beginIndex, endIndex);

        quickSort(data, beginIndex, pivot - 1);
        quickSort(data, pivot + 1, endIndex);
    }

    /**
     * 查找集合中排序为{@code nthLargest}的元素
     * @param elements 待查找的元素集合
     * @param nthLargest 查找的目标元素在集合中的排序
     * @throws IllegalArgumentException 如果{@code nthLargest < 1 || nthLargest > elements.length}抛出此异常
     * @return 返回排序为{@code nthLargest}的元素
     */
    public <E extends Comparable<? super E>> E  findNthLargestElement(E[] elements, int nthLargest) {
        Objects.requireNonNull(elements);
        if(nthLargest < 1 || nthLargest > elements.length) {
            throw new IllegalArgumentException("nthLargest: " + nthLargest);
        }

        return findNthLargestElement(elements, nthLargest, 0, elements.length -1);


    }

    /**
     * 在{@literal >= beginIndex, < endIndex}范围内递归查找集合中排序为{@code nthLargest}的元素
     * @param elements 待查找的元素集合
     * @param nthLargest 查找的目标元素在集合中的排序
     * @param beginIndex 查找范围的开始下标
     * @param endIndex 查找范围的结束下标
     * @return 返回排序为{@code nthLargest}的元素
     */
    private <E extends Comparable<? super E>> E findNthLargestElement(E[] elements, int nthLargest, int beginIndex, int endIndex) {

        if(beginIndex >= endIndex) {
            return elements[beginIndex];
        }

        int pivot = partition(elements, beginIndex, endIndex);
        if(pivot == nthLargest - 1) {
            return elements[pivot];
        }else if(pivot < nthLargest) {
            return findNthLargestElement(elements, nthLargest, pivot + 1, endIndex);
        } else {
            return findNthLargestElement(elements, nthLargest, beginIndex, pivot - 1);
        }
    }

    /**
     * 分区函数
     * @param data
     * @param beginIndex
     * @param endIndex
     * @return
     */
    private int partition(Comparable[] data, int beginIndex, int endIndex) {
        int j = beginIndex; // 小于等于取样点的分区
        for(int i = beginIndex; i < endIndex; ++i) {
            if(comparator.compare(data[i], data[endIndex]) < 0) { // 将取样元素（分区点）放在末尾来去掉数组边界检测（这里默认将末尾元素作为分区点）
                swap(data, i, j);
                ++j;
            }
        }

        // 取样点放在两个分区之间
        swap(data, j, endIndex);

        return j;
    }
}
