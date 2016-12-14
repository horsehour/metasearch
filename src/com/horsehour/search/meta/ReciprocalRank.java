package com.horsehour.search.meta;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Reciprocal Rank Fusion (RRF) is simple method for combining the ranking
 * results from multiple sources. It was believed to be consistently better than
 * any individual ranking aggregation system.
 * 
 * <p>
 * Detailed information can be found in: Cormack, Gordon V., Charles LA Clarke,
 * and Stefan Buettcher. "Reciprocal rank fusion outperforms condorcet and
 * individual rank learning methods." Proceedings of the 32nd international ACM
 * SIGIR conference on Research and development in information retrieval. ACM,
 * 2009.
 * 
 * @author Chunheng Jiang
 * @version 1.0
 * @since 20140507-204400
 **/
public class ReciprocalRank<E> extends BordaCount<E> {
	public ReciprocalRank() {
		super();
	}

	/**
	 * scores permutation of candidates as in borda rule: suppose there are n
	 * candidates, while only m < n selected candidates appearing in the given
	 * permutation, then the highest ranked one obtains a score 1/1, the
	 * following one gets a score of 1/2. For all the candidates that have not
	 * been selected, their desired sum of scores should be equally allocated,
	 * that each of them get a score of [1/(m + 1) + 1/(m + 2) + ... + 1/n]/(n -
	 * m)
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
				score += 1.0F / (idx + 1);

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
		if (m == n)
			return 0;

		float averageScore = 0;
		int k = m + 1;
		while (k <= n) {
			averageScore += 1.0F / k;
			k++;
		}
		averageScore /= (n - m);
		return averageScore;
	}
}
