import java.util.Scanner;

public class Solution {
    public static int partition (int[] arr, int l, int h) {
        int pivotPosition = l;
        for(int i=l+1; i<=h; i++){
            if(arr[pivotPosition] > arr[i]) {
                int temp = arr[pivotPosition+1];
                arr[pivotPosition+1] = arr[i];
                arr[i] = temp;

                temp = arr[pivotPosition+1];
                arr[pivotPosition+1] = arr[pivotPosition];
                arr[pivotPosition] = temp;

                pivotPosition++;
            }
        }
        return pivotPosition;
    }

    public static void quicksort(int[] arr, int l, int h) {
        if(l < h) {
            int pivot = partition(arr, l, h);
            quicksort(arr, l, pivot-1);
            quicksort(arr, pivot+1, h);
        }
    }

    public static int[] scanArray() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter elements in Array : ");
        int n=sc.nextInt(), arr[] = new int[n];
        System.out.print("Enter elements space seperated : ");
        for(int i=0; i<n; i++) arr[i] = sc.nextInt();
        System.out.println();
        return arr;
    }
    
    public static void printArray(int[] arr) {
        System.out.print("Array : ");
        for(int i=0; i<arr.length-1; i++) System.out.print(arr[i] + ", ");
        System.out.print(arr[arr.length-1]+"\n");
    }

    public static void main(String args[]) {
        int[] arr = scanArray();
        quicksort(arr,0,arr.length-1);
        printArray(arr);
    }
}
