import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
  static int N, M, W[], D[], MAX = 987654321;
	static List<int[]> list = new ArrayList<>();
	
	static int solution(int n, int[] weak, int[] dist) {
		D = dist;
		N = weak.length;
		M = D.length;
		W = new int[2 * N];
		
		int ret = MAX, i, j;
		
    // 원형벽을 2배로 늘려준다.
		for (i = 0; i < N ; i++) {
			W[i] = weak[i];
			W[i + N] = W[i] + n;
		}
		
    // 친구들을 배치할 수 있는 모든 조합 생성
		Arrays.sort(dist);
		permutation(0, new int[M], new boolean[M]);
		
    // 모든 취약 지점에 대해서 모든 친구 배열을 배치한 경우중
    // 가장 작은 경우를 취한다
		for (i = 0; i < N; i++) 
			for (j = 0; j < list.size(); j++) 
				ret = Math.min(ret, inject(i, list.get(j)));
		
		return ret == MAX ? -1 : ret;
    }
	
  // 친구 배치
	static int inject(int s, int[] friends) {
		int p = 0, i, a;
		
		for (i = 0; i < friends.length; i++) {
			a = W[s + p];
			
      // 친구들의 점검 범위만큼 포지션 체크
			while (p < N && W[s + p] <= a + friends[i]) p++;
			
      // 원형을 2배로 늘린 벽에서 순서대로 n개를 취했다는 건 모든 벽을 점검했다는 뜻
			if (p == N) return i + 1;
		}
		
    // 점검 실패
		return MAX;
	}
  
	// 조합
	static void permutation(int depth, int[] make, boolean[] use) {
		if (depth == M) {
			int[] tmp = new int[M];
			
			for (int i = 0; i < M; i++)
				tmp[i] = make[i];
			
			list.add(tmp);
			return;
		}
		
		for (int i = 0; i < M; i++) {
			make[depth] = D[i];
			
			if (!use[i]) {
				use[i] = true;
				permutation(depth + 1, make, use);
				use[i] = false;
			}
		}
	}
	
}
