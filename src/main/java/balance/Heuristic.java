package balance;

import java.util.Arrays;
import java.util.List;

public class Heuristic {
Strategy bestStrategy = null;
int bestProfit = 0;

int profit(int[] cnt) {
    int s = 0;
    for (int i : cnt) {
        s += i * i;
    }
    return -s;
}

Heuristic(int n, List<Integer> solutions) {
    new StrategyList(n, strategy -> {
        int res[] = new int[3];
        for (int solution : solutions) {
            res[Judger.judge(solution, strategy)]++;
        }
        int p = profit(res);
        if (bestStrategy == null || p > bestProfit) {
            bestProfit = p;
            bestStrategy = strategy.copy();
        }
    });
}
}
