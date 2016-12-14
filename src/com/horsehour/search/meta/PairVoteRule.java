package com.horsehour.search.meta;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Stage pairwise voting method rating pairs of candidates based on stagewise
 * rating rule, a rule that top ranked candidates receive more scores and the
 * differences between neighbors are decreasing with the ranking positions. For
 * example, there are n candidates for voting, for a given permutation \pi =
 * \{\pi_1,\ldots,\pi_m\}, where m <= n, we assume that when compared with the
 * followed \pi_i, \pi_1 gets a score of i - 1. When compared with the followed
 * candidates \pi_i, \pi_2 gets a positive score of (i-2)/2. Those candidates
 * that have been ranked below therefore receive an equal negative score. In
 * this system, all the other unselected candidates receive a same negative
 * score. Finally, all the scores are collected for each candidates, and
 * utilized in future ranking.
 * 
 * @author Chunheng Jiang
 * @version 1.0
 * @since 2014年5月9日 下午6:24:54
 **/
public class PairVoteRule<E> extends BordaCount<E> {
	protected List<Float> harmonicNum;
	protected List<Float> scoreList;

	private static int initLen = 10;

	public PairVoteRule() {
		super();
		harmonicNum = new ArrayList<>();
		scoreList = new ArrayList<>();
		computeHarmonicNum(initLen);
	}

	/**
	 * precompute the harmonic number
	 */
	private void computeHarmonicNum(int len) {
		float harmonic = 0.0F;
		int beginIdx = 1;
		if (len > initLen) {
			beginIdx = initLen + 1;
			harmonic = harmonicNum.get(initLen - 1);
			initLen = len;// update
		}
		for (int i = beginIdx; i <= len; i++) {
			harmonic += 1.0F / i;
			harmonicNum.add(harmonic);
		}
	}

	/**
	 * scores permutation of candidates in stage pairwise voting rule
	 * 
	 * @param permu
	 * @param uniqueList
	 */
	protected void score(List<E> permu, Map<E, Float> uniqueList) {
		int m = permu.size();
		if (m > initLen)
			computeHarmonicNum(m);

		float[] scores = vote(m, nCandidate);// m+1 dimension
		Iterator<Map.Entry<E, Float>> iter = uniqueList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<E, Float> entry = iter.next();
			E elem = entry.getKey();
			float score = entry.getValue();
			int idx = permu.indexOf(elem);
			if (idx == -1)
				score += scores[m];
			else
				score += scores[idx];
			uniqueList.put(elem, score);
		}
	}

	/**
	 * @param m
	 * @param n
	 * @return get the score of each position
	 */
	private float[] vote(int m, int n) {
		float[] score = new float[m + 1];
		score[0] = 1.0F * m * (2 * n - m - 1) / 2;// highest
		for (int i = 1; i <= m; i++)
			score[i] = score[i - 1] + 0.5F + 1.0F * (m + 1) * (m - 2 * n)
			        / (2 * i * (i + 1)) - harmonicNum.get(i - 1);
		return score;
	}
}
