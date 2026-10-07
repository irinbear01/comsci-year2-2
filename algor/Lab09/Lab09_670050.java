import java.util.*;

public class Lab09_670050 {

    static int capacity = 10;
    static int[] items = {2, 8, 1, 9, 9, 5, 2, 8, 9, 7};

    static void printInputLine(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    static void printBinSummary(List<List<int[]>> bins) {
        for (int b = 0; b < bins.size(); b++) {
            System.out.print("Bin" + (b + 1) + " : ");
            List<int[]> bin = bins.get(b);

            for (int i = 0; i < bin.size(); i++) {
                int[] pair = bin.get(i);
                if (i > 0) System.out.print(", ");
                System.out.print(pair[0] + "(" + pair[1] + ")");
            }
            System.out.println();
        }
        System.out.println();
    }
    // FIRST FIT
    public static void firstFit(int[] arr) {
        System.out.println("First Fit");
        printInputLine(arr);

        List<List<int[]>> bins = new ArrayList<>();
        int[] remain = new int[0];

        for (int i = 0; i < arr.length; i++) {
            int sIndex = i + 1;
            int size = arr[i];

            int chosenBin = -1;
            for (int b = 0; b < bins.size(); b++) {
                if (remain[b] >= size) {
                    chosenBin = b;
                    break; 
                }
            }

            if (chosenBin == -1) {
                bins.add(new ArrayList<>());

                int[] newRemain = new int[bins.size()];
                for (int k = 0; k < remain.length; k++) newRemain[k] = remain[k];
                newRemain[bins.size() - 1] = capacity;
                remain = newRemain;

                chosenBin = bins.size() - 1;
            }

            bins.get(chosenBin).add(new int[]{sIndex, size});
            remain[chosenBin] -= size;

            System.out.println("S" + sIndex + " = " + size + " in Bin " + (chosenBin + 1));
        }

        printBinSummary(bins);
    }

    // BEST FIT
    public static void bestFit(int[] arr) {
        System.out.println("Best Fit");
        printInputLine(arr);

        List<List<int[]>> bins = new ArrayList<>();
        int[] remain = new int[0];

        for (int i = 0; i < arr.length; i++) {
            int sIndex = i + 1;
            int size = arr[i];

            int chosenBin = -1;
            int bestRemainAfter = Integer.MAX_VALUE;

            for (int b = 0; b < bins.size(); b++) {
                if (remain[b] >= size) {
                    int after = remain[b] - size;
                    if (after < bestRemainAfter) {
                        bestRemainAfter = after;
                        chosenBin = b;
                    }
                }
            }

            if (chosenBin == -1) {
                bins.add(new ArrayList<>());
                int[] newRemain = new int[bins.size()];
                for (int k = 0; k < remain.length; k++) newRemain[k] = remain[k];
                newRemain[bins.size() - 1] = capacity;
                remain = newRemain;

                chosenBin = bins.size() - 1;
            }

            bins.get(chosenBin).add(new int[]{sIndex, size});
            remain[chosenBin] -= size;

            System.out.println("S" + sIndex + " = " + size + " in Bin " + (chosenBin + 1));
        }

        printBinSummary(bins);
    }


    // FFD
    public static void ffd(int[] arr) {
        System.out.println("FFD (First-fit decreasing)");

        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < sorted.length / 2; i++) {
            int tmp = sorted[i];
            sorted[i] = sorted[sorted.length - 1 - i];
            sorted[sorted.length - 1 - i] = tmp;
        }

        printInputLine(sorted);

        List<List<int[]>> bins = new ArrayList<>();
        int[] remain = new int[0];

        for (int i = 0; i < sorted.length; i++) {
            int sIndex = i + 1;  
            int size = sorted[i];

            int chosenBin = -1;
            for (int b = 0; b < bins.size(); b++) {
                if (remain[b] >= size) {
                    chosenBin = b;
                    break; 
                }
            }

            if (chosenBin == -1) {
                bins.add(new ArrayList<>());
                int[] newRemain = new int[bins.size()];
                for (int k = 0; k < remain.length; k++) newRemain[k] = remain[k];
                newRemain[bins.size() - 1] = capacity;
                remain = newRemain;

                chosenBin = bins.size() - 1;
            }

            bins.get(chosenBin).add(new int[]{sIndex, size});
            remain[chosenBin] -= size;

            System.out.println("S" + sIndex + " = " + size + " in Bin " + (chosenBin + 1));
        }

        printBinSummary(bins);
    }

    public static void jobScheduling(int[] arr, int m) {
        System.out.println("=== Job Scheduling (sorted desc) ===");

        int[] jobs = arr.clone();
        Arrays.sort(jobs);
        for (int i = 0; i < jobs.length / 2; i++) {
            int temp = jobs[i];
            jobs[i] = jobs[jobs.length - 1 - i];
            jobs[jobs.length - 1 - i] = temp;
        }

        int[] computers = new int[m];

        for (int job : jobs) {
            int minIndex = 0;

            for (int i = 1; i < m; i++) {
                if (computers[i] < computers[minIndex]) {
                    minIndex = i;
                }
            }

            computers[minIndex] += job;
        }

        for (int i = 0; i < m; i++) {
            System.out.println("Computer " + (i + 1) + " Load = " + computers[i]);
        }

        int maxtime = Arrays.stream(computers).max().getAsInt();
        System.out.println("Max time = " + maxtime);
    }

    public static void printBins(List<List<Integer>> bins) {
        for (int i = 0; i < bins.size(); i++) {
            System.out.println("Bin " + (i + 1) + " : " + bins.get(i));
        }
        System.out.println("Total Bins Used = " + bins.size());
        System.out.println();
    }

    public static void main(String[] args) {

        firstFit(items);
        bestFit(items);
        ffd(items);

        jobScheduling(items, 4);
    }
}