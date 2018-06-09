package balance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Strategy {
List<Integer> left = new ArrayList<>();
List<Integer> right = new ArrayList<>();

Strategy() {

}

Strategy(List<Integer> left, List<Integer> right) {
    this.left = left;
    this.right = right;
}

Strategy copy() {
    return new Strategy(new ArrayList<>(this.left), new ArrayList<>(this.right));
}

@Override
public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i : left) builder.append(i + " ");
    builder.append('=');
    for (int i : right) builder.append(i + " ");
    return builder.toString();
}
}
