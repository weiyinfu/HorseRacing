package balance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StrategyList {
int n;
StrategyVisitor v;

StrategyList(int n, StrategyVisitor v) {
    this.n = n;
    this.v = v;
    go();
}

void go() {
    List<Integer> a = new ArrayList<>();
    for (int i = 0; i < n; i++) a.add(i);
    for (int i = 1; i <= n / 2; i++) {
        final int cnt = i;
        new Select(a, i * 2, chosen -> new Select(chosen, cnt, right -> {
            List<Integer> left = new ArrayList<>(cnt);
            int j = 0;
            for (int k = 0; k < chosen.size(); k++) {
                if (j < right.size() && chosen.get(k).equals(right.get(j))) {
                    j++;
                } else {
                    left.add(chosen.get(k));
                }
            }
            v.handle(new Strategy(left, right));
        }));
    }
}


}
