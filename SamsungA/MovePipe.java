import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M[][], dp[][][], dirs[][][] = {
			{{0, 1, 0}, {1, 1, 1}}, // 가로
			{{0, 1, 0}, {1, 1, 1}, {1, 0, 2}}, // 대각선
			{{1, 1, 1}, {1, 0, 2}} // 세로
	};
	
	static int cases(int y, int x, int dir) {
		if (y == N - 1 && x == N - 1) return 1; // 도착한 경우 경우의 수 1 리턴
		
		if (dp[y][x][dir] != -1) return dp[y][x][dir]; // 메모이제이션
		
		int ret = 0, i;
		
		for (i = 0; i < dirs[dir].length; i++) { 
			int ny = y + dirs[dir][i][0], nx = x + dirs[dir][i][1];
			// 범위를 벗어날 때
			if (ny < 0 || ny >= N || nx < 0 || nx >= N || M[ny][nx] == 1) continue;
			// 대각선 방향이고 위, 왼쪽이 벽일 때
			if (dirs[dir][i][2] == 1 && (M[ny - 1][nx] == 1 || M[ny][nx - 1] == 1)) continue;
			
			ret += cases(ny, nx, dirs[dir][i][2]);
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		M = new int[N][N];
		dp = new int[N][N][3];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				M[i][j] = Integer.parseInt(s[j]);
				dp[i][j][0] = dp[i][j][1] = dp[i][j][2] = -1;
			}
		}
		
		bw.write(cases(0, 1, 0) + "");
		bw.close();
	}
}
