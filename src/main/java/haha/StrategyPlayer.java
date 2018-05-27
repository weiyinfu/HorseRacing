package haha;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

/**
 * 决策演示器，给定一个决策二叉树，形象的展示结果
 */
class StrategyPlayer {
Pair[] tree;

StrategyPlayer(Pair[] tree) {
    this.tree = tree;
}

String empty(int cnt) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < cnt; i++) builder.append(' ');
    return builder.toString();
}

String visit(int x, int depth) {
    if (x >= tree.length || tree[x] == null) return "";
    StringBuilder builder = new StringBuilder();
    builder.append("\n" + empty(depth) + tree[x].x + " " + tree[x].y + visit(x << 1, depth + 1) + visit(x << 1 | 1, depth + 1));
    return builder.toString();
}

void show() {
    System.out.println(visit(1, 0));
}

Element createXML(int x, boolean isLeft) {
    if (x >= tree.length) return null;
    if (tree[x] == null) return null;
    Element e = DocumentHelper.createElement(isLeft ? "if" : "else");
    e.addAttribute("x", "" + tree[x].x).addAttribute("y", "" + tree[x].y);
    Element left = createXML(x << 1, true);
    if (left != null) e.add(left);
    Element right = createXML(x << 1 | 1, false);
    if (right != null) e.add(right);
    return e;
}

void exportXML() {
    try {
        Document doc = DocumentHelper.createDocument();
        doc.add(createXML(1, true));
        Writer writer = new FileWriter("haha.xml");
        doc.write(writer);
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void main(String[] args) {
    Pair[] pairs = new Pair[10];
    Random random = new Random();
    for (int i = 0; i < pairs.length; i++) {
        pairs[i] = new Pair(random.nextInt(100), random.nextInt(100));
    }
    new StrategyPlayer(pairs).exportXML();
}
}