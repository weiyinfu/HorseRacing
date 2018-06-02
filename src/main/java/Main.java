import java.io.File;
import java.util.Scanner;

public class Main {
final int MAXN = (int) (1e6 + 7);
int a[] = new int[MAXN];
int n;
long dp[][] = new long[MAXN][4];

Main() {
    Scanner cin = new Scanner(System.in);
    try {
        cin = new Scanner(new File("in.txt"));
    } catch (Exception e) {

    }
    n = cin.nextInt();
    for (int i = 0; i < n; i++) a[i + 1] = cin.nextInt();
    dp[0][0] = dp[0][1] = dp[0][2] = Integer.MIN_VALUE;
    long ans = Integer.MIN_VALUE;
    for (int i = 1; i <= n; i++) {
        if (a[i] == 0) {
            dp[i][0] = a[i];
            for (int j = 1; j <= 3; j++) {
                if (dp[i - 1][j - 1] >= 0) {
                    dp[i][j] = dp[i - 1][j - 1] + a[i];
                } else {
                    dp[i][j] = a[i];
                }
                if (dp[i][j] > ans) {
                    ans = dp[i][j];
                }
            }
            if (a[i] > ans) ans = a[i];
        } else {
            for (int j = 0; j < 4; j++) {
                if (dp[i - 1][j] >= 0) {
                    dp[i][j] = dp[i - 1][j] + a[i];
                } else {
                    dp[i][j] = a[i];
                }
                if (dp[i][j] > ans) ans = dp[i][j];
            }
        }
    }
    cin.close();
    System.out.println(ans);
}

public static void main(String[] args) {
    new Main();
}
}
