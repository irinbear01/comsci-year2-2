public class Subsetsum_670050 {
    static boolean subsetSum(int[] nums, int sum) {
        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= sum; s++) {
                dp[i][s] = dp[i - 1][s];
                if (s >= nums[i - 1]) {
                    dp[i][s] = dp[i][s] || dp[i - 1][s - nums[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }
    // public static void main(String[] args) {
    //     int[] nums1 = {1, 4, 3, 2, 7};
    //     int sum1 = 9;
    //     System.out.println("Result = " + subsetSum(nums1, sum1));

    //     int[] nums2 = {3, 34, 4, 12, 5, 2};
    //     int sum2 = 9;
    //     System.out.println("Result = " + subsetSum(nums2, sum2));

    //     int[] nums3 = {3, 34, 4, 12, 5, 2};
    //     int sum3 = 30;
    //     System.out.println("Result = " + subsetSum(nums3, sum3));
    // }
}
