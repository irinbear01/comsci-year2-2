import java.util.*;

public class Boonnut_67050050 {

    //เก็บเวลา
    static class Interval {
        int start;
        int end;

        Interval(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Interval[] intervals = new Interval[N];

        //input
        for (int i = 0; i < N; i++) {
            int s = sc.nextInt();
            int e = sc.nextInt();
            intervals[i] = new Interval(s, e);
        }

        //เรียงตามเวลา end น้อยไปมาก
        Arrays.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                return a.end - b.end;
            }
        });

        int keep = 0;
        int lastEnd = -1; //เพราะ S >= 1

        //Greedy selection
        for (Interval in : intervals) {
            if (in.start >= lastEnd) {
                keep++;
                lastEnd = in.end;
            }
        }

        //จำนวนที่ต้องยกเลิก
        int cancel = N - keep;
        System.out.println(cancel);

        sc.close();
    }
}
