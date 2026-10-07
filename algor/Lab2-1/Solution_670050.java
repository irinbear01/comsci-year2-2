import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution_670050 {

    public int[][] kClosest(int[][] points, int k) {
       // return pq(points, k);   //heap
        return partition_caller(points, k); //quick
    }
    //-------------heap--------------
    private int[][] pq(int [][] points, int K) {
        // expected runtime is O(n log K)
        int [][] ans = new int[K][2];

        PriorityQueue<int[]> heap = new PriorityQueue<int[]>((p1, p2) ->
            p2[0]*p2[0] + p2[1]*p2[1] - (p1[0]*p1[0] + p1[1]*p1[1])
        );

        for (int[] p : points) {
            heap.offer(p);          //ใส่จุดpเข้า heap

            if (heap.size() > K) {
                heap.poll();        //ดึงจุดที่ไกลที่สุดออก
            }
        }

        int index = 0;
        while (!heap.isEmpty()) {
            ans[index++] = heap.poll();
        }

        return ans;
    }
    //-----------quick---------------
    private int[][] partition_caller(int [][] points, int K) {
        // expected average runtime is O(n)
        int left = 0, right = points.length - 1;
        while (left <= right) {
            int pivot = partition(points, left, right);
            if (pivot < K)
                left = pivot + 1;
            else if (K < pivot) 
                right = pivot - 1;
            else 
                break;
        }
        return Arrays.copyOfRange(points, 0, K);
    }

    private int partition(int [][] points, int left, int right) {
        int[] pivot = points[right];
        int storeIndex = left; 

        for (int i = left; i < right; i++) {
            if (compare(points[i], pivot) <= 0) {
                int[] temp = points[i];
                points[i] = points[storeIndex];
                points[storeIndex] = temp;
                storeIndex++;
            }
        }

        int[] temp = points[storeIndex];
        points[storeIndex] = points[right];
        points[right] = temp;

        return storeIndex;
    }

    private int compare(int [] p1, int [] p2) {
        return (p1[0] * p1[0] + p1[1] * p1[1] 
            - p2[0] * p2[0] - p2[1] * p2[1]);
    }

    // public static void main(String[] args) {
    //     Solution_670050 s = new Solution_670050();

    //     int[][] points = {
    //         {3, 3},
    //         {5, -1},
    //         {-2, 4}
    //     };
    //     int k = 2;

    //     System.out.println("Test Heap ");
    //     int[][] ans1 = s.pq(points.clone(), k);
    //     print(ans1);

    //     System.out.println("Test Quick");
    //     int[][] ans2 = s.partition_caller(points.clone(), k);
    //     print(ans2);
    // }

    // private static void print(int[][] arr) {
    //     for (int[] p : arr) {
    //         System.out.println(Arrays.toString(p));
    //     }
    //     System.out.println();
    // }

}
