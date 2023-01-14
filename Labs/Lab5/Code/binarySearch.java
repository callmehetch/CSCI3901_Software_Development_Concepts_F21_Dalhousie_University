import java.util.Arrays;

class binarySearch {
    int binarySearch(int arr[], int x)
    {
        int l = 0, r = arr.length - 1;

        //[PRECONDITION] - To check if passed array is sorted and not empty 
        assert arr.length >0: "Empty Array Passed";
        assert isItSorted(arr) : "Array is altered - Not sorted";
        assert l<=r: "l should be less than r";        
        while (l <= r) {
            int m = l + (r - l) / 2;
            //[LOOP-INVARIANT]: To check if mid (supposed to be the index of x 
            //if present in the array) is actually present between l and r for every loop.
            assert m>=0: "Mid can't be less than zero";
            assert l<=r: "l is greater than r";
            assert l<=m: "Mid is not inbetween l and r";
            assert m<=r:"Mid is not inbetween l and r";
            if (arr[m] == x)
                return m;
            if (arr[m] < x)
                l = m + 1;
            else
                r = m - 1;
        }
        //[POSTCONDITION] - check if we have checked complete loop
        assert l>r: "Didn't run full loop";
        assert isItSorted(arr): "Loop is not in sorted condition after loop";

        return -1;
    }
 
    public static void main(String args[])
    {
        binarySearch ob = new binarySearch();
        int arr[] = { 2, 3, 4, 10, 40 };
        Arrays.sort(arr);
        int n = arr.length;
        assert n>0: "Empty Array Given";
        int x = 77;
        int result = ob.binarySearch(arr, x);
        if (result == -1)
            System.out.println("Element not present");
        else
            System.out.println("Element found at " + "index " + result);
    }


    //Method to check if an array is sorted
    boolean isItSorted(int arr[]) {
        int i;
        for (i = 0; i < arr.length-2; i++) { 
            if (arr[i] <= arr[i+1]) {
                return true;
            }
        }
        return false;
    }
}