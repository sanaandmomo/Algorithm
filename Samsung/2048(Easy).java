import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, m[][];
	
	static int max(int depth) {
		if (depth == 5) { // 5번 이동했을 때
			int ret = 0, i, j;
			
			for (i = 0; i < N; i++)
				for (j = 0; j < N; j++)
					ret = Math.max(ret, m[i][j]);
			// 가장 큰 블럭
			return ret;
		}
		
		int ret = 0, dir, tmp[][] = new int[N][N];
		copy(tmp, m); // tmp의 변환전 저장
		
		for (dir = 0; dir < 4; dir++) {
			crash(dir); // 각도에 따라 
			ret = Math.max(ret, max(depth + 1)); // 다음번
			copy(m, tmp); // 빠져나오면 다시 변환전으로 돌아가기 (백트래킹)
		}
		
		return ret;
	}
	
  // 충돌
	static void crash(int dir) {
		Queue<Integer> q = new LinkedList<>();
		int r, c;
		
		switch (dir) {
		case 0: // 북
			for (c = 0; c < N; c++) {
				for (r = 0; r < N; r++) {
					if (m[r][c] != 0) {
						q.add(m[r][c]);
						m[r][c] = 0;
					}
				}
				
				int y = 0;
				
				while (!q.isEmpty()) {
					int block = q.poll();
					
					if (m[y][c] == 0) m[y][c] = block;
					else if (m[y][c] == block) {
						m[y][c] *= 2;
						y++;
					} else m[++y][c] = block;
				}
			}
			break;
		case 1: // 동
			for (r = 0; r < N; r++) {
				for (c = N - 1; c >= 0; c--) {
					if (m[r][c] != 0) {
						q.add(m[r][c]);
						m[r][c] = 0;
					}
				}
				
				int x = N - 1;
				
				while (!q.isEmpty()) {
					int block = q.poll();
					
					if (m[r][x] == 0) m[r][x] = block;
					else if (m[r][x] == block) {
						m[r][x] *= 2;
						x--;
					} else m[r][--x] = block;
				}
			}
			break;
		case 2: // 남
			for (c = 0; c < N; c++) {
				for (r = N - 1; r >= 0; r--) {
					if (m[r][c] != 0) {
						q.add(m[r][c]);
						m[r][c] = 0;
					}
				}
				
				int y = N - 1;
				
				while (!q.isEmpty()) {
					int block = q.poll();
					
					if (m[y][c] == 0) m[y][c] = block;
					else if (m[y][c] == block) {
						m[y][c] *= 2;
						y--;
					} else m[--y][c] = block;
				}
			}
			break;
		case 3: // 서
			for (r = 0; r < N; r++) {
				for (c = 0; c < N; c++) {
					if (m[r][c] != 0) {
						q.add(m[r][c]);
						m[r][c] = 0;
					}
				}
				
				int x = 0;
				
				while (!q.isEmpty()) {
					int block = q.poll();
					
					if (m[r][x] == 0) m[r][x] = block;
					else if (m[r][x] == block) {
						m[r][x] *= 2;
						x++;
					} else m[r][++x] = block;
				}
			}
		}
	}
	
	static void copy(int[][] a, int[][] b) {
		int i, j;
		
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++)
				a[i][j] = b[i][j];
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		m = new int[N][N];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < N; j++)
				m[i][j] = Integer.parseInt(s[j]);
		}
		
		bw.write(max(0) + "");
		bw.close();
	}
}
