package com.horsehour.search.meta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * BordaCount is a popular aggregation method which simply computes the ranking
 * score of an element based on the number of elements that are ranked following
 * in all the ranking lists.
 * 
 * @author Chunheng Jiang
 * @version 1.0
 * @since 2014年5月7日 下午7:15:24
 **/
public class BordaCount<E> {
	protected int nCandidate;

	public BordaCount() {
		nCandidate = 0;
	}

	/**
	 * @param list
	 *            list of ranking list
	 * @param beginId
	 *            begin index of each ranking list
	 */
	public Map<E, Float> score(List<E> list, List<Integer> beginId) {
		Map<E, Float> uniqueList = getUniqueCandidates(list);
		List<E> selected = null;
		int nPermu = beginId.size();
		if (nPermu == 1) {
			score(list, uniqueList);
			return uniqueList;
		}

		for (int i = 1; i < nPermu; i++) {
			selected = list.subList(beginId.get(i - 1), beginId.get(i));
			score(selected, uniqueList);
		}
		selected = list.subList(beginId.get(nPermu - 1), list.size());
		score(selected, uniqueList);
		return uniqueList;
	}

	/**
	 * scores permutation of objects using borda rule: suppose there are n
	 * candidates, while only m < n selected candidates appearing in the given
	 * permutation, then the highest ranked one obtains a score n, the following
	 * one gets a score of n - 1. For all the candidates that have not been
	 * selected, their desired sum of scores should be equally allocated, that
	 * each of them get a score of [(n - m) + (n - m - 1) + ... + 1]/(n - m) =
	 * (n - m + 1)/2
	 * 
	 * @param permu
	 * @param uniqueList
	 */
	protected void score(List<E> permu, Map<E, Float> uniqueList) {
		int m = permu.size();
		float averageScore = calcAverageScore(m, nCandidate);
		Iterator<Map.Entry<E, Float>> iter = uniqueList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<E, Float> entry = iter.next();
			E elem = entry.getKey();
			float score = entry.getValue();
			int idx = permu.indexOf(elem);
			if (idx == -1)
				score += averageScore;
			else
				score += (nCandidate - idx);

			uniqueList.put(elem, score);
		}
	}

	/**
	 * @param m
	 * @param n
	 * @return calculation of the average score for all the other unselected
	 *         candidates
	 */
	private float calcAverageScore(int m, int n) {
		return (n - m + 1) * 1.0F / 2;
	}

	/**
	 * @param list
	 * @return obtain the involving unique candidates
	 */
	protected Map<E, Float> getUniqueCandidates(List<E> list) {
		Map<E, Float> uniqueList = new HashMap<>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			E elem = list.get(i);
			if (uniqueList.containsKey(elem))
				continue;

			uniqueList.put(elem, 0.0F);
			nCandidate++;
		}
		return uniqueList;
	}
}