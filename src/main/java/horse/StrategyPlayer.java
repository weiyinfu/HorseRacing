package horse;

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

String tos() {
    return fileStyle("", 1);
}

public String fileStyle(String prefix, int x) {
    StringBuilder builder = new StringBuilder();
    builder.append(prefix + tree[x].x + "," + tree[x].y).append('\n');
    String temp = prefix.replace('┣', '┃');
    temp = temp.replace("━", "  ");
    temp = temp.replace("┗", "  "); //一定要注意，一个这个符号是两个空格
    boolean left = (x << 1) < tree.length && tree[x << 1] != null;
    boolean right = (x << 1 | 1) < tree.length && tree[x << 1 | 1] != null;
    if (left && right) {
        builder.append(fileStyle(temp + "┣━", x << 1));
        builder.append(fileStyle(temp + "┗━", x << 1 | 1));
    } else {
        if (left) {
            builder.append(fileStyle(temp + "┗━", x << 1));
        } else if (right) {
            builder.append(fileStyle(temp + "┗━", x << 1 | 1));
        }
    }
    return builder.toString();
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