public class LabDP1_WoodCutter {
    static int[] price = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

    static void woodCutter(int N) {
        int[] dp = new int[N + 1]; 
        int[] cut = new int[N + 1]; 

        for (int i = 1; i <= N; i++) {
            dp[i] = Integer.MIN_VALUE;
            cut[i] = 1;

            for (int j = 1; j <= Math.min(i, 10); j++) {
                int candidate = dp[i - j] + price[j];

                if (candidate > dp[i]) {
                    dp[i] = candidate;
                    cut[i] = j;
                } else if (candidate == dp[i]) {

                    int currentJ = cut[i];
                    int currentDiff = Math.abs(currentJ - (i - currentJ));
                    int newDiff = Math.abs(j - (i - j));

                    if (newDiff < currentDiff) {
                        cut[i] = j;
                    } else if (newDiff == currentDiff) {
                        int currentMax = Math.max(currentJ, i - currentJ);
                        int newMax = Math.max(j, i - j);
                        if (newMax < currentMax) {
                            cut[i] = j;
                        }
                    }
                }
            }
        }

        int[] count = new int[11];
        int length = N;
        int totalPieces = 0;

        while (length > 0) {
            int j = cut[length];
            count[j]++;
            totalPieces++;
            length -= j;
        }

        double laborRate;
        if (totalPieces <= 10) laborRate = 0.0;
        else if (totalPieces <= 50) laborRate = 0.05;
        else if (totalPieces <= 100) laborRate = 0.10;
        else laborRate = 0.15;

        int netIncome = (int) (dp[N] * (1.0 - laborRate));

        System.out.println(netIncome);
        System.out.println(totalPieces);
        for (int i = 1; i <= 10; i++) {
            System.out.print(count[i] + (i == 10 ? "" : " "));
        }
        System.out.println();
    }
    // public static void main(String[] args) {
    //     woodCutter(1);
    //     woodCutter(7);
    //     woodCutter(9);
    //     woodCutter(20);
    //     woodCutter(998);
    // }
}

