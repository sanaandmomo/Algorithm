class Solution {
    static boolean solution(int[][] key, int[][] lock) {
		int N = lock.length, M = key.length, moveNum = N + M - 1, i, j, r, c;
		int[][] oriLock = new int[N][N];
		
    // 기존의 자물쇠 복사
		copy(oriLock, lock, N);
		
    // 키를 4방향 다 돌려본다
		for (int dir = 0; dir < 4; dir++) {
       // 키를 자물쇠에 왼쪽위부터 오른쪽 아래까지 갖다대본다
			for (i = 0; i < moveNum; i++) {
				for (j = 0; j < moveNum; j++) {
					boolean flag = true;
					int kr = Math.max(0, i - M + 1), kc = Math.max(0, j - M + 1),
							kre = Math.min(i, N - 1), kce = Math.min(j, N - 1);
					
					out: for (r = kr; r <= kre; r++) {
						for (c = kc; c <= kce; c++) {
              // 자물쇠와 키의 홈이 다르다면 채워준다
							if (lock[r][c] != key[r + M - i - 1][c + M - j - 1]) 
								lock[r][c] = 1;
							else { // 같다면 맞지 않다는 뜻
								flag = false;
								break out;
							}
						}
					}
          // 자물쇠에 키가 맞물린다면
					if (flag && check(lock, N)) return true;
					
					copy(lock, oriLock, N);
				}
			}
      // 키 회전
			key = rotate(key, M);
		}
		
    return false;
  }
    
	// 90도 회전
	static int[][] rotate(int[][] key, int M) {
		int i, j, tmp[][] = new int[M][M];
		
		for (i = 0; i < M; i++)
			for (j = 0; j < M; j++)
				tmp[i][j] = key[M - j - 1][i];
		
		return tmp;
	}
  
	// 열릴 수 있는지 체크
	static boolean check(int[][] lock, int N) {
		int r, c;
		
		for (r = 0; r < N; r++) {
			for (c = 0; c < N; c++) {
				if (lock[r][c] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
  
	// 카피
	static void copy(int[][] a, int[][] b, int N) {
		int i, j;
		
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++)
				a[i][j] = b[i][j];
	}
}
