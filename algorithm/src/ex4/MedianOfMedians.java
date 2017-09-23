package ex4;

import java.util.Arrays;

public class MedianOfMedians {
    /*public static void findMedian(int arr[]) {
        int median = findMedianUtil(arr, (arr.length) / 2 + 1, 0, arr.length - 1);
        System.out.println("메디안 : " + median);
    }

    private static int findMedianUtil(int arr[], int k, int low, int high) {
        // Uncomment this if you want to print the current subArray being processed/searched
        //printArray(arr, low, high);

        if (low == high) {
            return arr[low];
        }

        // sort the mth largest element in the given array
        int m = partition(arr, low, high);

        // Adjust position relative to the current subarray being processed
        int length = m - low + 1;

        // If mth element is the median, return it
        if (length == k) {
            return arr[m];
        }

        // If mth element is greater than median, search in the left subarray
        if (length > k) {
            return findMedianUtil(arr, k, low, m - 1);
        }
        // otherwise search in the right subArray
        else {
            return findMedianUtil(arr, k - length, m + 1, high);
        }

    }


    private static int partition(int arr[], int low, int high) {
        int pivotValue = getPivotValue(arr, low, high);

        while (low < high) {
            while (arr[low] < pivotValue) {
                low++;
            }

            while (arr[high] > pivotValue) {
                high--;
            }

            if (arr[low] == arr[high]) {
                low++;
            } else if (low < high) {
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
            }

        }
        return high;
    }

    private static int getPivotValue(int arr[], int low, int high) {
        if (high - low + 1 <= 9) {
            Arrays.sort(arr);
            return arr[arr.length / 2];
        }
        int temp[] = null;

        int medians[] = new int[(int) Math.ceil((double) (high - low + 1) / 5)];
        int medianIndex = 0;

        while (low <= high) {
            temp = new int[Math.min(5, high - low + 1)];

            for (int j = 0; j < temp.length && low <= high; j++) {
                temp[j] = arr[low];
                low++;
            }
            Arrays.sort(temp);
            medians[medianIndex] = temp[temp.length / 2];
            medianIndex++;
        }
        return getPivotValue(medians, 0, medians.length - 1);
    }*/

    public static void main(String args[]) {
        final int ELEMENTS = 400;
        int arr[] = new int[ELEMENTS];

        for (int i = 0; i < ELEMENTS; i++) {
            arr[i] = (int) (2000 * Math.random());
        }

        System.out.println("[임의로 생성된 배열]");
        printArray(arr, 0, ELEMENTS);
        System.out.println();

//        findMedian(arr);
        System.out.println();

        java.util.Arrays.sort(arr);
        System.out.println("[정리된 배열]");
        printArray(arr, 0, ELEMENTS);
        final int FIND = 300;
        System.out.println(FIND + "번째로 작은 수 : " + arr[FIND - 1]);
        System.out.println();
    }

    private static void printArray(int arr[], int low, int high) {
        int idx = 0;
        for (int i = low; i < high; i++) {
            idx++;
            System.out.printf("%4d", arr[i]);
            if(idx % 10 == 0)
                System.out.println();
            else
                System.out.print(", ");
        }
    }


}