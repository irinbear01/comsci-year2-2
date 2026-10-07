import java.io.*;
import java.util.*;

public class Restore_IP_Addresses_670050 {
    static List<String> result = new ArrayList<>();
    static String s;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        s = br.readLine().trim();

        backtrack(0, 0, new ArrayList<>());

        if (!result.isEmpty()) {
            System.out.println(String.join(" ", result));
        }
    }

    static void backtrack(int index, int part, List<String> current) {
        if (part == 4) {
            if (index == s.length()) {
                result.add(String.join(".", current));
            }
            return;
        }

        int remainingChars = s.length() - index;
        int remainingParts = 4 - part;

        if (remainingChars < remainingParts || remainingChars > remainingParts * 3) {
            return;
        }

        for (int len = 1; len <= 3 && index + len <= s.length(); len++) {
            String segment = s.substring(index, index + len);

            if (!isValid(segment)) {
                continue;
            }

            current.add(segment);
            backtrack(index + len, part + 1, current);
            current.remove(current.size() - 1);
        }
    }

    static boolean isValid(String segment) {
        if (segment.length() > 1 && segment.charAt(0) == '0') {
            return false;
        }

        int value = Integer.parseInt(segment);
        return value >= 0 && value <= 255;
    }
}