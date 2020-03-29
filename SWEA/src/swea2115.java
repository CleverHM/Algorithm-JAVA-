import java.util.*;
import java.io.*;

public class swea2115 {
	static int N, M, C; // N: 벌통크기, M: 연속된 벌통수
	static int[][] map, maxMap; // map : 입력된 벌통
								// maxMap : i,j위치에사 가질 수 있는 최대이익

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			maxMap = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " "); // 한행 입력
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			} // 입력 완료...
				// 1. 각 i,j 위치에서 연속된 M개를 고려하여 취할 수 있는 부분집합의 최대이익 계산
			makeMaxMap();
			// 2. 두 일꾼의 조합.
			System.out.println("#" + t + " " + getMaxBenefit());
		}
	}

	private static void makeMaxMap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= N - M; j++) {
				makeMaxSubset(i, j, 0, 0, 0);
			}
		}
	}

	// i: 행위치, j:열위치, cnt:고려한원소수.
	// sum: 부분집합에 속한 원소들의 합.
	// PowSum: 부분집합에 속한 원소의 이익.
	private static void makeMaxSubset(int i, int j, int cnt, int sum, int PowSum) {
		if (sum > C)
			return; // 부분집합의 합이 목표량C를 초과하면 리턴.
		if (cnt == M) {
			if (maxMap[i][j - M] < PowSum) {
				maxMap[i][j - M] = PowSum;
			}
			return;
		}

		// i,j위치 원소 선택한경우.
		makeMaxSubset(i, j + 1, cnt + 1, sum + map[i][j], PowSum + map[i][j] * map[i][j]);
		// i,j위치 원소 비선택
		makeMaxSubset(i, j + 1, cnt + 1, sum, PowSum);
	}

	private static int getMaxBenefit() {
		int max = 0, temp = 0; // max : 조합적 선택후의 최대이익값.
		// 1. 일꾼A 기준선택
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= N - M; j++) { // a일꾼
				// 2. 일꾼 B 선택
				// 같은 행 기준 선택
				for (int j2 = j + M; j2 < N - M; j2++) {
					temp = maxMap[i][j] + maxMap[i][j2];
					if (max < temp) {
						max = temp;
					}
				}

				// 다음행부터 마지막행까지 선택
				for (int i2 = i + 1; i2 < N; i2++) {
					for (int j2 = 0; j2 <= N - M; j2++) {
						temp = maxMap[i][j] + maxMap[i2][j2];

						if (max < temp) {
							max = temp;
						}
					}
				}
			}
		}
		return max;
	}
}
