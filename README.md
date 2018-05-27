## 从马赛跑问题说起
25匹马在只有5个跑道的操场上赛跑，决出第一第二第三名至少比赛几次？

## 答案
7次。  
25马分成5组比五次，选出每组第一名共5马举行第六次比赛，得到甲乙丙三马，甲马即为冠军不必再比。甲组的亚军、甲组的季军、乙马、乙组的亚军、丙马共5马举行第七次比赛，得到ABC三马，AB两马即为第二名、第三名。

得到特定问题的答案不能满足，要考虑这个问题更一般的形式

## 问题变形
决出前三，前三无需分名次

## 形式化
输入三个数字N M K  
N表示有N匹马  
M表示有M条跑道  
K表示决出前K名来（分名次）

重要假设:
* 可复现:甲如果比乙强,那么比赛重复多次,甲还是比乙强
* 传递性:甲比乙强,乙比丙强,那么甲比丙强

## 打表
最好是能够枚举N从3到100，M固定为5，K固定为3，打表找规律。

## 启发式解法
每次比赛，肯定选取各个小组的第一名进行比赛，这样才能够更快地排除掉不可能有名次的马。  
比如有102匹马5道前三名，分成20组，剩下x马和y马。  
20组的第一再比，分成4组，得到4个第一；  
4个第一加上x马五匹马比赛，得到一个边长为3的左上角，其余马都被淘汰。这个左上角第一名已经确定，还剩下五匹马争夺第二第三名。
最后的前三名跟y马再比一场。  
需要注意，余下的单马（也就是x马和y马这样的马）参加比赛，它们肯定会输，因为如果它们胜利，只会让比赛次数更少，所以一定要假设它们会输，这样求出来的结果才能保证是至少需要多少次才能决出来。
推广之，组小的马参加比赛，应该假设它们会输，因为如果组大的马输了，可能会使得比赛次数减少

一言以蔽之，这个问题就是贪心：
* 每次比赛，优先选取各个小组第一参赛，这样能够最快排除掉劣马
* 除了最后一次比赛不满道，其它比赛肯定都是满道，因为不满道的比赛是一种浪费
* 单马参加比赛，优先假设它们会输，因为如果它们赢了，更有利于快速结束比赛，而我们要求的是至少最多需要多少次。那些小组内的马如果赢了，需要考虑它组内成员是否有可能也很强；而单马赢了，只是单马赢了。所以组内马赢，情况更复杂。

一开始，假设所有马的最佳排名都是第一。随着比赛的进行，更新各匹马的最佳排名。

这个问题等价于树的合并：  
一开始有N棵树，每次可以选取M棵树进行合并，使得最终树形成“一个长度为K的链表+一棵树”的形式，也就是树的根部没有分叉。

## 编程实现
可以使用并查集进行优化。  
每个结点都维护一个打败马的集合。  
马的最佳排名就是马到树根结点的距离。  


## 暴力求法
如何描述一个比赛局面?  
比赛局面实际上是一个有向无环图.可以用一个二维表来表示.
minmax剪枝.
对于每个比赛局面,我们可以采取多种策略.一种策略就对应一组比赛选手.
每个比赛可能有多种比赛结果.
策略的估值就是选择该策略之后引起的最差结果.也就是说选择该策略之后至多需要比赛多少次的结果.
我们所要求解的其实就是一个策略产生器:输入局面,输出决策.  
这个过程就像博弈一样.是策略产生器和比赛现实之间的博弈.
策略产生器总是想让比赛尽快结束,比赛现实却总是想让比赛晚点结束.

## 一种棋类游戏
在N×N的棋盘上进行,一个人决定在一个位置下黑子(胜)还是白子(败)

## 拓扑图有序性评估指标
如何评价一个有序拓扑图的混乱程度?  
掰腕子比赛多少次才能使整个拓扑图变得有序.

## 特殊化的问题
2是世界上最特殊的数字  
数字发生两次重大变化：从0到1是无中生有，从1到2是由单一变成多个。  
有了2人们可以用2拼出3，拼出更多，2是从单一走向多元的质变过程，而从2到3、从3到4都是量变过程。  
空间中的距离终究都是勾股定理，是二维拼凑出来的。  

人类的比赛大都是1v1，也就是2个人比赛，从棋类竞技、体育竞技到政治和战争，两个集团之间的竞争太常见了。  
马赛跑这个问题也有一种特殊形式：跑道上只能容下2匹马，要求决出前三名。这不就相当于奥运会上应该如何安排比赛吗？  
肯定是两两比赛，取各组第一名再进行比赛。。。。这个问题是马赛跑问题的特殊形式。  

形式化描述这个特殊化的问题:n个人掰腕子,至多比赛几次才能找到力量最大的前k个人?
称此问题为:掰腕子问题.
## 跟其它问题的联系
无码天平寻病球，关键在于天平无码  
马赛跑问题，关键在于没有准确读数的表  
尺规作图，关键在于尺子没有刻度  
人们都喜欢这种简单化、非刻度化的问题，如果刻度化，那就趣味大减。

无码天平寻病球，天平上可以放下多个球；马赛跑，相当于天平有5个托盘，每个托盘只能放下1个球。  
于是构造新问题，多托盘天平寻病球，每个托盘上可以放置的球数为P，当P=1时，问题等价于马赛跑问题；当托盘个数等于2且P=N（球数或者马数）时，问题等价于无码天平寻病球。  

这类问题的共性：**使用没有刻度的度量衡对样本点进行分类**。  
马赛跑分出第一、第二、第三、没名次4类  
无码天平寻病球分出异常球和普通球或者重球和普通球或者轻球和普通球两种类别  

这类问题为何如此常见：
* 没有办法量化选手的能力，比如围棋选手的水平，这是难以用数值来衡量的，只能用“没有刻度的度量衡”来让他们比赛
* 无环假设，人们假设：能力的比拼具有传递性，即A强过B、B强过C，则A强过C。而实际上不一定！为了简化问题，人们只能做出无环假设，因为这个假设的存在，才使得上面这些问题有意义，否则毫无意义。

## 简化版问题
n匹马，m个跑道，给这些马排出名次来需要比赛多少次？

## 参考资料
[zhihu](https://www.zhihu.com/question/19856916)