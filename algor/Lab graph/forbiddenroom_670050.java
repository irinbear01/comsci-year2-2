import java.io.*;
import java.util.*;

public class forbiddenroom_670050 {

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) { this.in = in; }

        private int readByte() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = readByte();
                if (c == -1) return Integer.MIN_VALUE; // no more
            } while (c <= ' ');

            int sign = 1;
            if (c == '-') { sign = -1; c = readByte(); }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = readByte();
            }
            return val * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int S = fs.nextInt();
        if (S == Integer.MIN_VALUE) return;
        int D = fs.nextInt();
        int P = fs.nextInt();
        int[] from = new int[P];
        int[] to = new int[P];
        int maxNode = Math.max(S, D);

        for (int i = 0; i < P; i++) {
            from[i] = fs.nextInt();
            to[i] = fs.nextInt();
            maxNode = Math.max(maxNode, Math.max(from[i], to[i]));
        }

        List<Integer>[] g = new ArrayList[maxNode + 1];
        for (int i = 0; i <= maxNode; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < P; i++) g[from[i]].add(to[i]);

        boolean[] visited = new boolean[maxNode + 1];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(S);
        visited[S] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            if (u == D) {
                System.out.print("Possible");
                return;
            }
            for (int v : g[u]) {
                if (v >= 0 && v < visited.length && !visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }

        System.out.print("Impossible");
    }
}
