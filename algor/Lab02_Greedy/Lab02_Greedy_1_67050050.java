public class Lab02_Greedy_1_67050050 {
    static int minJump(int[] arr){
        int n = arr.length;

        if (n == 1) 
            return 0;
        if (arr[0] == 0 && n > 1)
            return -1;
        int jump = 1, maxReach = arr[0], step = arr[0];

        for (int i = 1; i < n; i++){
            if(i == n -1)
                return jump;
            maxReach = Math.max(maxReach, i + arr[i]);
            step--;
            if(step == 0) {
                jump++;
                if(i >= maxReach)
                    return -1;
                step = maxReach - i;
            }
        }
        return -1;
    }
    // public static void main(String[] args) {
    //     int[][] tests = {
    //         {2,3,1,1,4},
    //         {0,10,20},
    //         {1,3,2,2,1,4,6}
    //     };

    //     for (int[] t : tests) {
    //         System.out.println(minJump(t));
    //     }

    // }

}
