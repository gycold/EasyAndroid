package com.easytools.tools;

import java.util.Stack;

/**
 * package: com.easytools.tools.ArrayUtils
 * author: gyc
 * description:关于数组的各种排序算法
 * time: create at 2017/1/6 13:50
 */

public class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * is null or its length is 0
     *
     * @param <V>
     * @param sourceArray
     * @return
     */
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * 在数组objects中搜索元素element
     *
     * @param objects 待操作的数组
     * @param element 待匹配的元素
     * @return 索引，如不存在，-1
     */
    public static int search(Object[] objects, Object element) {
        int e = -1;
        for (int w = 0; w < objects.length; w++) {
            if (!element.equals(objects[w])) {
                continue;
            } else {
                e = w;
                break;
            }
        }
        return e;
    }

    /* *********Int数组排序相关start***************** */


    /**
     * 选择排序法
     * 在未排序序列中找到最小/最大元素，存放到排序序列的起始位置
     * 再从剩余未排序元素中继续寻找最小/最大元素，然后放到排序序列末尾。
     *
     * @param intArray  待排序数组
     * @param ascending 排序方式：true：升序，false：降序
     */
    public static void selectSort(int[] intArray, boolean ascending) {
        for (int i = 0; i < intArray.length - 1; i++) {
            int temp = intArray[i];
            int min = 0;
            for (int j = i + 1;
                 j <= intArray.length - 1;
                 j++) {
                boolean type = true;
                if (ascending) {
                    type = temp > intArray[j];
                } else {
                    type = temp < intArray[j];
                }
                if (type) {
                    temp = intArray[j];
                    min = j;
                }
            }

            if (min != 0) {
                int f = intArray[min];
                intArray[min] = intArray[i];
                intArray[i] = f;
            }
        }
    }

    /**
     * 使用插入排序法，对数组intArray进行排序
     * 1.从第一个元素开始，该元素可以认为已经被排序
     * 2.取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3.如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 4.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 5.将新元素插入到该位置中
     * 6.重复步骤2
     *
     * @param intArray  待排序的数组
     * @param ascending 排序方式：true：升序，false：降序
     */
    public static void insertSort(int[] intArray, boolean ascending) {
        for (int i = 1; i < intArray.length; i++) {
            int t = intArray[i];
            int y = -1;
            for (int j = i - 1; j >= 0; j--) {
                boolean typee = true;
                if (ascending) {
                    typee = t < intArray[j];
                } else {
                    typee = t > intArray[j];
                }
                if (!typee) break;
                intArray[j + 1] = intArray[j];
                y = j;
            }

            if (y > -1) intArray[y] = t;
        }
    }

    /**
     * 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     *
     * @param intArray  待排序的数组
     * @param ascending 排序方式：true：升序，false：降序
     */
    public static void bubbleSort(int[] intArray, boolean ascending) {
        for (int e = 0; e < intArray.length - 1; e++) {
            for (int r = 0; r < intArray.length - 1; r++) {
                boolean typee = true;
                if (ascending) {
                    typee = intArray[r] > intArray[r + 1];
                } else {
                    typee = intArray[r] < intArray[r + 1];
                }
                if (typee) {
                    int t = intArray[r];
                    intArray[r] = intArray[r + 1];
                    intArray[r + 1] = t;
                }
            }
        }
    }

    /**
     * 递归快速排序法
     * 通过一趟排序将待排序记录分割成独立的两部分，
     * 其中一部分记录的关键字均比另一部分关键字小，则分别对这两部分继续进行排序，直到整个序列有序。
     * <p>
     * （采用分治的策略，将排序细分化，这样下来，整个快速排序的时间复杂度为O(N*logN) ,
     * 比那些常规的冒泡排序，选择排序，都要快。但是这里存在了一个弊端，就是如果目标数组
     * 是一个很大的数组，由于递归的原因，可能会引发栈内存溢出的问题）
     *
     * @param intArray  待排序的数组
     * @param start     开始位置
     * @param end       结束位置
     * @param ascending 排序方式：true：升序，false：降序
     */
    public static void recursiveSort(int[] intArray, int start, int end, boolean
            ascending) {
        int tmp = intArray[start];
        int i = start;

        if (ascending) {
            for (int j = end; i < j; ) {
                while (intArray[j] > tmp && i < j) {
                    j--;
                }
                if (i < j) {
                    intArray[i] = intArray[j];
                    i++;
                }
                for (; intArray[i] < tmp && i < j; i++) {
                    ;
                }
                if (i < j) {
                    intArray[j] = intArray[i];
                    j--;
                }
            }
        } else {
            for (int j = end; i < j; ) {
                while (intArray[j] < tmp && i < j) {
                    j--;
                }
                if (i < j) {
                    intArray[i] = intArray[j];
                    i++;
                }
                for (; intArray[i] > tmp && i < j; i++) {
                    ;
                }
                if (i < j) {
                    intArray[j] = intArray[i];
                    j--;
                }
            }
        }

        intArray[i] = tmp;
        if (start < i - 1) {
            recursiveSort(intArray, start, i - 1, ascending);
        }
        if (end > i + 1) {
            recursiveSort(intArray, i + 1, end, ascending);
        }
    }

    /**
     * 栈快速排序
     * 对数组intArray进行排序
     *
     * @param intArray  待排序的数组
     * @param ascending 升序
     */
    public static void fastStackSort(int[] intArray, boolean ascending) {
        Stack<Integer> sa = new Stack<Integer>();
        sa.push(0);
        sa.push(intArray.length - 1);
        while (!sa.isEmpty()) {
            int end = ((Integer) sa.pop()).intValue();
            int start = ((Integer) sa.pop()).intValue();
            int i = start;
            int j = end;
            int tmp = intArray[i];
            if (ascending) {
                while (i < j) {
                    while (intArray[j] > tmp && i < j) {
                        j--;
                    }
                    if (i < j) {
                        intArray[i] = intArray[j];
                        i++;
                    }
                    for (; intArray[i] < tmp && i < j; i++) {
                        ;
                    }
                    if (i < j) {
                        intArray[j] = intArray[i];
                        j--;
                    }
                }
            } else {
                while (i < j) {
                    while (intArray[j] < tmp && i < j) {
                        j--;
                    }
                    if (i < j) {
                        intArray[i] = intArray[j];
                        i++;
                    }
                    for (; intArray[i] > tmp && i < j; i++) {
                        ;
                    }
                    if (i < j) {
                        intArray[j] = intArray[i];
                        j--;
                    }
                }
            }
            intArray[i] = tmp;
            if (start < i - 1) {
                sa.push(Integer.valueOf(start));
                sa.push(Integer.valueOf(i - 1));
            }
            if (end > i + 1) {
                sa.push(Integer.valueOf(i + 1));
                sa.push(Integer.valueOf(end));
            }
        }
    }

    /**
     * 将数组颠倒
     *
     * @param objects 给定数组
     * @return 颠倒后的数组
     */
    public static Object[] upsideDown(Object[] objects) {
        int length = objects.length;
        Object tem;
        for (int w = 0; w < length / 2; w++) {
            tem = objects[w];
            objects[w] = objects[length - 1 - w];
            objects[length - 1 - w] = tem;
            tem = null;
        }
        return objects;
    }


    /**
     * 将数组颠倒
     *
     * @param intArray 给定数组
     * @return 颠倒后的数组
     */
    public static int[] upsideDown(int[] intArray) {
        int length = intArray.length;
        int tem = 0;
        for (int w = 0; w < length / 2; w++) {
            tem = intArray[w];
            intArray[w] = intArray[length - 1 - w];
            intArray[length - 1 - w] = tem;
            tem = 0;
        }
        return intArray;
    }

    /**
     * Inteher数组转换成int数组
     */
    public static int[] integersToInts(Integer[] integers) {
        int[] ints = new int[integers.length];
        for (int w = 0; w < integers.length; w++) {
            ints[w] = integers[w];
        }
        return ints;
    }

    /**
     * 将给定的数组转换成字符串
     *
     * @param integers     给定的数组
     * @param startSymbols 开始符号
     * @param separator    分隔符
     * @param endSymbols   结束符号
     * @return 例如开始符号为"{"，分隔符为", "，结束符号为"}"，那么结果为：{1, 2, 3}
     */
    public static String toString(int[] integers, String startSymbols, String separator, String
            endSymbols) {
        boolean addSeparator = false;
        StringBuffer sb = new StringBuffer();
        //如果开始符号不为null且不空
        if (!StringUtils.isEmpty(startSymbols)) {
            sb.append(startSymbols);
        }
        //循环所有的对象
        for (int object : integers) {
            //如果需要添加分隔符
            if (addSeparator) {
                sb.append(separator);
                addSeparator = false;
            }
            sb.append(object);
            addSeparator = true;
        }

        //如果结束符号不为null且不空
        if (!StringUtils.isEmpty(endSymbols)) {
            sb.append(endSymbols);
        }
        return sb.toString();
    }

    /**
     * 将给定的数组转换成字符串
     *
     * @param integers  给定的数组
     * @param separator 分隔符
     * @return 例如分隔符为", "那么结果为：1, 2, 3
     */
    public static String toString(int[] integers, String separator) {
        return toString(integers, null, separator, null);
    }

    /**
     * 将给定的数组转换成字符串，默认分隔符为", "
     *
     * @param integers 给定的数组
     * @return 例如：1, 2, 3
     */
    public static String toString(int[] integers) {
        return toString(integers, null, ", ", null);
    }

    /**
     * 将给定的数组转换成字符串
     *
     * @param objects      给定的数组
     * @param startSymbols 开始符号
     * @param separator    分隔符
     * @param endSymbols   结束符号
     * @return 例如开始符号为"{"，分隔符为", "，结束符号为"}"，那么结果为：{1, 2, 3}
     */
    public static String toString(Object[] objects, String startSymbols, String separator, String
            endSymbols) {
        boolean addSeparator = false;
        StringBuffer sb = new StringBuffer();
        //如果开始符号不为null且不空
        if (StringUtils.isEmpty(startSymbols)) {
            sb.append(startSymbols);
        }

        //循环所有的对象
        for (Object object : objects) {
            //如果需要添加分隔符
            if (addSeparator) {
                sb.append(separator);
                addSeparator = false;
            }
            sb.append(object);
            addSeparator = true;
        }

        //如果结束符号不为null且不空
        if (StringUtils.isEmpty(endSymbols)) {
            sb.append(endSymbols);
        }
        return sb.toString();
    }

    /**
     * 将给定的数组转换成字符串
     *
     * @param objects   给定的数组
     * @param separator 分隔符
     * @return 例如分隔符为", "那么结果为：1, 2, 3
     */
    public static String toString(Object[] objects, String separator) {
        return toString(objects, null, separator, null);
    }

    /**
     * 将给定的数组转换成字符串，默认分隔符为", "
     *
     * @param objects 给定的数组
     * @return 例如：1, 2, 3
     */
    public static String toString(Object[] objects) {
        return toString(objects, null, ", ", null);
    }
}
