package com.horsehour.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * A Tiny Mathematical Utility
 * 
 * @author Chunheng Jiang
 * @version 3.0
 * @since 20131208
 */
public final class MathUtils {
	/**
	 * Euclidean Norm
	 * 
	 * @param array
	 * @return sqrt(∑array[i]^2)
	 */
	public static <T extends Number> double getEuclidNorm(T[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i].doubleValue() * array[i].doubleValue();
		return Math.sqrt(sum);
	}

	public static double getEuclidNorm(double[] array) {

		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i] * array[i];
		return Math.sqrt(sum);
	}

	public static float getEuclidNorm(float[] array) {
		float sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i] * array[i];

		return (float) Math.sqrt(sum);
	}

	public static double getEuclidNorm(int[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i] * array[i];

		return Math.sqrt(sum);
	}

	public static <T extends Number> double getEuclidNorm(List<T> list) {
		double sum = 0;
		int len = list.size();
		for (int i = 0; i < len; i++) {
			double elm = list.get(i).doubleValue();
			sum += elm * elm;
		}
		return Math.sqrt(sum);
	}

	/**
	 * Weighted Euclidean Norm
	 * 
	 * @param array
	 * @param weight
	 * @return sqrt(∑w[i]*arr[i]^2)
	 */
	public static <T extends Number, P extends Number> double getWeightedEuclidNorm(T[] array, P[] weight) {
		double wsum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i].doubleValue() * array[i].doubleValue() * array[i].doubleValue();
		return Math.sqrt(wsum);
	}

	public static double getWeightedEuclidNorm(double[] array, double[] weight) {
		double wsum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i] * array[i] * array[i];

		return Math.sqrt(wsum);
	}

	public static float getWeightedEuclidNorm(float[] array, float[] weight) {
		float wsum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i] * array[i] * array[i];

		return (float) Math.sqrt(wsum);
	}

	public static double getWeightedEuclidNorm(List<? extends Number> list, List<? extends Number> weight) {
		double wsum = 0;
		int len = list.size();
		for (int i = 0; i < len; i++) {
			double val = list.get(i).doubleValue();
			wsum += weight.get(i).doubleValue() * val * val;
		}
		return Math.sqrt(wsum);
	}

	/**
	 * L_2 Norm, namely Euclidean Norm
	 * 
	 * @param array
	 * @return ∑array[i]^2
	 */
	public static <T extends Number> double getL2Norm(T[] array) {
		return getEuclidNorm(array);
	}

	public static double getL2Norm(double[] array) {
		return getEuclidNorm(array);
	}

	public static float getL2Norm(float[] array) {
		return getEuclidNorm(array);
	}

	public static double getL2Norm(int[] array) {
		return getEuclidNorm(array);
	}

	public static double getL2Norm(List<? extends Number> list) {
		return getEuclidNorm(list);
	}

	/**
	 * L_1 Norm
	 * 
	 * @param array
	 * @return ∑|array[i]|
	 */
	public static <T extends Number> double getL1Norm(T[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += Math.abs(array[i].doubleValue());
		return sum;
	}

	public static double getL1Norm(double[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += Math.abs(array[i]);

		return sum;
	}

	public static float getL1Norm(float[] array) {
		float sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += Math.abs(array[i]);

		return sum;
	}

	public static int getL1Norm(int[] array) {
		int sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += Math.abs(array[i]);

		return sum;
	}

	public static <T extends Number> double getL1Norm(List<T> list) {
		double sum = 0;
		int len = list.size();
		for (int i = 0; i < len; i++)
			sum += Math.abs(list.get(i).doubleValue());
		return sum;
	}

	/**
	 * Inner Product
	 * 
	 * @param arr1
	 * @param arr2
	 * @return ∑arr1[i]*arr2[i]
	 */
	public static <T extends Number, P extends Number> double innerProd(T[] arr1, P[] arr2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}

		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double sum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			sum += arr1[i].doubleValue() * arr2[i].doubleValue();

		return sum;
	}

	public static double innerProd(double[] arr1, double[] arr2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double sum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			sum += arr1[i] * arr2[i];

		return sum;
	}

	public static float innerProd(float[] arr1, float[] arr2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		float sum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			sum += arr1[i] * arr2[i];

		return sum;
	}

	public static int innerProd(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int sum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			sum += arr1[i] * arr2[i];

		return sum;
	}

	public static double innerProd(List<? extends Number> list1, List<? extends Number> list2) {
		if (list1.isEmpty() || list2.isEmpty()) {
			System.err.println("Empty List.");
			System.exit(0);
		}
		if (list1.size() != list2.size()) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}
		double sum = 0;
		int len = list1.size();
		for (int i = 0; i < len; i++)
			sum += list1.get(i).doubleValue() * list2.get(i).doubleValue();
		return sum;
	}

	/**
	 * Weigthed Inner Product
	 * 
	 * @param arr1
	 * @param arr2
	 * @return ∑w[i]arr1[i]*arr2[i]
	 */
	public static <T extends Number, P extends Number> double weightedInnerProd(T[] arr1, T[] arr2, P[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}

		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double wsum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i].doubleValue() * arr1[i].doubleValue() * arr2[i].doubleValue();

		return wsum;
	}

	public static double weightedInnerProd(double[] arr1, double[] arr2, double[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}

		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double wsum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i] * arr1[i] * arr2[i];

		return wsum;
	}

	public static float weightedInnerProd(float[] arr1, float[] arr2, float[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}

		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		float wsum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i] * arr1[i] * arr2[i];

		return wsum;
	}

	public static double weightedInnerProd(int[] arr1, int[] arr2, double[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}

		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double wsum = 0;
		int len = arr1.length;
		for (int i = 0; i < len; i++)
			wsum += weight[i] * arr1[i] * arr2[i];

		return wsum;
	}

	public static double weightedInnerProd(List<? extends Number> list1, List<? extends Number> list2,
			List<? extends Number> weight) {
		if (list1.isEmpty() || list2.isEmpty()) {
			System.err.println("Empty List.");
			System.exit(0);
		}
		if (list1.size() != list2.size()) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double wsum = 0;
		int len = list1.size();
		for (int i = 0; i < len; i++)
			wsum += weight.get(i).doubleValue() * list1.get(i).doubleValue() * list2.get(i).doubleValue();

		return wsum;
	}

	/**
	 * @param array
	 * @return ∑array[i]
	 */
	public static <T extends Number> double sum(T[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i].doubleValue();
		return sum;
	}

	public static double sum(double[] array) {
		double sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i];
		return sum;
	}

	public static float sum(float[] array) {
		float sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i];
		return sum;
	}

	public static int sum(int[] array) {
		int sum = 0;
		int len = array.length;
		for (int i = 0; i < len; i++)
			sum += array[i];
		return sum;
	}

	public static double sum(List<? extends Number> list) {
		double sum = 0;
		int sz = list.size();
		for (int i = 0; i < sz; i++)
			sum += list.get(i).doubleValue();
		return sum;
	}

	/**
	 * Normalize Array Based on Euclidean Norm or L_2 Norm
	 * 
	 * @param array
	 */
	public static <T extends Number> Double[] normalize(T[] array) {
		double norm = getEuclidNorm(array);
		Double[] ret = null;
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			ret = new Double[len];
			for (int i = 0; i < len; i++)
				ret[i] = array[i].doubleValue() / norm;
		}
		return ret;
	}

	public static void normalize(double[] array) {
		double norm = getEuclidNorm(array);
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= norm;
		}
	}

	public static void normalize(float[] array) {
		double norm = getEuclidNorm(array);
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= norm;
		}
	}

	public static double[] normalize(int[] array) {
		double norm = getEuclidNorm(array);
		double[] ret = null;
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			ret = new double[len];
			for (int i = 0; i < len; i++)
				ret[i] /= norm;
		}
		return ret;
	}

	public static <T extends Number> List<Double> normalize(List<T> list) {
		double norm = getEuclidNorm(list);
		List<Double> ret = null;
		if (norm > Double.MIN_VALUE) {
			ret = new ArrayList<Double>();
			int len = list.size();
			for (int i = 0; i < len; i++)
				ret.add(list.get(i).doubleValue() / norm);
		}

		return ret;
	}

	/**
	 * Normalize Array Based on Sum
	 * 
	 * @param array
	 */
	public static <T extends Number> Double[] sumScale(T[] array) {
		double sum = sum(array);
		Double[] ret = null;
		if (sum > Double.MIN_VALUE) {
			int len = array.length;
			ret = new Double[len];
			for (int i = 0; i < len; i++)
				ret[i] = array[i].doubleValue() / sum;
		}
		return ret;
	}

	public static void sumScale(double[] array) {
		double sum = sum(array);
		if (sum > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= sum;
		}
	}

	public static void sumScale(float[] array) {
		float sum = sum(array);
		if (sum > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= sum;
		}
	}

	public static double[] sumScale(int[] array) {
		double norm = sum(array);
		double[] ret = null;
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			ret = new double[len];
			for (int i = 0; i < len; i++)
				ret[i] /= norm;
		}
		return ret;
	}

	public static List<Double> sumScale(List<? extends Number> list) {
		double norm = sum(list);
		List<Double> ret = null;
		if (norm > Double.MIN_VALUE) {
			ret = new ArrayList<Double>();
			int len = list.size();
			for (int i = 0; i < len; i++)
				ret.add(list.get(i).doubleValue() / norm);
		}

		return ret;
	}

	/**
	 * Normalize Array Based on Maximum Value
	 * 
	 * @param array
	 */
	public static <T extends Number> Double[] maxScale(T[] array) {
		int len = 0;
		if (array == null || (len = array.length) == 0) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		Double[] ret = new Double[len];
		double max = array[0].doubleValue();
		if (array.length == 1)
			ret[0] = max;

		for (int i = 1; i < len; i++)
			max = max > array[i].doubleValue() ? max : array[i].doubleValue();

		if (max > Double.MIN_VALUE) {
			for (int i = 0; i < len; i++)
				ret[i] = array[i].doubleValue() / max;
		}
		return ret;
	}

	public static void maxScale(double[] array) {
		double max = max(array);
		if (max > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= max;
		}
	}

	public static void maxScale(float[] array) {
		float max = max(array);
		if (max > Double.MIN_VALUE) {
			int len = array.length;
			for (int i = 0; i < len; i++)
				array[i] /= max;
		}
	}

	public static double[] maxScale(int[] array) {
		double norm = max(array);
		double[] ret = null;
		if (norm > Double.MIN_VALUE) {
			int len = array.length;
			ret = new double[len];
			for (int i = 0; i < len; i++)
				ret[i] /= norm;
		}
		return ret;
	}

	public static List<Double> maxScale(List<? extends Number> list) {
		int len = 0;
		if (list == null || (len = list.size()) == 0) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		List<Double> ret = new ArrayList<Double>();
		double max = list.get(0).doubleValue();
		if (len == 1)
			ret.add(max);

		for (int i = 1; i < len; i++) {
			max = max > list.get(i).doubleValue() ? max : list.get(i).doubleValue();
			ret.add(0.0);
		}

		if (max > Double.MIN_VALUE) {
			for (int i = 0; i < len; i++)
				ret.set(i, list.get(i).doubleValue() / max);
		}
		return ret;
	}

	/**
	 * ZScore Normalization
	 * 
	 * @param array
	 */
	public static <T extends Number> Double[] zscoreScale(T[] array) {
		int n = array.length;
		double mean = sum(array) / n;
		double std = 0;
		for (T t : array) {
			double val = t.doubleValue() - mean;
			std += val * val;
		}
		Double[] ret = new Double[n];
		if (std == 0) {
			mean = 0;
			std = 1;
		} else
			std = Math.sqrt(std / (n - 1));

		for (int i = 0; i < n; i++)
			ret[i] = (array[i].doubleValue() - mean) / std;
		return ret;
	}

	public static void zscoreScale(double[] array) {
		int n = array.length;
		double mean = sum(array) / n;
		double std = 0;
		for (int i = 0; i < n; i++) {
			double val = array[i] - mean;
			std += val * val;
		}
		if (std > 0) {
			std = Math.sqrt(std / (n - 1));
			for (int i = 0; i < n; i++)
				array[i] = (array[i] - mean) / std;
		}
	}

	public static void zscoreScale(float[] array) {
		int n = array.length;
		float mean = sum(array) / n;
		float std = 0;
		for (int i = 0; i < n; i++) {
			float val = array[i] - mean;
			std += val * val;
		}
		if (std > 0) {
			std = (float) Math.sqrt(std / (n - 1));
			for (int i = 0; i < n; i++)
				array[i] = (array[i] - mean) / std;
		}
	}

	public static double[] zscoreScale(int[] array) {
		int len = array.length;
		double mean = mean(array);
		double std = 0;
		for (int i = 0; i < len; i++) {
			double val = array[i] - mean;
			std += val * val;
		}

		double[] ret = new double[len];
		if (std > 0) {
			std = Math.sqrt(std / (len - 1));
			for (int i = 0; i < len; i++)
				ret[i] = (array[i] - mean) / std;
		}
		return ret;
	}

	public static List<Double> zscoreScale(List<? extends Number> list) {
		int sz = list.size();
		double mean = mean(list);
		double std = 0;
		for (int i = 0; i < sz; i++) {
			double val = list.get(i).doubleValue() - mean;
			std += val * val;
		}
		if (std == 0) {
			mean = 0;
			std = 1;
		} else
			std = Math.sqrt(std / (sz - 1));

		List<Double> ret = new ArrayList<Double>();
		for (int i = 0; i < sz; i++)
			ret.add((list.get(i).doubleValue() - mean) / std);
		return ret;
	}

	/**
	 * [0,1] Normalization
	 * 
	 * @param array
	 */
	public static <T extends Number> Double[] scale(T[] array, double a, double b) {
		double max = Double.MAX_VALUE;
		double min = Double.MIN_VALUE;

		int sz = array.length;
		double val = 0;
		for (T t : array) {
			val = t.doubleValue();
			if (val > max)
				max = val;
			if (val < min)
				min = val;
		}

		Double[] ret = new Double[sz];
		double alpha = -1;
		if (max > min & b > a) {
			alpha = (b - a) / (max - min);
			for (int i = 0; i < sz; i++)
				ret[i] = alpha * (array[i].doubleValue() - min) + a;
		} else
			for (int i = 0; i < sz; i++)
				ret[i] = array[i].doubleValue();
		return ret;
	}

	public static void scale(double[] array, double a, double b) {
		double max = MathUtils.max(array);
		double min = MathUtils.min(array);

		int sz = array.length;
		double alpha = -1;
		if (max > min) {
			alpha = (b - a) / (max - min);
			for (int i = 0; i < sz; i++)
				array[i] = (float) (alpha * (array[i] - min) + a);
		}
	}

	public static void scale(float[] array, double a, double b) {
		double max = MathUtils.max(array);
		double min = MathUtils.min(array);

		int sz = array.length;
		double alpha = -1;
		if (max > min) {
			alpha = (b - a) / (max - min);
			for (int i = 0; i < sz; i++)
				array[i] = (float) (alpha * (array[i] - min) + a);
		}
	}

	public static double[] scale(int[] array, double a, double b) {
		double max = MathUtils.max(array);
		double min = MathUtils.min(array);

		int sz = array.length;
		double[] ret = new double[sz];
		double alpha = -1;
		if (max > min) {
			alpha = (b - a) / (max - min);
			for (int i = 0; i < sz; i++)
				ret[i] = alpha * (array[i] - min) + a;
		} else
			for (int i = 0; i < sz; i++)
				ret[i] = array[i] * 1.0;
		return ret;
	}

	public static List<Double> scale(List<? extends Number> list, double a, double b) {
		double max = Double.MAX_VALUE;
		double min = Double.MIN_VALUE;

		int sz = list.size();
		double val = 0;
		for (int i = 0; i < sz; i++) {
			val = list.get(i).doubleValue();
			if (val > max)
				max = val;
			if (val < min)
				min = val;
		}

		List<Double> ret = new ArrayList<Double>();
		double alpha = -1;
		if (max > min & b > a) {
			alpha = (b - a) / (max - min);
			for (int i = 0; i < sz; i++)
				ret.add(alpha * (list.get(i).doubleValue() - min) + a);
		} else
			for (int i = 0; i < sz; i++)
				ret.add(list.get(i).doubleValue());
		return ret;
	}

	/**
	 * Constrain Within
	 * 
	 * @param val
	 * @param l
	 * @param u
	 * @return val constraint in [l, u]
	 */
	public static int constrain(int val, int l, int u) {
		if (val < l)
			val = l;
		if (val > u)
			val = u;
		return val;
	}

	public static long constrain(long val, long l, long u) {
		if (val < l)
			val = l;
		if (val > u)
			val = u;
		return val;
	}

	public static float constrain(float val, float l, float u) {
		if (val < l)
			val = l;
		if (val > u)
			val = u;
		return val;
	}

	public static double constrain(double val, double l, double u) {
		if (val < l)
			val = l;
		if (val > u)
			val = u;
		return val;
	}

	/**
	 * @param array
	 * @return largest
	 */
	public static <T extends Comparable<? super T>> T max(T[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		T max = array[0];
		if (array.length == 1)
			return max;

		int len = array.length;
		for (int i = 1; i < len; i++)
			max = max.compareTo(array[i]) > 0 ? max : array[i];

		return max;
	}

	public static double max(double[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		double max = array[0];
		if (array.length == 1)
			return max;

		int len = array.length;
		for (int i = 1; i < len; i++)
			max = (max > array[i]) ? max : array[i];

		return max;
	}

	public static float max(float[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		float max = array[0];

		if (array.length == 1)
			return max;

		int len = array.length;
		for (int i = 1; i < len; i++)
			max = (max > array[i]) ? max : array[i];

		return max;
	}

	public static int max(int[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		int max = array[0];

		if (array.length == 1)
			return max;

		int len = array.length;
		for (int i = 1; i < len; i++)
			max = (max > array[i]) ? max : array[i];

		return max;
	}

	public static <T extends Number> T max(List<T> list) {
		if (list.isEmpty()) {
			System.err.println("List is Empty.");
			System.exit(0);
		}

		T max = list.get(0);

		int len = list.size();
		if (len == 1)
			return max;

		for (int i = 1; i < len; i++)
			max = (max.doubleValue() > list.get(i).doubleValue()) ? max : list.get(i);

		return max;
	}

	/**
	 * Search the smallest element
	 * 
	 * @param array
	 * @return smallest
	 */
	public static <T extends Number> T min(T[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		T min = array[0];
		if (array.length == 1)
			return min;

		int len = array.length;
		for (int i = 1; i < len; i++)
			min = (min.doubleValue() < array[i].doubleValue()) ? min : array[i];
		return min;
	}

	public static double min(double[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		double min = array[0];
		if (array.length == 1)
			return min;

		int len = array.length;
		for (int i = 1; i < len; i++)
			min = (min < array[i]) ? min : array[i];

		return min;
	}

	public static float min(float[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		float min = array[0];
		if (array.length == 1)
			return min;

		int len = array.length;
		for (int i = 1; i < len; i++)
			min = (min < array[i]) ? min : array[i];

		return min;
	}

	public static int min(int[] array) {
		if (array == null) {
			System.err.println("Array is Empty.");
			System.exit(0);
		}

		int min = array[0];

		int len = array.length;
		if (len == 1)
			return min;

		for (int i = 1; i < len; i++)
			min = (min < array[i]) ? min : array[i];

		return min;
	}

	public static <T extends Number> T min(List<T> list) {
		if (list.isEmpty()) {
			System.err.println("List is Empty.");
			System.exit(0);
		}

		T min = list.get(0);

		int len = list.size();
		if (len == 1)
			return min;

		for (int i = 1; i < len; i++)
			min = (min.doubleValue() < list.get(i).doubleValue()) ? min : list.get(i);

		return min;
	}

	/**
	 * Computing Cosine Similarity between Two Vector
	 * 
	 * @param x
	 * @param y
	 * @return cosine<x, y>
	 */
	public static <T extends Number, P extends Number> double getSimCosine(T[] x, P[] y) {
		if (x == null || y == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		if (x.length != y.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double norm1 = 0;
		double norm2 = 0;
		double inprod = 0;
		int n = x.length;
		for (int i = 0; i < n; i++) {
			inprod += x[i].doubleValue() * y[i].doubleValue();
			norm1 += x[i].doubleValue() * x[i].doubleValue();
			norm2 += y[i].doubleValue() * y[i].doubleValue();
		}

		double ret = 0;
		if (norm1 * norm2 > Double.MIN_VALUE)
			ret = inprod / (Math.sqrt(norm1 * norm2));
		return ret;
	}

	/**
	 * Computing Cosine Similarity between Two Vector
	 * 
	 * @param x
	 * @param y
	 * @return 余弦值
	 */
	public static double getSimCosine(double[] x, double[] y) {
		if (x == null || y == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		if (x.length != y.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double norm1 = 0;
		double norm2 = 0;
		double inprod = 0;
		int n = x.length;
		for (int i = 0; i < n; i++) {
			inprod += x[i] * y[i];
			norm1 += x[i] * x[i];
			norm2 += y[i] * y[i];
		}

		double ret = 0;
		if (norm1 * norm2 > Double.MIN_VALUE)
			ret = inprod / (Math.sqrt(norm1 * norm2));
		return ret;
	}

	public static float getSimCosine(float[] x, float[] y) {
		if (x == null || y == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		if (x.length != y.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		float norm1 = 0;
		float norm2 = 0;
		float inprod = 0;
		int n = x.length;
		for (int i = 0; i < n; i++) {
			inprod += x[i] * y[i];
			norm1 += x[i] * x[i];
			norm2 += y[i] * y[i];
		}

		float ret = 0;
		if (norm1 * norm2 > Float.MIN_VALUE)
			ret = (float) (inprod / (Math.sqrt(norm1 * norm2)));
		return ret;
	}

	public static double getSimCosine(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double norm1 = 0;
		double norm2 = 0;
		double inprod = 0;
		int n = arr1.length;
		for (int i = 0; i < n; i++) {
			inprod += arr1[i] * arr2[i];
			norm1 += arr1[i] * arr1[i];
			norm2 += arr2[i] * arr2[i];
		}

		double ret = 0;
		if (norm1 * norm2 > Double.MIN_VALUE)
			ret = inprod / (Math.sqrt(norm1 * norm2));
		return ret;
	}

	public static <T extends Number, P extends Number> double getSimCosine(List<T> list1, List<P> list2) {
		if (list1.isEmpty() || list2.isEmpty()) {
			System.err.println("At least one list is Empty.");
			System.exit(0);
		}

		int len1 = list1.size();
		int len2 = list2.size();
		if (len1 != len2) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		double norm1 = 0;
		double norm2 = 0;
		double inprod = 0;
		for (int i = 0; i < len1; i++) {
			double val1 = list1.get(i).doubleValue();
			double val2 = list2.get(i).doubleValue();
			inprod += val1 * val2;
			norm1 += val1 * val1;
			norm2 += val2 * val2;
		}

		double ret = 0;
		if (norm1 * norm2 > Double.MIN_VALUE)
			ret = inprod / (Math.sqrt(norm1 * norm2));

		return ret;
	}

	/**
	 * 计算加权余弦相似度
	 * 
	 * @param arr1
	 * @param arr2
	 * @param weight
	 * @return weighted cosine similarity
	 */
	public static <T extends Number, P extends Number> double getWeightedSimCosine(T[] arr1, T[] arr2, P[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		double ret = 0;
		if (arr1.length == arr2.length && arr2.length == weight.length) {
			double norm1 = 0;
			double norm2 = 0;
			double inprod = 0;
			int n = arr1.length;
			for (int i = 0; i < n; i++) {
				inprod += arr1[i].doubleValue() * arr2[i].doubleValue() * weight[i].doubleValue();
				norm1 += arr1[i].doubleValue() * arr1[i].doubleValue() * weight[i].doubleValue();
				norm2 += arr2[i].doubleValue() * arr2[i].doubleValue() * weight[i].doubleValue();
			}

			if (norm1 * norm2 > Float.MIN_VALUE)
				ret = inprod / (Math.sqrt(norm1 * norm2));
		} else {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		return ret;
	}

	public static double getWeightedSimCosine(double[] arr1, double[] arr2, double[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		double ret = 0;
		if (arr1.length == arr2.length && arr2.length == weight.length) {
			double norm1 = 0;
			double norm2 = 0;
			double inprod = 0;
			int n = arr1.length;
			for (int i = 0; i < n; i++) {
				inprod += arr1[i] * arr2[i] * weight[i];
				norm1 += arr1[i] * arr1[i] * weight[i];
				norm2 += arr2[i] * arr2[i] * weight[i];
			}

			if (norm1 * norm2 > Float.MIN_VALUE)
				ret = inprod / (Math.sqrt(norm1 * norm2));
		} else {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		return ret;
	}

	public static float getWeightedSimCosine(float[] arr1, float[] arr2, float[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		float ret = 0;
		if (arr1.length == arr2.length && arr2.length == weight.length) {
			float norm1 = 0;
			float norm2 = 0;
			float inprod = 0;
			int n = arr1.length;
			for (int i = 0; i < n; i++) {
				inprod += arr1[i] * arr2[i] * weight[i];
				norm1 += arr1[i] * arr1[i] * weight[i];
				norm2 += arr2[i] * arr2[i] * weight[i];
			}

			if (norm1 * norm2 > Float.MIN_VALUE)
				ret = (float) (inprod / (Math.sqrt(norm1 * norm2)));
		} else {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}
		return ret;
	}

	public static double getWeightedSimCosine(int[] arr1, int[] arr2, double[] weight) {
		if (arr1 == null || arr2 == null) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		double ret = 0;
		if (arr1.length == arr2.length && arr2.length == weight.length) {
			double norm1 = 0;
			double norm2 = 0;
			double inprod = 0;
			int n = arr1.length;
			for (int i = 0; i < n; i++) {
				inprod += arr1[i] * arr2[i] * weight[i];
				norm1 += arr1[i] * arr1[i] * weight[i];
				norm2 += arr2[i] * arr2[i] * weight[i];
			}

			if (norm1 * norm2 > Float.MIN_VALUE)
				ret = inprod / (Math.sqrt(norm1 * norm2));
		} else {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		return ret;
	}

	public static <U extends Number, V extends Number> double getWeightedSimCosine(List<U> arr1, List<U> arr2,
			List<V> weight) {
		if (arr1.isEmpty() || arr2.isEmpty()) {
			System.err.println("At least one array is Empty.");
			System.exit(0);
		}

		double ret = 0;
		int len = arr1.size();
		if (len == arr2.size() && arr2.size() == weight.size()) {
			double norm1 = 0;
			double norm2 = 0;
			double inprod = 0;
			for (int i = 0; i < len; i++) {
				double val1 = arr1.get(i).doubleValue();
				double val2 = arr2.get(i).doubleValue();
				double w = weight.get(i).doubleValue();
				inprod += val1 * val2 * w;
				norm1 += val1 * val1 * w;
				norm2 += val2 * val2 * w;
			}

			if (norm1 * norm2 > Float.MIN_VALUE)
				ret = inprod / (Math.sqrt(norm1 * norm2));

		} else {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		return ret;
	}

	/**
	 * 计算两个字符串的Hamming距离（等长字符串，相同位置不同字符的个数）
	 * 
	 * @param str1
	 * @param str2
	 * @return 不同字符的个数
	 */
	public static int hammingDistance(String str1, String str2) {
		int len = str1.length();
		if (len != str2.length()) {
			System.err.println("Inconsistent Dimensions.");
			System.exit(0);
		}

		int concordant = 0;
		for (int i = 0; i < len; i++) {
			if (str1.charAt(i) == str2.charAt(i))
				concordant++;
		}
		return len - concordant;
	}

	/**
	 * 计算两个数组的欧式距离
	 * 
	 * @param arr1
	 * @param arr2
	 * @return sqrt(∑(arr1[i] - arr2[i]^2))
	 */
	public static <T extends Number, P extends Number> double euclideanDistance(T[] arr1, P[] arr2) {
		return getEuclidNorm(diff(arr1, arr2));
	}

	public static double euclideanDistance(double[] arr1, double[] arr2) {
		return getEuclidNorm(diff(arr1, arr2));
	}

	public static float euclideanDistance(float[] arr1, float[] arr2) {
		return getEuclidNorm(diff(arr1, arr2));
	}

	public static double euclideanDistance(int[] arr1, int[] arr2) {
		return getEuclidNorm(diff(arr1, arr2));
	}

	public static double euclideanDistance(List<? extends Number> list1, List<? extends Number> list2) {
		return getEuclidNorm(diff(list1, list2));
	}

	/**
	 * 放大
	 * 
	 * @param array
	 * @param factor
	 * @return 数组放大
	 */
	public static <T extends Number> Double[] scale(T[] array, double factor) {
		int len = array.length;
		Double arr[] = new Double[len];
		for (int i = 0; i < len; i++)
			arr[i] = array[i].doubleValue() * factor;

		return arr;
	}

	public static double[] scale(double[] array, double factor) {
		int len = array.length;
		double arr[] = new double[len];
		for (int i = 0; i < len; i++)
			arr[i] = array[i] * factor;

		return arr;
	}

	public static float[] scale(float[] array, float factor) {
		int len = array.length;
		float arr[] = new float[len];
		for (int i = 0; i < len; i++)
			arr[i] = array[i] * factor;

		return arr;
	}

	public static double[] scale(int[] array, double factor) {
		int len = array.length;
		double arr[] = new double[len];
		for (int i = 0; i < len; i++)
			arr[i] = array[i] * factor;

		return arr;
	}

	public static List<Double> scale(List<? extends Number> list, double factor) {
		int sz = list.size();
		List<Double> ret = new ArrayList<Double>();
		for (int i = 0; i < sz; i++)
			ret.add(list.get(i).doubleValue() * factor);
		return ret;
	}

	/**
	 * 加和
	 * 
	 * @param arr1
	 * @param arr2
	 * @return arr[i] = arr1[i] + arr2[i]
	 */
	public static <T extends Number, P extends Number> Double[] add(T[] arr1, P[] arr2) {
		int len = arr1.length;
		Double[] arr = new Double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i].doubleValue() + arr2[i].doubleValue();
		return arr;
	}

	public static double[] add(double[] arr1, double[] arr2) {
		int len = arr1.length;
		double[] arr = new double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] + arr2[i];

		return arr;
	}

	public static float[] add(float[] arr1, float[] arr2) {
		int len = arr1.length;
		float[] arr = new float[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] + arr2[i];
		return arr;
	}

	public static int[] add(int[] arr1, int[] arr2) {
		int len = arr1.length;
		int[] arr = new int[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] + arr2[i];
		return arr;
	}

	public static <T extends Number> List<Double> add(List<T> list1, List<T> list2) {
		int len = list1.size();
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < len; i++)
			list.add(list1.get(i).doubleValue() + list2.get(i).doubleValue());

		return list;
	}

	/**
	 * 差
	 * 
	 * @param arr1
	 * @param arr2
	 * @return arr[i] = arr1[i] - arr2[i]
	 */
	public static <T extends Number, P extends Number> Double[] diff(T[] arr1, P[] arr2) {
		int len = arr1.length;
		Double[] arr = new Double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i].doubleValue() - arr2[i].doubleValue();
		return arr;
	}

	public static double[] diff(double[] arr1, double[] arr2) {
		int len = arr1.length;
		double[] arr = new double[len];

		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] - arr2[i];

		return arr;
	}

	public static float[] diff(float[] arr1, float[] arr2) {
		int len = arr1.length;
		float[] arr = new float[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] - arr2[i];

		return arr;
	}

	public static int[] diff(int[] arr1, int[] arr2) {
		int len = arr1.length;
		int[] arr = new int[len];

		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] - arr2[i];

		return arr;
	}

	public static <T extends Number, P extends Number> List<Double> diff(List<T> list1, List<P> list2) {
		int len = list1.size();
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < len; i++)
			list.add(list1.get(i).doubleValue() - list2.get(i).doubleValue());

		return list;
	}

	/**
	 * Linear Combination
	 * 
	 * @param arr1
	 * @param f1
	 * @param arr2
	 * @param f2
	 * @return arr[i] = arr1[i]*f1 + arr2[i]*f2
	 */
	public static <T extends Number, P extends Number> Double[] linComb(T[] arr1, double f1, P[] arr2, double f2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int len = arr1.length;
		Double[] arr = new Double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i].doubleValue() * f1 + arr2[i].doubleValue() * f2;

		return arr;
	}

	/**
	 * 线性组合
	 * 
	 * @param arr1
	 * @param f1
	 * @param arr2
	 * @param f2
	 * @return arr[i] = arr1[i]*f1 + arr2[i]*f2
	 */
	public static double[] linComb(double[] arr1, double f1, double[] arr2, double f2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int len = arr1.length;
		double[] arr = new double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] * f1 + arr2[i] * f2;

		return arr;
	}

	public static float[] linComb(float[] arr1, float f1, float[] arr2, float f2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int len = arr1.length;
		float[] arr = new float[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] * f1 + arr2[i] * f2;

		return arr;
	}

	public static double[] linComb(int[] arr1, double f1, int[] arr2, double f2) {
		if (arr1 == null || arr2 == null) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (arr1.length != arr2.length) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int len = arr1.length;
		double[] arr = new double[len];
		for (int i = 0; i < len; i++)
			arr[i] = arr1[i] * f1 + arr2[i] * f2;

		return arr;
	}

	public static <T extends Number, P extends Number> List<Double> linComb(List<T> list1, P f1, List<T> list2, P f2) {
		if (list1.isEmpty() || list2.isEmpty()) {
			System.err.println("Empty Array.");
			System.exit(0);
		}
		if (list1.size() != list2.size()) {
			System.err.println("Dimensions are inconsistent.");
			System.exit(0);
		}

		int len = list1.size();
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < len; i++)
			list.add(list1.get(i).doubleValue() * f1.doubleValue() + list2.get(i).doubleValue() * f2.doubleValue());
		return list;
	}

	/**
	 * 将数组转存为链表
	 * 
	 * @param array
	 * @return list.get(i) = array[i]
	 */
	public static List<Double> toList(double[] array) {
		List<Double> list = new ArrayList<Double>();
		int len = array.length;
		for (int i = 0; i < len; i++)
			list.add(array[i]);
		return list;
	}

	public static List<Float> toList(float[] array) {
		List<Float> list = new ArrayList<Float>();
		int len = array.length;
		for (int i = 0; i < len; i++)
			list.add(array[i]);
		return list;
	}

	public static List<Integer> toList(int[] array) {
		List<Integer> list = new ArrayList<Integer>();
		int len = array.length;
		for (int i = 0; i < len; i++)
			list.add(array[i]);

		return list;
	}

	public static <T> List<T> toList(T[] array) {
		return Arrays.asList(array);
	}

	/**
	 * 四舍五入
	 * 
	 * @param valueToRound
	 * @param numberOfDecimalPlaces
	 * @return 精确到小数点后某几位
	 */
	public static double round(double valueToRound, int numberOfDecimalPlaces) {
		double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
		double interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	public static float round(float valueToRound, int numberOfDecimalPlaces) {
		float multipicationFactor = (float) Math.pow(10, numberOfDecimalPlaces);
		float interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	/**
	 * 随机生成指定范围内的十进制数,并要求精确到小数点后precision位
	 * 
	 * @param a
	 * @param b
	 * @param n
	 *            digits
	 * @return random number in [a, b] with precision
	 */
	public static double rand(double a, double b, int n) {
		double rand = a + Math.random() * (b - a);
		StringBuilder digits = new StringBuilder();
		while (n-- > 0)
			digits.append("0");

		DecimalFormat dFormat = new DecimalFormat("#." + digits.toString());
		return new Double(dFormat.format(rand));
	}

	/**
	 * Sampling n integers in [a, b]
	 * 
	 * @param a
	 * @param b
	 * @param n
	 * @return n different integers in [a,b]
	 */
	public static List<Integer> sample(int a, int b, int n) {
		if (b <= a)
			throw new IllegalArgumentException("b must be larger than a");
		else if (b - a + 1 < n)
			throw new IllegalArgumentException("[a, b] has less than n integers");

		List<Integer> list = new ArrayList<Integer>();
		for (int i = b; i >= a; i--)
			list.add(i);
		Collections.shuffle(list);
		return list.subList(0, n);
	}

	/**
	 * @param a
	 * @param b
	 * @return select one integer in [a, b] randomly
	 */
	public static int rand(int a, int b) {
		if (b <= a)
			throw new IllegalArgumentException("b must be larger than a");
		return a + new Random().nextInt(b - a + 1);
	}

	/**
	 * @param a
	 * @param b
	 * @param n
	 * @return select n integers in [a, b] randomly
	 */
	public static List<Integer> rand(int a, int b, int n) {
		if (b <= a)
			throw new IllegalArgumentException("b must be larger than a");

		int len = b - a;
		Random rand = new Random();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < n; i++)
			list.add(a + rand.nextInt(len + 1));
		return list;
	}

	/**
	 * produce a number in Gaussian distribution randomly
	 * 
	 * @param mu
	 * @param sigma
	 * @return x~N(mu, sigma)
	 */
	public static double randNorm(double mu, double sigma) {
		Random rand = new Random();
		return mu + rand.nextGaussian() * sigma;
	}

	public static float randNorm(float mu, float sigma) {
		Random rand = new Random();
		return (float) (mu + rand.nextGaussian() * sigma);
	}

	/**
	 * produce uniformly distributed number randomly
	 * 
	 * @param a
	 * @param b
	 * @return x ~ U(a,b)
	 */
	public static double randUniform(double a, double b) {
		return a + Math.random() * (b - a);
	}

	public static float randUniform(float a, float b) {
		return (float) (a + Math.random() * (b - a));
	}

	/**
	 * 生成给定长度的随机概率分布
	 * 
	 * @param dim
	 * @return prob distribution
	 */
	public static float[] randDistribution(int dim) {
		float[] p = new float[dim];
		for (int i = 0; i < dim; i++)
			p[i] = (float) Math.random();
		sumScale(p);
		return p;
	}

	/**
	 * 生成指定大小的行随机方阵
	 * 
	 * @param dim
	 * @return 随机方阵
	 */
	public static float[][] stochasticMatrix(int dim) {
		float[][] matrix = new float[dim][dim];
		for (int i = 0; i < dim; i++)
			matrix[i] = randDistribution(dim);

		return matrix;
	}

	/**
	 * 获取数组array升序（ascend = true)/降序（ascend = false)排名列表
	 * 
	 * @param array
	 * @param ascend
	 * @return rank of array in ascending or descending order
	 */
	public static <T extends Comparable<? super T>> int[] getRank(T[] array, boolean ascend) {
		int len = array.length;
		if (len <= 0)
			return null;

		int inv = ascend ? 1 : -1;
		return IntStream.range(0, len).boxed().sorted((i, j) -> inv * (array[i].compareTo(array[j]))).mapToInt(e -> e)
				.toArray();
	}

	public static int[] getRank(double[] array, boolean ascend) {
		int len = array.length;
		int[] rank = new int[len];
		for (int i = 0; i < len; i++)
			rank[i] = i;

		int idx = (ascend) ? 1 : -1;
		for (int i = 0; i < len - 1; i++) {
			int max = i;
			for (int j = i + 1; j < len; j++)
				if (array[rank[max]] * idx > array[rank[j]] * idx)
					max = j;

			// swap
			int tmp = rank[i];
			rank[i] = rank[max];
			rank[max] = tmp;
		}
		return rank;
	}

	public static int[] getRank(float[] array, boolean ascend) {
		int sz = array.length;
		int[] rank = new int[sz];
		for (int i = 0; i < sz; i++)
			rank[i] = i;

		int idx = (ascend) ? 1 : -1;
		for (int i = 0; i < sz - 1; i++) {
			int max = i;
			for (int j = i + 1; j < sz; j++)
				if (array[rank[max]] * idx > array[rank[j]] * idx)
					max = j;

			// swap
			int tmp = rank[i];
			rank[i] = rank[max];
			rank[max] = tmp;
		}
		return rank;
	}

	public static int[] getRank(int[] array, boolean ascend) {
		int len = array.length;
		int[] rank = new int[len];
		for (int i = 0; i < len; i++)
			rank[i] = i;

		int idx = (ascend) ? 1 : -1;
		for (int i = 0; i < len - 1; i++) {
			int max = i;
			for (int j = i + 1; j < len; j++)
				if (array[rank[max]] * idx > array[rank[j]] * idx)
					max = j;

			// swap
			int tmp = rank[i];
			rank[i] = rank[max];
			rank[max] = tmp;
		}
		return rank;
	}

	/**
	 * 获取list升序（ascend = true)/降序（ascend = false)排名列表
	 * 
	 * @param list
	 * @param ascend
	 * @return rank of list in ascending or descending order
	 */
	public static <T extends Comparable<? super T>> int[] getRank(List<T> list, boolean ascend) {
		int size = list.size();
		if (size <= 0)
			return null;

		int inv = ascend ? 1 : -1;
		return IntStream.range(0, size).boxed().sorted((i, j) -> inv * (list.get(i).compareTo(list.get(j))))
				.mapToInt(e -> e).toArray();
	}

	/**
	 * @param array
	 * @return mean
	 */
	public static <T extends Number> double mean(T[] array) {
		int len = array.length;
		return 1.0d * sum(array) / len;
	}

	public static double mean(double[] array) {
		int len = array.length;
		return sum(array) / len;
	}

	public static float mean(float[] array) {
		int len = array.length;
		return sum(array) / len;
	}

	public static double mean(int[] array) {
		int len = array.length;
		return 1.0d * sum(array) / len;
	}

	public static double mean(List<? extends Number> list) {
		int len = list.size();
		return 1.0d * sum(list) / len;
	}

	/**
	 * @param array
	 * @return array的中值
	 */
	public static <T extends Comparable<? super T>> T median(T[] array) {
		int len = array.length;
		List<T> list = toList(array);
		Collections.sort(list);
		return list.get(len / 2);
	}

	public static double median(double[] array) {
		List<Double> list = toList(array);
		int len = array.length;
		Collections.sort(list);
		return list.get(len / 2);
	}

	public static float median(float[] array) {
		List<Float> list = toList(array);
		int len = array.length;
		Collections.sort(list);
		return list.get(len / 2);
	}

	public static int median(int[] array) {
		List<Integer> list = toList(array);
		int len = array.length;
		Collections.sort(list);
		return list.get(len / 2);
	}

	public static <T extends Comparable<? super T>> T median(List<T> list) {
		int len = list.size();
		Collections.sort(list);
		return list.get(len / 2);
	}

	/**
	 * @param array
	 * @return array的方差
	 */
	public static <T extends Number> double var(T[] array) {
		int len = array.length;
		if (len == 1)
			return 0;

		double mean = mean(array), sum = 0;
		for (T val : array)
			sum += (val.doubleValue() - mean) * (val.doubleValue() - mean);
		return sum / (len - 1);
	}

	public static double var(double[] array) {
		int len = array.length;
		if (len == 1)
			return 0;

		double mean = mean(array), sum = 0;
		for (double val : array)
			sum += (val - mean) * (val - mean);

		return sum / (len - 1);
	}

	public static float var(float[] array) {
		int len = array.length;
		if (len == 1)
			return 0;

		float mean = mean(array), sum = 0;

		for (float val : array)
			sum += (val - mean) * (val - mean);

		return sum / (len - 1);
	}

	public static double var(int[] array) {
		int len = array.length;
		if (len == 1)
			return 0;

		double mean = mean(array), sum = 0;

		for (int val : array)
			sum += (val - mean) * (val - mean);

		return sum / (len - 1);
	}

	public static <T extends Number> double var(List<T> list) {
		int len = list.size();
		if (len == 1)
			return 0;

		double mean = mean(list);
		double sum = 0;

		for (T t : list)
			sum += (t.doubleValue() - mean) * (t.doubleValue() - mean);

		return sum / (len - 1);
	}

	/**
	 * @param array
	 * @return array的标准差
	 */
	public static double stdvar(double[] array) {
		return Math.sqrt(var(array));
	}

	public static double stdvar(float[] array) {
		return Math.sqrt(var(array));
	}

	public static double stdvar(int[] array) {
		return Math.sqrt(var(array));
	}

	public static <T extends Number> double stdvar(List<T> list) {
		return Math.sqrt(var(list));
	}

	/**
	 * 建立字母与数字之间的映射关系
	 * <p>
	 * 使用26个英文字母编码正整数,类似于26进制(无零元)：<br/>
	 * a-z: 1-26<br/>
	 * aa-az: 27-52<br/>
	 * ba-bz: 53-78<br/>
	 * ......<br/>
	 * aaa-zzz: 703-18278<br/>
	 * 
	 * 比如：微软Excel表列名称使用的就是这种编码方式
	 * </p>
	 * 
	 * 将索引数值转换为字母串
	 * 
	 * @param num
	 * @return alpha represents the num
	 */
	public static String num2alpha(int num) {
		if (num < 1)
			System.err.println("Idx is less than 1!");

		List<Integer> remainderList = new ArrayList<Integer>();

		int remainder = 0;
		// 取余定元
		while ((remainder = num % 26) != num) {
			num = (num - remainder) / 26;
			remainderList.add(remainder);
		}
		remainderList.add(remainder);

		int product = 1;// 判断零元存在否
		for (int i = 0; i < remainderList.size(); i++)
			product *= remainderList.get(i);

		if (product == 0)
			// 借位替换头部零元
			while (remainderList.get(remainderList.size() - 1) > 0) {
			for (int j = remainderList.size() - 1; j >= 0; j--) {
			if (remainderList.get(j) == 0) {
			remainderList.set(j, 26);
			remainder = remainderList.get(j + 1);
			remainderList.set(j + 1, remainder - 1);
			}
			}
			}

		// 转换成字母
		String alpha = "";
		for (int i = remainderList.size() - 1; i >= 0; i--) {
			remainder = remainderList.get(i);
			if (remainder == 0)// 剔除尾部零元
				continue;

			alpha += (char) (remainder + 64);
		}
		return alpha;
	}

	/**
	 * 将字母串转换为索引数值
	 * 
	 * @param alpha
	 * @return num represented by alpha
	 */
	public static int alpha2num(String alpha) {
		alpha = alpha.toUpperCase();
		int idx = 0, len = alpha.length();
		for (int i = 0; i < len; i++)
			idx += (alpha.codePointAt(i) - 64) * Math.pow(26, len - i - 1);
		return idx;
	}

	/**
	 * <p>
	 * <b>BitCompute执行位计算,并以位的形式显示:</b>
	 * </p>
	 * <p>
	 * 首先介绍几种基本的位运算, HashCode, BitSet和MessageDigest
	 * </p>
	 * <p>
	 * 对于32位(4byte)的Integer数字,最高位若是1,则表示负数,比如1,其二进制编码：<br/>
	 * 00000000 00000000 00000000 00000001<br/>
	 * 若对其左移31位(1 << 31),即<br/>
	 * 10000000 00000000 00000000 00000000,表示负数,而如果左移30位,有<br/>
	 * 01000000 00000000 00000000 00000000,表示整数2^30<br/>
	 * 我们知道最大的整型数是2^31-1=Integer.MAX_VALUE=2,147,483,647,二进制形式为：<br/>
	 * 01111111 11111111 11111111 11111111<br/>
	 * 在此基础上+1:<br/>
	 * 01111111 11111111 11111111 11111111 ( 2^31-1 )<br/>
	 * + 00000000 00000000 00000000 00000001 ( 1 )<br/>
	 * = 10000000 00000000 00000000 00000000 ( 1 << 31 )
	 * </p>
	 * 
	 * <p>
	 * 如果将所有的整型数字连成环状,从0开始顺时针依次为1,2,...,则Integer.MAX_VALUE与<br/>
	 * Integer.MIN_VALUE相邻,从而会出现看似矛盾的计算结果: 2,147,483,647 + 1 = <br/>
	 * -2,147,483,648, 2,147,483,647 + 2 = -2,147,483,647<br/>
	 * 由于任何类型的数字在计算机中都有一个表示范围,如果超出这个范围就会出现异常,<br/>
	 * 但若通过强制转换到能够表示更大范围的类型时就可以解决异常问题,比如:<br/>
	 * (long) Integer.MAX_VALUE + 1 = 2,147,483,648
	 * </p>
	 * 
	 * @param n
	 * @return 二进制字串
	 */
	public static String binary(int n) {
		String bin = Integer.toBinaryString(n);
		int len = bin.length();
		int missed = 0;
		if ((missed = Integer.SIZE - len) > 0) {
			for (int i = 0; i < missed; i++)
				bin = "0" + bin;
		}

		return bin;
	}

	/**
	 * @param n
	 * @return 二进制字串
	 */
	public static String binary(long n) {
		String bin = Long.toBinaryString(n);
		int len = bin.length();
		int missed = 0;
		if ((missed = Long.SIZE - len) > 0) {
			for (int i = 0; i < missed; i++)
				bin = "0" + bin;
		}
		return bin;
	}

	/**
	 * 将二进制转化为十六进制: 每四位一组换算到到十六进制，比如<br/>
	 * bin: 1001 0010 1100<br/>
	 * Dec: 9 2 12<br/>
	 * Hex: 9 2 c
	 * 
	 * @param bin
	 * @return hex of bin
	 */
	public static String bin2hex(String bin) {
		// i -> dec2hex[i]
		char[] dec2hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		int len = bin.length(), mod = len % 4;
		StringBuffer hashcode = new StringBuffer();

		String subbin = "";
		if (mod > 0) {
			subbin = bin.substring(0, mod);
			hashcode.append(dec2hex[bin2dec(subbin)]);
			if (mod == len)
				return hashcode.toString();

			bin = bin.substring(mod);
		}

		for (int i = 0; i < bin.length() / 4; i++) {
			subbin = bin.substring(4 * i, 4 * i + 4);
			hashcode.append(dec2hex[bin2dec(subbin)]);
		}
		return hashcode.toString();
	}

	/**
	 * 从二进制转化为十进制
	 * 
	 * @param bin
	 * @return dec of bin
	 */
	public static int bin2dec(String bin) {
		int len = bin.length();
		int dec = 0;
		for (int i = 0; i < len; i++)
			dec += (bin.charAt(i) - 48) * Math.pow(2, len - i - 1);
		return dec;
	}

	/**
	 * @param source
	 * @param num
	 * @return
	 */
	public static List<String> combine(char[] source, int num) {
		List<String> list = new ArrayList<String>();
		char[] temp = new char[num];
		System.arraycopy(source, 0, temp, 0, num);
		list.add(new String(temp));
		int len = source.length;
		for (int i = num; i < len; i++) {
			for (int j = 0; j < num; j++) {
				char tempChar = temp[j];
				temp[j] = source[i];
				list.add(new String(temp));
				temp[j] = tempChar;
			}
		}
		return list;
	}

	/**
	 * @param str
	 * @return permutation of characters in str
	 */
	public static List<String> permute(String str) {
		List<String> list = new ArrayList<String>();
		permute(str.toCharArray(), list, 0, str.length() - 1);
		return list;
	}

	public static void permute(char[] buf, List<String> list, int start, int end) {
		if (start == end) {
			list.add(new String(buf));
			return;
		}

		for (int i = start; i <= end; i++) {
			char temp = buf[start];
			buf[start] = buf[i];
			buf[i] = temp;
			permute(buf, list, start + 1, end);
			temp = buf[start];
			buf[start] = buf[i];
			buf[i] = temp;
		}
	}

	/**
	 * @param list1
	 * @param list2
	 * @return intersection of two sets
	 */
	public static List<Integer> intersect(List<Integer> list1, List<Integer> list2) {
		if (list1 == null || list2 == null)
			return null;

		List<Integer> intersect = new ArrayList<Integer>();
		for (int id1 : list1) {
			if (list2.indexOf(id1) == -1)
				continue;
			intersect.add(id1);
		}
		return intersect;
	}

	/**
	 * @param list
	 * @return count each unique value
	 */
	public static Map<Integer, Integer> count(List<Integer> list) {
		Map<Integer, Integer> stat = new HashMap<Integer, Integer>();
		for (int key : list) {
			Integer idx = stat.get(key);
			if (idx == null)
				stat.put(key, 1);
			else
				stat.put(key, stat.get(key) + 1);
		}
		return stat;
	}

	public static Map<Integer, Float> getDistribution(List<Integer> list) {
		Map<Integer, Integer> stat = new HashMap<>();
		for (int key : list) {
			Integer idx = stat.get(key);
			if (idx == null)
				stat.put(key, 1);
			else
				stat.put(key, stat.get(key) + 1);
		}

		Map<Integer, Float> dist = new HashMap<>();
		int size = list.size();
		for (int key : stat.keySet()) {
			int count = stat.get(key);
			dist.put(key, count * 1.0f / size);
		}
		return dist;
	}

	/**
	 * @param poll
	 * @return result of majority rule
	 */
	public static int majority(List<Integer> poll) {
		Map<Integer, Integer> stat = count(poll);
		int id = -1, nvt = -1;
		for (Entry<Integer, Integer> entry : stat.entrySet()) {
			int val = entry.getValue();
			if (val > nvt) {
				id = entry.getKey();
				nvt = val;
			}
		}
		return id;
	}

	/**
	 * @param matrix
	 *            symmetric matrix
	 * @return 脊形矩阵
	 */
	public static float[][] ridgeMatrix(float[][] matrix) {
		List<Integer> idList = new ArrayList<Integer>();
		int dim = matrix.length;
		for (int i = 1; i < dim; i++)
			idList.add(i);

		int curId = 0;
		List<Integer> ridgeList = new ArrayList<Integer>();
		ridgeList.add(curId);

		getRidgeId(matrix, ridgeList, idList, curId);

		float[][] ridgeMatrix = new float[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++) {
				if (i > j)
					ridgeMatrix[i][j] = ridgeMatrix[j][i];
				else
					ridgeMatrix[i][j] = matrix[ridgeList.get(i)][ridgeList.get(j)];
			}
		return ridgeMatrix;
	}

	/**
	 * 获取脊形矩阵对角元的id
	 * 
	 * @param matrix
	 *            symmetric matrix
	 * @param ridgeList
	 * @param idxList
	 * @param currentId
	 *            focus
	 */
	private static void getRidgeId(float[][] matrix, List<Integer> ridgeList, List<Integer> idxList, int currentId) {
		int sz = 0;
		if (idxList == null || (sz = idxList.size()) == 0)
			return;

		int maxId = idxList.get(0);
		if (sz == 1) {
			ridgeList.add(maxId);
			return;
		}

		float[] row = new float[sz];
		for (int i = 0; i < sz; i++)
			row[i] = matrix[currentId][idxList.get(i)];

		int[] rank = MathUtils.getRank(row, false);

		for (int i = 0; i < sz; i++)
			rank[i] = idxList.get(rank[i]);
		idxList = MathUtils.toList(rank);

		maxId = idxList.get(0);
		ridgeList.add(maxId);
		idxList.remove(0);

		getRidgeId(matrix, ridgeList, idxList, maxId);
	}

	/**
	 * @param matrix
	 *            symmetric matrix
	 * @return ridge form matrix
	 */
	public static double[][] ridgeMatrix(double[][] matrix) {
		List<Integer> idList = new ArrayList<Integer>();
		int dim = matrix.length;
		for (int i = 1; i < dim; i++)
			idList.add(i);

		int curId = 0;
		List<Integer> ridgeList = new ArrayList<Integer>();
		ridgeList.add(curId);

		getRidgeId(matrix, ridgeList, idList, curId);

		double[][] ridgeMatrix = new double[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++) {
				if (i > j)
					ridgeMatrix[i][j] = ridgeMatrix[j][i];
				else
					ridgeMatrix[i][j] = matrix[ridgeList.get(i)][ridgeList.get(j)];
			}
		return ridgeMatrix;
	}

	/**
	 * 获取脊形矩阵对角元的id
	 * 
	 * @param matrix
	 *            symmetric matrix
	 * @param ridgeList
	 * @param idxList
	 * @param currentId
	 *            focus
	 */
	private static void getRidgeId(double[][] matrix, List<Integer> ridgeList, List<Integer> idxList, int currentId) {
		int sz = 0;
		if (idxList == null || (sz = idxList.size()) == 0)
			return;

		int maxId = idxList.get(0);
		if (sz == 1) {
			ridgeList.add(maxId);
			return;
		}

		double[] row = new double[sz];
		for (int i = 0; i < sz; i++)
			row[i] = matrix[currentId][idxList.get(i)];

		int[] rank = MathUtils.getRank(row, false);

		for (int i = 0; i < sz; i++)
			rank[i] = idxList.get(rank[i]);
		idxList = MathUtils.toList(rank);

		maxId = idxList.get(0);
		ridgeList.add(maxId);
		idxList.remove(0);

		getRidgeId(matrix, ridgeList, idxList, maxId);
	}

	/**
	 * @param matrix
	 *            symmetric matrix
	 * @return ridge form matrix
	 */
	public static int[][] ridgeMatrix(int[][] matrix) {
		List<Integer> idList = new ArrayList<Integer>();
		int dim = matrix.length;
		for (int i = 1; i < dim; i++)
			idList.add(i);

		int curId = 0;
		List<Integer> ridgeList = new ArrayList<Integer>();
		ridgeList.add(curId);

		getRidgeId(matrix, ridgeList, idList, curId);

		int[][] ridgeMatrix = new int[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++) {
				if (i > j)
					ridgeMatrix[i][j] = ridgeMatrix[j][i];
				else
					ridgeMatrix[i][j] = matrix[ridgeList.get(i)][ridgeList.get(j)];
			}
		return ridgeMatrix;
	}

	/**
	 * 获取脊形矩阵对角元的id
	 * 
	 * @param matrix
	 *            symmetric matrix
	 * @param ridgeList
	 * @param idxList
	 * @param currentId
	 *            focus
	 */
	private static void getRidgeId(int[][] matrix, List<Integer> ridgeList, List<Integer> idxList, int currentId) {
		int sz = 0;
		if (idxList == null || (sz = idxList.size()) == 0)
			return;

		int maxId = idxList.get(0);
		if (sz == 1) {
			ridgeList.add(maxId);
			return;
		}

		int[] row = new int[sz];
		for (int i = 0; i < sz; i++)
			row[i] = matrix[currentId][idxList.get(i)];

		int[] rank = getRank(row, false);

		for (int i = 0; i < sz; i++)
			rank[i] = idxList.get(rank[i]);
		idxList = MathUtils.toList(rank);

		maxId = idxList.get(0);
		ridgeList.add(maxId);
		idxList.remove(0);

		getRidgeId(matrix, ridgeList, idxList, maxId);
	}

	/**
	 * Sort List based on Reference
	 * 
	 * @param reference
	 * @param list
	 * @param ascend
	 */
	public static void linkedSort(List<? extends Number> reference, List<? extends Number> list, boolean ascend) {
		int len = reference.size();
		if (len <= 1)
			return;
		int inv = ascend ? 1 : -1;

		list.sort((a, b) -> inv * (Double.compare(reference.get(list.indexOf(a)).doubleValue(),
				reference.get(list.indexOf(b)).doubleValue())));
	}
}