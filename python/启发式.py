def init(N):
    """
    用元组的方式表示：
    0 马的id
    1 名次，计算得出
    2 我最近直接打败的马，计算得出
    3 最近打败我的马的id
    4 我打败马的个数，计算得出
    """
    return [[i, 1, set(), -1, 0] for i in range(N)]


def init_rank(a):
    def go(x, rank):
        a[x][1] = rank
        s = 0
        for j in a[x][2]:
            s += go(j, rank + 1)
        a[x][4] = s
        return s + 1

    for i in a:
        i[2].clear()
    for i in a:
        if i[3] != -1:
            a[i[3]][2].add(i[0])
    for i in a:
        if i[3] == -1:
            go(i[0], 1)


def choose(a, N, M, K):
    # 选马，按照“最佳排名、打败马的个数”进行排序，也就是组大的优先获胜
    b = sorted(a, key=lambda x: (x[1], x[4]))
    i = 0
    while i < K and b[i][1] != b[i + 1][1]:  # 前面几匹马已经确定不必再比
        i += 1
    if i >= K: return
    return [b[x][0] for x in range(i, min(N, i + M))]


def compete(a, chosen, output=True):
    # 对已选择的马进行比赛、排名，按照“打败马的个数、最佳排名”进行排序；更新各匹马的打败马集合
    chosen = sorted(chosen, key=lambda x: (-a[x][4]))
    if output:
        print("compete", chosen)
    for i in range(len(chosen) - 1, 0, -1):
        horse = chosen[i]
        last = chosen[i - 1]
        a[horse][3] = last


def show(a):
    def show_tree(x, prefix='', is_first=True):
        if is_first:
            print('%-3d' % (a[x][0]), end=' ', sep='')
        else:
            print('\n', prefix, '%-3d' % (a[x][0]), end=' ', sep='')
        first = True
        for i in a[x][2]:
            show_tree(i, prefix + ' ' * 4, first)
            if first: first = False

    for i in a:
        if i[1] == 1:
            print()
            show_tree(i[0])


def get_ans(N, M, K, output=True):
    cnt = 0
    a = init(N)
    while 1:
        t = choose(a, N, M, K)
        if not t: break
        compete(a, t, output)
        init_rank(a)
        cnt += 1
        if output:
            print(cnt)
            show(a)
            input()
    if output:
        res = [i[0] for i in sorted(a, key=lambda x: x[1])]
        print('共进行比赛', cnt, '场', '比赛结果', res[:K])
    return cnt


def test():
    N, M, K = 25, 6, 7
    for i in range(max(K + 1, 6), 100):
        N = i
        print(N, get_ans(N, M, K, 0))


def why():
    N, M, K = 7, 2, 2
    get_ans(N, M, K, True)
    print("=" * 20)


def print_table():
    for n in range(3, 10):
        for k in range(1, n):
            print(get_ans(n, 2, k, False), end=' ')
        print()


print_table()
