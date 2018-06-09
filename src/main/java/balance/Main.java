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

public class Main {
int n = 12;
Node root = null;

class Node {
    //三种结果对应三种决策
    Node[] sons = new Node[3];
    List<Integer> solutions;
    Strategy strategy;
    int order;//排行

    Node(List<Integer> solutions, int order) {
        this.solutions = solutions;
        this.order = order;
    }
}

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

NodeVisitor<Node> visitor = new NodeVisitor<Node>() {
    @Override
    public List<Node> getSons(Node node) {
        return Arrays.stream(node.sons).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public String tos(Node node) {
        StringBuilder builder = new StringBuilder();
        builder.append("平左右".charAt(node.order)).append(' ');
        if (node.strategy == null) {
            if (node.solutions.size() == 0) builder.append("error");
            else builder.append(node.solutions.get(0));
        } else {
            builder.append(node.strategy).append(' ');
            builder.append("[").append(node.solutions.size()).append("]");
        }
        return builder.toString();
    }

    @Override
    public Element toElement(Node node) {
        Element e = DocumentHelper.createElement("if");
        e.addAttribute("result", "平左右".charAt(node.order) + "");
        if (node.strategy == null) {
            if (node.solutions.size() == 0) e.addAttribute("state", "error");
            else e.addAttribute("state", node.solutions.get(0) + "");
        } else {
            e.addAttribute("strategy", node.strategy.toString());
        }
        return e;
    }

    @Override
    public Node root() {
        return root;
    }
};

Main() {
    List<Integer> solutions = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
        solutions.add(i);
    }
    root = build(solutions, 0);
    String s = TreePlayer.filestyle(visitor);
    System.out.println(s);
    TreePlayer.xml(visitor, "balance.xml");
    TreePlayer.swingControl(visitor);
}

public static void main(String[] args) {
    new Main();
}
}
