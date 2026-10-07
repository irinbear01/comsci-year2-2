public class Lab03_DP_670050 {

    static int minOperationsMatrixChain(int[] p, int n) {
        int[][] cost = new int[n][n];
        int i,j,k,L,numOps;

        for (i = 0; i < n; i++) {
            cost[i][i] = 0;
        }

        for (L = 2; L < n; L++) {
            for (i = 1; i < n - L + 1; i++) {
                 j = i + L - 1;
                cost[i][j] = Integer.MAX_VALUE;
                for (k = i; k < j; k++) {
                    numOps = cost[i][k] + cost[k + 1][j] + p[i - 1] * p[k] * p[j];

                    if (numOps < cost[i][j]) {
                        cost[i][j] = numOps;
                    }
                }
            }
        }
        return cost[1][n - 1];
    }

    public static void main(String[] args) {

        // A1 4x10, A2: 10x3, A3: 3x12, A4 12x20
        int[] P = {4, 10, 3, 12, 20};
        System.out.println( minOperationsMatrixChain(P, P.length) );
    }
}
