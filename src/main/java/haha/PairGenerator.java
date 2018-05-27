package haha;

import haha.Board;
import haha.Pair;

import java.util.List;

/**
 * 决策产生器：输入一个局面，判断让谁来处理
 */
public interface PairGenerator {
List<Pair> getCompetePairs(Board board);
}
