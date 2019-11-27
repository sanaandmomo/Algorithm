import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, A[][], K, MAX = 987654321, my[] = {0, 1, 0, -1}, mx[] = {1, 0, -1, 0};
	static ArrayList<int[]> list = new ArrayList<>();
	static boolean[] picked;
	
	static int min(int depth) {
		if (depth == K) {
			int i, j, ret = MAX, sum;
			
			for (i = 0; i < N; i++) {
				sum = 0;
				for (j = 0; j < M; j++)
					sum += A[i][j];
				
				ret = Math.min(ret, sum);
			}
			
			return ret;
		}
		
		int i, ret = MAX, tmp[][] = new int[N][M];
		
		copy(tmp, A); // 배열 변환하기전 원본 기억하기
		
    // 백트래킹으로 연산 순서 다 해보기
		for (i = 0; i < K; i++) {
			if (picked[i]) continue;
			
			picked[i] = true;
			rotate(i, tmp); // i번째 회전연산을 실행
			ret = Math.min(ret, min(depth + 1));
			copy(A, tmp); // 재귀 빠져나오면 다시 원본 복사
			picked[i] = false;
		}
		
		return ret;
	}
	
	static void rotate(int nth, int[][] tmp) {
		int v[] = list.get(nth), y = v[0], x = v[1], s = v[2], i, dir;
		
		for (i = 1; i <= s; i++) { // 범위 s까지 나아가서 돌려준다.
			int sy = y - i, sx = x - i;
			
			for (dir = 0; dir < 4; dir++) { // 4방향에 대해서
				while (true) {
					int ny = sy + my[dir], nx = sx + mx[dir];
					
					A[ny][nx] = tmp[sy][sx];
					
					sy = ny;
					sx = nx;
					
					if ((dir == 0 && nx == x + i) || (dir == 1 && ny == y + i) ||
							(dir == 2 && nx == x - i) || (dir == 3 && ny == y - i)) break;
				}
			}
		}
	}
	
	static void copy(int[][] a, int[][] b) {
		int i, j;
		
		for (i = 0; i < N; i++)
			for (j = 0; j < M; j++)
				a[i][j] = b[i][j];
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		K = Integer.parseInt(s[2]);
		
		A = new int[N][M];
		picked = new boolean[K];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < M; j++)
				A[i][j] = Integer.parseInt(s[j]);
		}
		
		for (i = 0; i < K; i++) {
			s = br.readLine().split(" ");
			list.add(new int[] {
					Integer.parseInt(s[0]) - 1,
					Integer.parseInt(s[1]) - 1,
					Integer.parseInt(s[2])
			});
		}
		
		bw.write(min(0) + "");
		bw.close();
	}
}
