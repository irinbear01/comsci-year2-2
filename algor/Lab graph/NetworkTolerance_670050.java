import java.io.*;
import java.util.*;

public class NetworkTolerance_670050 {
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
                if (c == -1) return Integer.MIN_VALUE;
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

    static List<Integer>[] g;
    static int[] disc, low, parent;
    static boolean[] visited;
    static int time = 0;
    static boolean foundArticulation = false;

    static void dfs(int u) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        int children = 0;

        for (int v : g[u]) {
            if (!visited[v]) {
                parent[v] = u;
                children++;
                dfs(v);

                low[u] = Math.min(low[u], low[v]);

                if (parent[u] == -1 && children >= 2) foundArticulation = true;
                if (parent[u] != -1 && low[v] >= disc[u]) foundArticulation = true;

            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }

            if (foundArticulation) return;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int E = fs.nextInt();
        if (E == Integer.MIN_VALUE) return;

        int[] a = new int[E];
        int[] b = new int[E];
        int maxNode = 0;

        for (int i = 0; i < E; i++) {
            a[i] = fs.nextInt();
            b[i] = fs.nextInt();
            maxNode = Math.max(maxNode, Math.max(a[i], b[i]));
        }

        g = new ArrayList[maxNode + 1];
        for (int i = 0; i <= maxNode; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            g[a[i]].add(b[i]);
            g[b[i]].add(a[i]);
        }

        disc = new int[maxNode + 1];
        low = new int[maxNode + 1];
        parent = new int[maxNode + 1];
        visited = new boolean[maxNode + 1];
        Arrays.fill(parent, -1);

        for (int i = 0; i <= maxNode; i++) {
            if (!visited[i]) {
                dfs(i);
                if (foundArticulation) break;
            }
        }

        System.out.print(foundArticulation ? "Detected" : "Good");
    }
}
