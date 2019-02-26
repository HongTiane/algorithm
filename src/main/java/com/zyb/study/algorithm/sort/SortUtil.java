package com.zyb.study.algorithm.sort;

import java.util.Objects;

public class SortUtil {

    /**
     * 冒泡排序
     * @param elements
     */
    public static void bubbleSort(final int[] elements) {
        Objects.requireNonNull(elements);

        // 冒泡排序的过程中是否进行数据交换，如果没有，则说明已经达到完全有序，不需要再继续执行后续的冒泡操作
        boolean exchange = false;
        for(int i = 1; i < elements.length; ++i) { // i = 1，减少代码的复杂性，提高效率
            for (int j = 0; j < elements.length - i; ++j) {
                if(elements[j] > elements[j + 1]) {
                    int temp = elements[j + 1];
                    elements[j + 1] = elements[j];
                    elements[j] = temp;
                    exchange = true;
                }
            }
            if(!exchange) {
                break;
            }
            exchange = false;
        }
    }

    /**
     * 插入排序
     * @param elements
     */
    public static void insertionSort(final int[] elements) {
        Objects.requireNonNull(elements);

        for(int i = 1; i < elements.length; ++i) {
            int currentElement = elements[i];
            int j = i - 1; // 从有序序列的最后一个元素开始比较
            for( ; j >= 0; --j) {
                if(elements[j] > currentElement) {
                    elements[j + 1] = elements[j]; // 如果大于当前的元素，后移一位
                } else { // 否则结束当前循环
                    break;
                }
            }
            elements[j + 1] = currentElement; // 当前元素插入到当前有序序列元素的下一个位置
        }
    }

    /**
     * 选择排序
     * @param elements
     */
    public static void selectionSort(final int[] elements) {
        Objects.requireNonNull(elements);

        for(int i = 0; i < elements.length - 1; ++i) {
            int minElemntIndex = i; // 保存无序区间最小元素的下标
            for(int j = i + 1; j < elements.length; ++j) {
                if(elements[j] < elements[minElemntIndex]) {
                    minElemntIndex = j;
                }
            }
            if(minElemntIndex == i) {
                continue;
            }
            int temp = elements[i];
            elements[i] = elements[minElemntIndex];
            elements[minElemntIndex] = temp;
        }
    }

    /**
     * 归并排序
     * @param elements
     */
    public static void mergeSort(int[] elements) {
        mergeSort(elements, 0, elements.length - 1, new int[elements.length / 2]);
    }

    private static void mergeSort(int[] elements, int beginIndex, int endIndex, int[] aux) {
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
     * @param elements
     * @param beginIndex
     * @param middleIndex
     * @param endIndex
     * @param aux 辅助数组
     */
    private static void merge(int[] elements, int beginIndex, int middleIndex, int endIndex, int[] aux) {
        int tempaLen = middleIndex - beginIndex + 1;
        for(int i = 0; i < tempaLen; ++i) { // 将前半部分元素保存在辅助数组中，可减少元素复制，经过一次遍历后有序
            aux[i] = elements[beginIndex + i];
        }

        int i = beginIndex;
        int j = 0;
        int k = middleIndex + 1;
        for(; j < tempaLen && k <= endIndex; ++i) {
            if(aux[j] > elements[k]) {
                elements[i] = elements[k++];
            } else {
                elements[i] = aux[j++];
            }
        }

        while(j < tempaLen) {
            elements[i++] = aux[j++];
        }
    }

    /**
     * 快速排序
     * @param elements
     */
    public static void quickSort(int[] elements) {
        Objects.requireNonNull(elements);

        quickSort(elements, 0, elements.length -1);
    }


    private static void quickSort(int[] elements, int beginIndex, int endIndex) {
        if(beginIndex >=  endIndex) {
            return;
        }

        int pivot = partition(elements, beginIndex, endIndex);

        quickSort(elements, beginIndex, pivot - 1);
        quickSort(elements, pivot + 1, endIndex);
    }

    /**
     * 查找集合中排序为{@code nthLargest}的元素
     * @param elements 待查找的元素集合
     * @param nthLargest 查找的目标元素在集合中的排序
     * @throws IllegalArgumentException 如果{@code nthLargest < 1 || nthLargest > elements.length}抛出此异常
     * @return 返回排序为{@code nthLargest}的元素
     */
    public static int findNthLargestElement(int[] elements, int nthLargest) {
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
    private static int findNthLargestElement(int[] elements, int nthLargest, int beginIndex, int endIndex) {

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
     * @param elements
     * @param beginIndex
     * @param endIndex
     * @return
     */
    private static int partition(int[] elements, int beginIndex, int endIndex) {
        int j = beginIndex; // 小于等于取样点的分区
        for(int i = beginIndex; i < endIndex; ++i) {
            if(elements[i] < elements[endIndex]) { // 将取样元素（分区点）放在末尾来去掉数组边界检测（这里默认将末尾元素作为分区点）
                int temp = elements[j];
                elements[j] = elements[i];
                elements[i] = temp;
                ++j;
            }
        }

        // 取样点放在两个分区之间
        int temp = elements[j];
        elements[j] = elements[endIndex];
        elements[endIndex] = temp;

        return j;
    }

    /**
     * 计数排序，排序之后{@code elements}有序
     * @param elements 待排序的元素集合
     */
    public static void countingSort(int[] elements) {
        Objects.requireNonNull(elements);

        // 查找数组最大的元素
        int maxRange = elements[0];
        for(int i = 1; i < elements.length; ++i) {
            if(maxRange < elements[i]) {
                maxRange = elements[i];
            }
        }

        // 创建范围数组，并且遍历待排序数组，在范围数组对应的下标加一
        int[] ranges = new int[maxRange + 1];
        for(int i = 1; i < elements.length; ++i) {
            ranges[elements[i]]++;
        }

        // 对范围数组进行顺序求和
        int rangeSum = 0;
        for(int i = 0; i < ranges.length; ++i) {
            rangeSum += ranges[i];
            ranges[i] = rangeSum;
        }

        // 创建临时数组，遍历待排序数组，根据元素在范围数组中大小，拷贝到对应临时数组中
        int[] temps = new int[elements.length];
        for(int i = elements.length - 1; i >= 0; --i) {
            temps[ranges[elements[i]]--] = elements[i];
        }

        for(int i = 0; i < temps.length; ++i) {
            elements[i] = temps[i];
        }
    }

    /**
     * 堆排序
     * 1、建堆
     * 2、排序
     * @param elements
     */
    public static void headSort(int[] elements) {
        Objects.requireNonNull(elements);

        initHead(elements);
    }

    /**
     * 堆排序中初始化堆
     * @param elements
     */
    private static void initHead(int[] elements) {
        int beginIndex = elements.length / 2 - 1;
        while(beginIndex >= 0) {

            int currentIndex = beginIndex;
            while(true) {
                int leftChildIndex = beginIndex * 2 + 1;
                int rightChildIndex = beginIndex * 2 + 2;

                int largerIndex = currentIndex;
                if(leftChildIndex < elements.length && elements[largerIndex] < elements[leftChildIndex]) {
                    largerIndex = leftChildIndex;
                }
                if(rightChildIndex < elements.length && elements[largerIndex] < elements[rightChildIndex]) {
                    largerIndex = rightChildIndex;
                }
                if(largerIndex == currentIndex) {
                    break;
                }
                swap(elements, beginIndex, largerIndex);
                currentIndex = largerIndex;
            }
        }
    }

    private static void swap(int[] elements, int firstIndex, int secondIndex) {
        int temp = elements[firstIndex];
        elements[firstIndex] = elements[secondIndex];
        elements[secondIndex] = temp;
    }
}
