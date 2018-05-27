package haha;

import java.util.ArrayList;
import java.util.List;

/**
 * 局面类，用二维表描述拓扑图
 */
class Board {
int N = 7, K = 3;//N匹马,选出前K名来
char a[][] = new char[N][N];//当前局面
int win[] = new int[N];//win[i]表示第i匹马胜了的场数,用于快速判断游戏是否结束
int lose[] = new int[N];//lose[i]表示第i匹马败了的场数，用于快速查询

Board(int N, int K) {
    this.N = N;
    this.K = K;
    this.a = new char[N][N];
    this.win = new int[N];
    this.lose = new int[N];
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

//根据比赛结果更新局面
List<Pair> updateByCompeteResult(int winner, int loser) {
    if (a[winner][loser] != 0) return new ArrayList<>();
    List<Pair> ret = new ArrayList<>();
    a[winner][loser] = 1;
    a[loser][winner] = 2;
    ret.add(new Pair(winner, loser));
    win[winner]++;
    lose[loser]++;
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
        lose[p.y]--;
    }
}

String screenCut() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            builder.append((char) ('0' + a[i][j]));
        }
        builder.append('\n');
    }
    builder.append("\nwin state\n");
    for (int i = 0; i < N; i++) {
        builder.append(win[i] + " ");
    }
    builder.append("\nlose state\n");
    for (int i = 0; i < N; i++) {
        builder.append(lose[i] + " ");
    }
    builder.append('\n');
    return builder.toString();
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
}
