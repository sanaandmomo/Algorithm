import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, x, y, K, mx[] = {0, 0, -1, 1}, my[] = {1, -1, 0, 0}, dice[] = new int[7], A[][];
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		x = Integer.parseInt(s[2]);
		y = Integer.parseInt(s[3]);
		K = Integer.parseInt(s[4]);
		
		A = new int[N][M];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < M; j++)
				A[i][j] = Integer.parseInt(s[j]);
		}
		
		s = br.readLine().split(" ");
		
		for (i = 0; i < K; i++) {
			int dir = Integer.parseInt(s[i]) - 1;
			
			int nx = x + mx[dir], ny = y + my[dir];
			
      // 주사위가 지도를 나갈 때
			if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
			
			int[] tmp = dice.clone();
			
      // 각도에 따라 전개도 재정의
			switch (dir) {
			case 0:
				dice[1] = tmp[4];
				dice[6] = tmp[3];
				dice[4] = tmp[6];
				dice[3] = tmp[1];
				break;
			case 1:
				dice[4] = tmp[1];
				dice[3] = tmp[6];
				dice[6] = tmp[4];
				dice[1] = tmp[3];
				break;
			case 2:
				dice[5] = tmp[1];
				dice[1] = tmp[2];
				dice[2] = tmp[6];
				dice[6] = tmp[5];
				break;
			case 3:
				dice[1] = tmp[5];
				dice[2] = tmp[1];
				dice[6] = tmp[2];
				dice[5] = tmp[6];
				break;
			}
			
			if (A[nx][ny] == 0) { // 지도가 0이면 지도에 주사위 바닥면 복사
				A[nx][ny] = dice[6]; 
			} else { // 주사위 바닥에 지도 복사후 지도 0
				dice[6] = A[nx][ny];
				A[nx][ny] = 0;
			}
			
			x = nx;
			y = ny;
			
			bw.write(dice[1] + "\n");
		}
		
		bw.close();
	}
}
