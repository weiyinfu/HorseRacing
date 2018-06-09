package balance;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import treeplayer.NodeVisitor;
import treeplayer.TreePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 启发式方法寻找病球，答案不一定是最少步数
 */
public class Main {
int n = 12;
Node root = null;

//根据当前可行解决定下一步的称量计划
Node build(List<Integer> solutions, int order) {
    Node node = new Node(solutions, order);
    if (solutions.size() <= 1) return node;
    node.strategy = new Heuristic(n, node.solutions).bestStrategy;
    List<List<Integer>> sonsSolutions = new ArrayList<>(3);
    for (int i = 0; i < 3; i++) sonsSolutions.add(new ArrayList<>());
    for (int solution : node.solutions) {
        int res = Judger.judge(solution, node.strategy);
        sonsSolutions.get(res).add(solution);
    }
    for (int i = 0; i < 3; i++) node.sons[i] = build(sonsSolutions.get(i), i);
    return node;
}

Main() {
    List<Integer> solutions = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
        solutions.add(i);
    }
    root = build(solutions, 0);
    new StrategyPlayer(root);
}

public static void main(String[] args) {
    new Main();
}
}
