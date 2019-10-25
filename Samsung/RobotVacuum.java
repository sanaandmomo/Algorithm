import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, D, A[][], my[] = {-1, 0, 1, 0}, mx[] = {0, 1, 0, -1},  ry[] = {1, 0, -1, 0}, rx[] = {0, -1, 0, 1};
	
	static int f(int y, int x, int d) {
		for (int dir = 0; dir < 4; dir++) {
			d = (d + 3) % 4; // 왼쪽으로 방향 틀기
			int ny = y + my[d], nx = x + mx[d];
			
			// 청소할 칸이라면
			if (A[ny][nx] == 0) {
				A[ny][nx] = 2; // 청소
				return 1 + f(ny, nx, d);
			}
		}
		
		// 4방향 모두 청소가 돼있거나 벽이라면
		int backY = y + ry[d], backX = x + rx[d];
		
    // 뒤에도 막히면 return 0 하고 종료, 안 막히면 다음 탐색
		return A[backY][backX] == 1 ? 0 : f(backY, backX, d);
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		A = new int[N][M];
		
		s = br.readLine().split(" ");
		
		int y = Integer.parseInt(s[0]), x = Integer.parseInt(s[1]), d = Integer.parseInt(s[2]), i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < M; j++)
				A[i][j] = Integer.parseInt(s[j]);
		}
		A[y][x] = 2;
		
		bw.write(1 + f(y, x, d) + "");
		bw.close();
	}
}
