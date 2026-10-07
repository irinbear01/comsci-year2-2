public class StrassenMultiplication {

    // TODO 1: Start with LEAF_SIZE = 64, then experiment with different values
    private static final int LEAF_SIZE = 64;

    /**
     * Strassen matrix multiplication (recursive).
     * Assumption: A and B are n x n, n is power of 2.
     */
    public int[][] strassen(int[][] A, int[][] B) {
        int n = A.length;

        // Base case: use standard multiplication for small matrices
        if (n <= LEAF_SIZE) {
            return standardMultiply(A, B);
        }

        int mid = n / 2;

        // Create submatrices for A
        int[][] A11 = new int[mid][mid];
        int[][] A12 = new int[mid][mid];
        int[][] A21 = new int[mid][mid];
        int[][] A22 = new int[mid][mid];

        // Create submatrices for B
        int[][] B11 = new int[mid][mid];
        int[][] B12 = new int[mid][mid];
        int[][] B21 = new int[mid][mid];
        int[][] B22 = new int[mid][mid];

        // Split A and B into 4 blocks each
        split(A, A11, A12, A21, A22);
        split(B, B11, B12, B21, B22);

        // Compute the 7 intermediate products (M1...M7) using Strassen formulas
        // M1 = (A11 + A22) * (B11 + B22)
        int[][] M1 = strassen(add(A11, A22), add(B11, B22));

        // TODO 2: Implement M2 through M7 using Strassen's formulas
        // M2 = (A21 + A22) * B11
        int[][] M2 = strassen(add(A21, A22), B11);

        // M3 = A11 * (B12 - B22)
        int[][] M3 = strassen(A11, subtract(B12, B22));

        // M4 = A22 * (B21 - B11)
        int[][] M4 = strassen(A22, subtract(B21, B11));

        // M5 = (A11 + A12) * B22
        int[][] M5 = strassen(add(A11, A12), B22);

        // M6 = (A21 - A11) * (B11 + B12)
        int[][] M6 = strassen(subtract(A21, A11), add(B11, B12));

        // M7 = (A12 - A22) * (B21 + B22)
        int[][] M7 = strassen(subtract(A12, A22), add(B21, B22));

        // Reconstruct result submatrices C11, C12, C21, C22
        // C11 = M1 + M4 - M5 + M7
        int[][] C11 = add(subtract(add(M1, M4), M5), M7);

        // TODO 3: Implement C12, C21, C22 using reconstruction formulas
        // C12 = M3 + M5
        int[][] C12 = add(M3, M5);

        // C21 = M2 + M4
        int[][] C21 = add(M2, M4);

        // C22 = M1 - M2 + M3 + M6
        int[][] C22 = add(add(subtract(M1, M2), M3), M6);

        // Combine the 4 blocks into one result matrix
        return combine(C11, C12, C21, C22);
    }

    // Helper Methods 

    // Matrix addition R = X + Y
    private int[][] add(int[][] X, int[][] Y) {
        int n = X.length;
        int[][] R = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                R[i][j] = X[i][j] + Y[i][j];
            }
        }
        return R;
    }

    // Matrix subtraction R = X - Y 
    private int[][] subtract(int[][] X, int[][] Y) {
        int n = X.length;
        int[][] R = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                R[i][j] = X[i][j] - Y[i][j];
            }
        }
        return R;
    }

    // Standard matrix multiplication 
    private int[][] standardMultiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    //split
    private void split(int[][] P, int[][] P11, int[][] P12, int[][] P21, int[][] P22) {
        int n = P.length;
        int mid = n / 2;
        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                P11[i][j] = P[i][j];
                P12[i][j] = P[i][j + mid];
                P21[i][j] = P[i + mid][j];
                P22[i][j] = P[i + mid][j + mid];
            }
        }
    }

    //combin
    private int[][] combine(int[][] C11, int[][] C12, int[][] C21, int[][] C22) {
        int mid = C11.length;
        int n = mid * 2;
        int[][] C = new int[n][n];

        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                C[i][j] = C11[i][j];
                C[i][j + mid] = C12[i][j];
                C[i + mid][j] = C21[i][j];
                C[i + mid][j + mid] = C22[i][j];
            }
        }
        return C;
    }

//     public static void main(String[] args) {
//         StrassenMultiplication sm = new StrassenMultiplication();

//         // Example from your Exercise 1 
//         int[][] A = {
//                 { 1, -3 },
//                 { 5, -7 }
//         };

//         int[][] B = {
//                 { 2, -4 },
//                 { -6, -8 }
//         };

//         int[][] C = sm.strassen(A, B);

//         System.out.println("Result C = A x B:");
//         printMatrix(C);
//         // 20 20
//         // 52 36
//     }

//     private static void printMatrix(int[][] M) {
//         for (int[] row : M) {
//             for (int v : row) {
//                 System.out.print(v + " ");
//             }
//             System.out.println();
//         }
//     }
// }
