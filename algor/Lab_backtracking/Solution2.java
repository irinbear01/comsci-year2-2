import java.io.*;

public class Solution2 {

    static final int N = 25;
    static final int K = 8;
    static final int TARGET = 888;

    public String task(int[] arr) {
        return bt(arr, 0, 0, 0) ? "Pass" : "All Evil";
    }

    private boolean bt(int[] arr, int i, int sum, int chosen) {
        if (sum > TARGET) return false;
        if (chosen == K) return sum == TARGET;
        if (i == arr.length) return false;
        if (chosen + (arr.length - i) < K) return false;

        if (bt(arr, i + 1, sum + arr[i], chosen + 1)) return true;
        return bt(arr, i + 1, sum, chosen);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int[] nums = new int[N];
        int cnt = 0;

        while (cnt < N) {
            Integer v = fs.nextIntOrNull();
            if (v == null) break;
            nums[cnt++] = v;
        }

        if (cnt == 0) {
            return;
        }

        if (cnt < N) {
            System.out.print("All Evil");
            return;
        }

        Solution2 s = new Solution2();
        System.out.print(s.task(nums));
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) { this.in = in; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        Integer nextIntOrNull() throws IOException {
            int c;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
                if (c == -1) return null;
            }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
                if (c == -1) break;
            }
            return val * sign;
        }
    }
}