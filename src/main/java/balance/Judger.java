package balance;

public class Judger {

static int judge(int solution, Strategy strategy) {
    for (int i : strategy.left) if (i == solution) return 1;
    for (int i : strategy.right) if (i == solution) return 2;
    return 0;
}
}
