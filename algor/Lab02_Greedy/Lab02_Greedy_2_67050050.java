import java.util.Arrays;

public class Lab02_Greedy_2_67050050 {
    static int findKaykai(int arr[]) {
        int n = arr.length;
        if (n == 1)
            return arr[0];
        if (n == 0)
            return -1;
        Arrays.sort(arr);
        int countKaykai = 0, min = 0, max = 0;
        
        for (int i = 0; i < n; i++) {
            boolean isUnique = false;

            if (i == 0) {
                if (arr[0] != arr[1])
                    isUnique = true;
            }
            else if (i == n-1) {
                if (arr[n-1] != arr[n-2])
                    isUnique = true;
            }
            else
                if (arr[i] != arr[i+1] && arr[i]!= arr[i-1])
                    isUnique = true;
            if (isUnique) {
                countKaykai++;
                if(countKaykai == 1)
                    min=max=arr[i];
                else if(arr[i]>max)
                    max = arr[i];
                else if(arr[i]<min)
                    min = arr[i];
            }
        }
        if (countKaykai == 0)
            return -1;
        if (countKaykai == 1)
            return min;
        return min + max;
    }

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     //ใส่จำนวนตัวเลขก่อนค่อยใส่ตัวเลข
    //     int n = sc.nextInt();
    //     int[] arr = new int[n];

    //     for (int i = 0; i < n; i++) {
    //         arr[i] = sc.nextInt();
    //     }

    //     System.out.println(findKaykai(arr));
    // }
}
