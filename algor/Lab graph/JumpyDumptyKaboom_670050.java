import java.io.*;
import java.util.*;

public class JumpyDumptyKaboom_670050{
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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int X = fs.nextInt();
        if (X == Integer.MIN_VALUE) return;
        int N = fs.nextInt(); // ำนวนข้อมูลบ้านที่อยู่ติดกัน

        int[] a = new int[N];
        int[] b = new int[N];
        int maxNode = 0;

        for (int i = 0; i < N; i++) {
            a[i] = fs.nextInt();
            b[i] = fs.nextInt();
            maxNode = Math.max(maxNode, Math.max(a[i], b[i]));
        }

        List<Integer>[] g = new ArrayList[maxNode + 1];
        for (int i = 0; i <= maxNode; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            g[a[i]].add(b[i]);
            g[b[i]].add(a[i]);
        }

        int[] dist = new int[maxNode + 1];
        Arrays.fill(dist, -1);

        ArrayDeque<Integer> q = new ArrayDeque<>();
        dist[0] = 0;
        q.add(0);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g[u]) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    q.add(v);
                }
            }
        }

        long sum = 0;
        for (int node = 1; node <= maxNode; node++) {
            if (dist[node] == -1) continue;
            int d = dist[node];
            int comp = X - 10 * (d - 1);
            if (comp < 0) comp = 0;
            sum += comp;
        }

        System.out.print(sum);
    }
}
