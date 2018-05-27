import java.util.*;

public class BruteForce {
int N = 7, K = 3;//N匹马,选出前K名来
char a[][] = new char[N][N];//当前局面
int win[] = new int[N];//win[i]表示第i匹马胜了的场数
Map<String, Integer> solved = new HashMap<>();//已经解决过得局面


void visit(int x, Pair[] tree, int maxStep) {
    if (isOver()) return;
    List<Pair> pairs = getCompetePairs();
    System.out.println(x + "\n" + printState());
    for (Pair p : pairs) {
        int xy = N * N, yx = N * N;
        List<Pair> fix = updateByCompeteResult(p.x, p.y);
        String now = hash();
        if (solved.containsKey(now)) {
            xy = solved.get(now);
        }
        undo(fix);
        if (xy > maxStep - 1) continue;
        if (!isSame(p.x, p.y)) {
            fix = updateByCompeteResult(p.y, p.x);
            now = hash();
            if (solved.containsKey(now)) {
                yx = solved.get(now);
            }
            undo(fix);
        } else {
            yx = xy;
        }
        if (Math.max(xy, yx) == maxStep - 1) {
            tree[x] = p;
            break;
        }
    }
    //访问左子树
    List<Pair> fix = updateByCompeteResult(tree[x].x, tree[x].y);
    visit(x << 1, tree, maxStep - 1);
    undo(fix);
    //访问右子树
    fix = updateByCompeteResult(tree[x].y, tree[x].x);
    visit(x << 1 | 1, tree, maxStep - 1);
    undo(fix);
}

//回溯找到最优策略
Pair[] traceStrategy(int minStep) {
    Pair[] tree = new Pair[1 << minStep];
    visit(1, tree, minStep);
    return tree;
}

//求x的哈希值:用他当前的比赛局面表示
String hashPerson(int x) {
    return new String(a[x]);
}

//求整个局面的哈希值
String hash() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < N; i++) {
        builder.append(a[i]);
    }
    return builder.toString();
}

//根据局面获取比赛对,优先让高手们决战,优先让优胜者胜
List<Pair> getCompetePairs() {
    List<Pair> pairs = new ArrayList<>();
    Set<String> added = new TreeSet<>();
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < i; j++) {
            if (a[i][j] == 0) {
                String pairCode = hashPerson(i) + hashPerson(j);
                if (!added.contains(pairCode)) {
                    added.add(pairCode);
                    pairs.add(new Pair(i, j));
                }
            }
        }
    }
    pairs.sort((o1, o2) -> {
        int one = win[o1.x] + win[o1.y];
        int two = win[o2.x] + win[o2.y];
        return two - one;
    });
    return pairs;
}

//根据比赛结果更新局面
List<Pair> updateByCompeteResult(int winner, int loser) {
    if (a[winner][loser] != 0) return new ArrayList<>();
    List<Pair> ret = new ArrayList<>();
    a[winner][loser] = 1;
    a[loser][winner] = 2;
    ret.add(new Pair(winner, loser));
    win[winner]++;
    for (int i = 0; i < N; i++) {
        if ((a[loser][i] == 1)) {
            ret.addAll(updateByCompeteResult(winner, i));
        }
        if (a[winner][i] == 2) {
            ret.addAll(updateByCompeteResult(i, loser));
        }
    }
    return ret;
}

//撤销对局面的更新
void undo(List<Pair> pairs) {
    for (Pair p : pairs) {
        a[p.x][p.y] = a[p.y][p.x] = 0;
        win[p.x]--;
    }
}

//判断游戏是否终止
boolean isOver() {
    boolean had[] = new boolean[K];
    for (int i = 0; i < N; i++) {
        if (win[i] > N - K - 1) {
            had[N - win[i] - 1] = true;
        }
    }
    for (int i = 0; i < K; i++) {
        if (!had[i]) {
            return false;
        }
    }
    return true;
}

//判断两匹马是否等价
boolean isSame(int x, int y) {
    for (int i = 0; i < N; i++) {
        if (a[x][i] != a[y][i]) {
            return false;
        }
    }
    return true;
}

//求局面的最少步数
int minSteps(int maxStep) {
    if (isOver()) {
        return 0;
    }
    if (maxStep <= 0) return N * N;
    String code = hash();
    if (solved.containsKey(code)) return solved.get(code);
    int nowMin = N * N;
    List<Pair> strategies = getCompetePairs();
    for (Pair p : strategies) {
        List<Pair> fix = updateByCompeteResult(p.x, p.y);
        int xy = minSteps(nowMin);
        undo(fix);
        if (xy >= nowMin) continue;
        if (isSame(p.x, p.y)) {
            nowMin = Math.min(nowMin, xy);
        } else {
            fix = updateByCompeteResult(p.y, p.x);
            int yx = minSteps(nowMin);
            undo(fix);
            nowMin = Math.min(Math.max(xy, yx), nowMin);
        }
    }
    if (nowMin < N * N) {
        solved.put(code, 1 + nowMin);
    }
    return 1 + nowMin;
}


void findRule() {
    long begTime = System.currentTimeMillis();
    int n = 10;
    for (int i = 1; i < n; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.print(solveOne(i, j, 100) + " ");
        }
        System.out.println();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("总共用时" + (endTime - begTime));
}

String printState() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            builder.append((char) ('0' + a[i][j]));
        }
        builder.append('\n');
    }
    return builder.toString();
}

int solveOne(int n, int k, int maxStep) {
    this.N = n;
    this.K = k;
    this.a = new char[N][N];
    this.win = new int[N];
    solved.clear();
    if (maxStep == 0) maxStep = n * n;
    int ans = minSteps(maxStep);
    return ans;
}

void showStrategy(int n, int k) {
    int ans = solveOne(n, k, 0);
    System.out.println(printState() + " " + ans);
    Pair[] tree = traceStrategy(ans);
    new StrategyPlayer(tree).show();
}

BruteForce() {
//    findRule();
    showStrategy(5, 3);
}

public static void main(String[] args) {
    new BruteForce();
}
}
