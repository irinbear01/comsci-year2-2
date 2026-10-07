import java.io.*;

public class Lab8_Police_670050 {

    static final int N = 20;
    static final int TARGET = 191;

    static int[] a = new int[N];

    static boolean seenEven;      
    static boolean oddAfterEven;   
    static boolean evenAfterOdd;    
    static boolean dfs(int i, int sum, boolean seenEvenNow, boolean oddAfterEvenNow, boolean evenAfterOddNow) {
        if (sum > TARGET) return false;

        if (sum == TARGET) {
            return evenAfterOddNow;
        }

        if (i == N) return false;
        if (dfs(i + 1, sum, seenEvenNow, oddAfterEvenNow, evenAfterOddNow)) return true;
        int v = a[i];
        boolean se = seenEvenNow;
        boolean oe = oddAfterEvenNow;
        boolean eo = evenAfterOddNow;

        if ((v & 1) == 0) {
            if (se && oe) eo = true;  
            se = true;       
        } else {
            if (se) oe = true;       
        }

        return dfs(i + 1, sum + v, se, oe, eo);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int cnt = 0;
        while (cnt < N) {
            Integer x = fs.nextIntOrNull();
            if (x == null) break;
            a[cnt++] = x;
        }

        if (cnt < N) {
            System.out.print("Free");
            return;
        }

        boolean ok = dfs(0, 0, false, false, false);
        System.out.print(ok ? "Police Freeze" : "Free");
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