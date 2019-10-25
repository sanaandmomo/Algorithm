import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, my[] = {-1, 0, 1, 0}, mx[] = {0, 1, 0, -1};
	static char[][] m;
	static boolean visit[][][][];
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		
		m = new char[N][M];
		visit = new boolean[N][M][N][M];
		
		int i, j, ry = 0, rx = 0, by = 0, bx = 0;
		
		for (i = 0; i < N; i++) {
			String c = br.readLine();
			for (j = 0; j < M; j++) {
				char d = c.charAt(j);
				
				if (d == 'R') {
					ry = i;
					rx = j;
				} else if (d == 'B') {
					by = i;
					bx = j;
				}
				
				m[i][j] = d;
			}
		}
		
		Queue<int[]> q = new LinkedList<>();
		
		q.add(new int[] {ry, rx, by, bx, 0});
		visit[ry][rx][by][bx] = true;
		
		int ans = -1;
		
		out: while (!q.isEmpty()) {
			int[] cur = q.poll();
			
			// 10번 시도해도 안되는 경우
			if (cur[4] > 9) break;
			
			for (int dir = 0; dir < 4; dir++) {
				int[] NR = move(cur[0], cur[1], dir), NB = move(cur[2], cur[3], dir);
				
				if (NB[2] == 1) continue;
				
				if (NR[2] == 1) {
					ans = cur[4] + 1;
					break out;
				}
				
				// 같은 칸에 있을 때 방향에 따라 위치 재조정,
				// 깔끔하게 짜려다 보니까 이런 기본적인 로직을 놓쳐 고생했다.
				// 더럽더라도 케이스별로 나눠서 생각하고 코딩하는 습관을 길러야겠다.
				if (NR[0] == NB[0] && NR[1] == NB[1]) {
					switch (dir) {
					case 0:
						if (cur[0] < cur[2]) NB[0]++;
						else NR[0]++;
						break;
					case 1: 
						if (cur[1] < cur[3]) NR[1]--;
						else NB[1]--;
						break;
					case 2: 
						if (cur[0] < cur[2]) NR[0]--;
						else NB[0]--;
						break;
					case 3: 
						if (cur[1] < cur[3]) NB[1]++;
						else NR[1]++;
						break;
					}
				}
				
				// 북동남서로 돌릴 때마다 빨간공, 파란공의 위치 정보를 저장한다.
				// 만약 저장했던 위치 정보가 그대로 있을시 탐색하지 않아도 된다는 뜻.
				if (!visit[NR[0]][NR[1]][NB[0]][NB[1]]) {
					visit[NR[0]][NR[1]][NB[0]][NB[1]] = true;
					q.add(new int[] {NR[0], NR[1], NB[0], NB[1], cur[4] + 1});
				}
			}
			
		}
		
		bw.write(ans + "");
		bw.close();
	}
	
  // 구슬 이동
	static int[] move(int y, int x, int dir) {
		int retY = y, retX = x, escape = 0, ny = y + my[dir], nx = x + mx[dir];

		while (m[ny][nx] != '#') { // 벽 만나기 전까지
			retY = ny;
			retX = nx;
		
			if (m[ny][nx] == 'O') { // 탈출 성공
				escape = 1;
				break;
			};
	
			ny += my[dir];
			nx += mx[dir];
		}
		
		return new int[] {retY, retX, escape};
	}
}
