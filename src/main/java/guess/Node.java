package guess;

import java.util.List;

public class Node {
List<Integer> solutions;
Node[] sons;
int strategy;
int order;//我的排名

Node(List<Integer> solutions, int N, int order) {
    this.solutions = solutions;
    this.sons = new Node[N + 1];
    this.order = order;
}
}
