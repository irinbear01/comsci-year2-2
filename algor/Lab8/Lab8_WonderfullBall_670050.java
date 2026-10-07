import java.io.*;

public class Lab8_WonderfullBall_670050 {

    static final int N = 30;
    static final int MIN_S = -60;
    static final int MAX_S = 90;
    static final int OFFSET = 60;
    static final int RANGE = MAX_S - MIN_S + 1;

    static int scoreOf(char c) {
        if (c == 'R') return 3;
        if (c == 'G') return 2;
        if (c == 'B') return -1;
        return -2;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String s = fs.nextTokenOrNull();
        if (s == null) return;

        StringBuilder sb = new StringBuilder();
        while (sb.length() < N && s != null) {
            sb.append(s.trim());
            if (sb.length() >= N) break;
            s = fs.nextTokenOrNull();
        }
        String str = sb.toString();
        if (str.length() < N) {
            System.out.print(0);
            return;
        }

        int[] vals = new int[N];
        for (int i = 0; i < N; i++) vals[i] = scoreOf(str.charAt(i));

        long[][] dp = new long[N + 1][RANGE];
        dp[0][0 + OFFSET] = 1;

        for (int v : vals) {
            for (int size = N - 1; size >= 0; size--) {
                for (int si = 0; si < RANGE; si++) {
                    long ways = dp[size][si];
                    if (ways == 0) continue;

                    int newScore = (si - OFFSET) + v;
                    if (newScore < MIN_S || newScore > MAX_S) continue;

                    dp[size + 1][newScore + OFFSET] += ways;
                }
            }
        }

        long ans = 0;
        for (int size = 1; size <= N; size++) {
            for (int score = MIN_S; score <= MAX_S; score++) {
                long ways = dp[size][score + OFFSET];
                if (ways == 0) continue;
                if (score >= 0 && score > size && ((score & 1) == (size & 1))) {
                    ans += ways;
                }
            }
        }

        System.out.print(ans);
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

        String nextTokenOrNull() throws IOException {
            int c;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= ' ');

            StringBuilder sb = new StringBuilder();
            while (c > ' ') {
                sb.append((char)c);
                c = read();
                if (c == -1) break;
            }
            return sb.toString();
        }
    }
}