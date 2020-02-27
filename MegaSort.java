// package Practice05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class MegaSort {

    // File name used for sorting
    static final String FILENAME = "1m-ints.txt";

    /*
    megaSort:
    Takes in the name of a file, and if found
    adds the digit to array list
     */
    public static void megaSort(String fileName){

        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            ArrayList<Integer> arrayList = new ArrayList<>();

            System.out.println("File found");

            // copies integers from file into array list
            while(reader.hasNextLine()){
                arrayList.add(reader.nextInt());
            }

            // sort array using Radix Sort
            int max_leading_zeros = radixSort(arrayList);

            // print out sorted array
            for(int n: arrayList){
                System.out.printf("%0" + max_leading_zeros + "d %n", n);
            }

            reader.close();

        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    /*
    maxValue:
    Function to return highest value in array list.
     */
    private static int maxValue(ArrayList<Integer> arr){

        int temp = arr.get(0);
        for (int value : arr)
            temp = Math.max(value, temp);
        return temp;
    }

    /*
    countSort:
    Sorting algorithm based on count sort
     */
    private static void countSort(ArrayList<Integer> arr, int exp){

        int n = arr.size();

        int[] out = new int[n];  // out array
        int[] count = new int[10];

        // fill count array with 0s
        Arrays.fill(count, 0);

        // store number of times number appears
        for (int num : arr)
            count[(num / exp) % 10]++;

        // change position so digit is in actual position
        for(int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build out array
        for(int i = n - 1; i >= 0; i--){
            out[count[ (arr.get(i)/exp)%10] - 1] = arr.get(i);
            count[ (arr.get(i)/exp)%10 ]--;
        }

        // Copy elements from out array into original array
        for(int i = 0; i < n; i++)
            arr.set(i, out[i]);

    }

    /*
    radixSort:
    Function that sorts array using Radix Sort
     */
    private static int radixSort(ArrayList<Integer> arr){

        // Find the digit with the max value
        int max = maxValue(arr);

        int max_leading_zeros = 0;
        int temp = max;

        while( temp > 0){
            temp = temp/10;
            max_leading_zeros++;
        }

        // do count sort for every digit
        for(int exp = 1; max/exp > 0; exp *= 10)
            countSort(arr, exp);

        return max_leading_zeros;
    }

    // main driver for mega sort
    public static void main(String[] args){
        megaSort(FILENAME);
    }
}
