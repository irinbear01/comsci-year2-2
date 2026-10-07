import java.util.*;

public class Solution {
    // --task1-- Subset Sum (Backtracking)
    public void task1_subsetSum(int[] arr, int k) {
        Set<List<Integer>> set = new HashSet<>();
        subsetEqualsTarget(arr, k, 0, 0, new LinkedList<>(), set);

        System.out.println("--task1-- answers (sum = " + k + ")");
        for (List<Integer> one_case : set) {
            System.out.println(one_case);
        }
        System.out.println("bye task1");
    }

    private void subsetEqualsTarget(int[] arr, int k, int i, int sum,
                                    LinkedList<Integer> curState,
                                    Set<List<Integer>> set) {

        if (k == 0) return;    
        if (sum > k) return;   
        if (sum == k) {        
            set.add(new LinkedList<>(curState));
            return;
        }
        if (i == arr.length) return;

        curState.add(arr[i]);
        subsetEqualsTarget(arr, k, i + 1, sum + arr[i], curState, set);

        curState.removeLast();

        subsetEqualsTarget(arr, k, i + 1, sum, curState, set);
    }

    // --task2-- Maze Find Path (DLRU)
    static String direction = "DLRU";

    static int[] rowwise = { 1, 0, 0, -1 };
    static int[] colwise = { 0, -1, 1, 0 };

    private boolean isValid(int row, int col, int n, int[][] maze) {
        return row >= 0 && col >= 0 && row < n && col < n && maze[row][col] == 1;
    }

    public List<String> task2_findAllPaths(int[][] maze) {
        int n = maze.length;
        List<String> result = new ArrayList<>();
        StringBuilder currentPath = new StringBuilder();

        if (n == 0) return result;
        if (maze[0][0] != 0 && maze[n - 1][n - 1] != 0) {
            findPath(0, 0, maze, n, result, currentPath);
        }
        return result;
    }

    public void findPath(int row, int col, int[][] maze,
                         int n, List<String> ans, StringBuilder currentPath) {

        if (row == n - 1 && col == n - 1) {
            ans.add(currentPath.toString());
            return;
        }

        maze[row][col] = 0;

        for (int dir = 0; dir < 4; dir++) {
            int newRow = row + rowwise[dir];
            int newCol = col + colwise[dir];

            if (isValid(newRow, newCol, n, maze)) {
                currentPath.append(direction.charAt(dir));
                findPath(newRow, newCol, maze, n, ans, currentPath);
                currentPath.deleteCharAt(currentPath.length() - 1);
            }
        }
        maze[row][col] = 1;
    }

//     public static void main(String[] args) {

//         Solution s = new Solution();

//         // test task1
//         int[] arr = { 1, 2, 6, 1 };
//         int k = 3;
//         s.task1_subsetSum(arr, k);

//         System.out.println();

//         // test task2
//         int[][] maze = {
//                 { 1, 0, 0, 0 }, // _ x x x
//                 { 1, 1, 0, 1 }, // _ _ x _
//                 { 1, 1, 0, 0 }, // _ _ x x
//                 { 0, 1, 1, 1 }  // x _ _ _
//         };

//         List<String> paths = s.task2_findAllPaths(maze);
//         System.out.println("--task2-- paths (DLRU):");
//         for (String p : paths) {
//             System.out.println(p);
//         }
//         System.out.println("bye task2");
//     }
}