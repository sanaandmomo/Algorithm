import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M[][], A[][], area[];
	
  // 선거구를 나눌 수 있는지 판별한다
	static boolean isPossible(int x, int y, int d1, int d2) {
		if (x + d1 >= N || y - d1 < 0) return false;
		if (x + d2 >= N || y + d2 >= N) return false;
		if (x + d1 + d2 >= N || y - d1 + d2 >= N || y - d1 + d2 < 0) return false;
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		A = new int[N][N];
		M = new int[N][N];
		area = new int[6];
		
		int i, j, k, l, d1, d2, x1, y1, x2, y2, x3, y3, x4, y4, ans = 987654321;
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(s[j]);
				M[i][j] = 5;
			}
		}
    // 입력
		
    
    // 모든 행과 열에 대하여 선거구를 나눠본다
		for (i = 0; i < N; i++) {
			for (j = 1; j < N; j++) {
				for (d1 = 1; d1 <= j; d1++) {
					for (d2 = 1; d2 < N - j; d2++) {
						if (!isPossible(i, j, d1, d2)) continue;
						x1 = i;
						y1 = j;
						x2 = i + d1;
						y2 = j - d1;
						x3 = i + d2;
						y3 = j + d2;
						x4 = x2 + d2;
						y4 = y2 + d2;

						// 1 선거구
						int a = 0;
						
						for (k = 0; k < x2; k++) {
							if (k >= x1) a++;
							for (l = 0; l <= y1 - a; l++) 
								M[k][l] = 1;
						}
						
						a = 0;
						
						// 2 선거구
						for (k = 0; k <= x3; k++) {
							if (k > x1) a++;
							for (l = y1 + 1 + a; l < N; l++)
								M[k][l] = 2;
						}
						
						a = 0;
						
						// 3 선거구
						for (k = N - 1; k >= x2; k--) {
							if (k < x4) a++;
							for (l = 0; l < y4 - a; l++)
								M[k][l] = 3;
						}
						
						a = 0;
						
						// 4 선거구
						for (k = N - 1; k > x3; k--) {
							if (k <= x4) a++;
							for (l = y4 + a; l < N; l++)
								M[k][l] = 4;
						}
						
            // 선거구들의 인구 합산
						for (k = 0; k < N; k++)
							for (l = 0; l < N; l++)
								area[M[k][l]] += A[k][l];
						
						Arrays.sort(area);
						
						ans = Math.min(ans, area[5] - area[1]);
						
            // 다시 초기화 작업
						for (k = 1; k < 6; k++)
							area[k] = 0;
						
						for (k = 0; k < N; k++)
							for (l = 0; l < N; l++)
								M[k][l] = 5;
					}
				}
			}
		}
		
		bw.write(ans + "");
		bw.close();
	}
}
