import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int R, C, T, M[][], tmp[][], my[] = {-1,0,1,0}, mx[] = {0,1,0,-1}, cy[] = {0,-1,0,1}, cx[] = {1,0,-1,0}, ccy[] = {0,1,0,-1};
	static Queue<int[]> q = new LinkedList<>();
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		R = Integer.parseInt(s[0]);
		C = Integer.parseInt(s[1]);
		T = Integer.parseInt(s[2]);
		
		M = new int[R][C];
		tmp = new int[R][C];
		
		int i, j, t, top[] = {0, 0}, bottom[] = {0, 0};
		
		for (i = 0; i < R; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < C; j++) {
				M[i][j] = Integer.parseInt(s[j]);
				tmp[i][j] = M[i][j];
				
				if (M[i][j] == -1) {
					if (top[0] == 0) {
						top[0] = i;
						top[1] = j;
					} else {
						bottom[0] = i;
						bottom[1] = j;
					}
				}
			}
		}
		
		for (t = 0; t < T; t++) {
			// 미세먼지 좌표 넣기
			for (i = 0; i < R; i++) {
				for (j = 0; j < C; j++) { 
					if (M[i][j] > 0) 
						q.add(new int[] {i, j, M[i][j]});
				}
			}
			
			// 확산
			while (!q.isEmpty()) {
				int cur[] = q.poll(), y = cur[0], x = cur[1], v = cur[2];
				
				for (int dir = 0; dir < 4; dir++) {
					int ny = y + my[dir], nx = x + mx[dir];
					
					if (ny < 0 || ny >= R || nx < 0 || nx >= C || M[ny][nx] == -1) continue;
					
					int spread = (int)Math.floor(1.0 * v / 5);
					
					M[ny][nx] += spread;
					M[y][x] -= spread;
				}
			}
			
			// 카피
			for (i = 0; i < R; i++)
				for (j = 0; j < C; j++) 
					tmp[i][j] = M[i][j];
			
			// 순환
			circulate(top[0], top[1], cy, cx);
			circulate(bottom[0], bottom[1], ccy, cx);
		}
		
		int sum = 0;
		
		for (i = 0; i < R; i++)
			for (j = 0; j < C; j++)
				sum += M[i][j];

		bw.write(sum + 2 + "");
		bw.close();
	}
	
  // 순환시키기
	static void circulate(int cy, int cx, int[] ydir, int[] xdir) {
		int y = cy, x = cx + 1;
		M[y][x] = 0; // 공기청정기에 들어간 먼지는 없어짐
		
		for (int dir = 0; dir < 4; dir++) { // 4방향에 대해서
			while (true) {
				int ny = y + ydir[dir], nx = x + xdir[dir];
				// 범위 벗어나면 끝
				if (ny < 0 || ny >= R || nx < 0 || nx >= C) break;
				// 공기청정기 좌표일시 끝
				if (ny == cy && nx == cx) break;
				
				M[ny][nx] = tmp[y][x];
				
				y = ny;
				x = nx;
			}
		}
	}
	
}

