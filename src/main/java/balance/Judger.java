package balance;

public class Judger {
/**
 * 当备选答案遇见称量策略strategy之后的结果
 */
static int judge(int solution, Strategy strategy) {
    for (int i : strategy.left) if (i == solution) return 1;
    for (int i : strategy.right) if (i == solution) return 2;
    return 0;
}
}
