import java.util.LinkedList;
import java.util.Queue;

class Solution {
  static int N;
	static int[][] map;
	static boolean[][][][] visit;

	public int solution(int[][] board) {
		N = board.length;
		map = board;
		visit = new boolean[N][N][N][N]; // 로봇의 좌표 
    
		int[] my = { -1, 0, 1, 0 }, mx = { 0, 1, 0, -1 };

		Queue<int[]> q = new LinkedList<>();
		add(0, 0, 0, 1, 0, q);

		while (!q.isEmpty()) {
			int[] cur = q.poll();

			// 4방향 탐색
			for (int dir = 0; dir < 4; dir++) {
				int ny1 = cur[0] + my[dir], nx1 = cur[1] + mx[dir], ny2 = cur[2] + my[dir], nx2 = cur[3] + mx[dir];

				// 갈 수 있을 때
				if (isRange(ny1, nx1) && isRange(ny2, nx2) && isNotObstacle(ny1, nx1, ny2, nx2) && !visit[ny1][nx1][ny2][nx2]) {
					if (isArrive(ny1, nx1, ny2, nx2))
						return cur[4] + 1;

					add(ny1, nx1, ny2, nx2, cur[4] + 1, q);
				}
			}

			if (cur[0] == cur[2]) { // 가로로 있을 때
				// 위로 회전
				int y = cur[0] - 1;

				if (isRange(y, cur[1]) && isNotObstacle(y, cur[1], y, cur[3])) {
					if (!visit[y][cur[3]][cur[2]][cur[3]])
						add(y, cur[3], cur[2], cur[3], cur[4] + 1, q);
					
					if (!visit[y][cur[1]][cur[0]][cur[1]])
						add(y, cur[1], cur[0], cur[1], cur[4] + 1, q);
				}

				// 아래로 회전
				y = cur[0] + 1;

				if (isRange(y, cur[1]) && isNotObstacle(y, cur[1], y, cur[3])) {
					if (!visit[y][cur[3]][cur[2]][cur[3]]) {
						if (isArrive(y, cur[3], cur[2], cur[3]))
							return cur[4] + 1;
						
						add(y, cur[3], cur[2], cur[3], cur[4] + 1, q);
					}
					
					if (!visit[y][cur[1]][cur[0]][cur[1]]) {
						if (isArrive(y, cur[1], cur[0], cur[1]))
							return cur[4] + 1;

						add(y, cur[1], cur[0], cur[1], cur[4] + 1, q);
					}
				}
			} else { // 세로로 있을 때
				// 왼쪽으로 회전
				int x = cur[1] - 1;
				
				if (isRange(cur[2], x) && isNotObstacle(cur[2], x, cur[0], x)) {
					if (!visit[cur[0]][x][cur[0]][cur[1]])
						add(cur[0], x, cur[0], cur[1], cur[4] + 1, q);
					if (!visit[cur[2]][x][cur[2]][cur[3]])
						add(cur[2], x, cur[2], cur[3], cur[4] + 1, q);
				}
				
				// 오른쪽으로 회전
				x = cur[1] + 1;
				
				if (isRange(cur[2], x) && isNotObstacle(cur[2], x, cur[0], x)) {
					if (!visit[cur[0]][cur[1]][cur[0]][x]) {
						if (isArrive(cur[0], cur[1], cur[0], x))
							return cur[4] + 1;
						
						add(cur[0], cur[1], cur[0], x, cur[4] + 1, q);
					}
						
					if (!visit[cur[2]][cur[3]][cur[2]][x]) {
						if (isArrive(cur[2], cur[3], cur[2], x)) 
							return cur[4] + 1;
						
						add(cur[2], cur[3], cur[2], x, cur[4] + 1, q);
					}
				}
				
			}

		}

		return -1;
	}
  
  // 범위 체크
	static boolean isRange(int y, int x) {
		return 0 <= y && y < N && 0 <= x && x < N;
	}
  
  // 장애물이 아닐 때
	static boolean isNotObstacle(int y1, int x1, int y2, int x2) {
		return map[y1][x1] != 1 && map[y2][x2] != 1;
	}
  
  // 도착 여부
	static boolean isArrive(int y1, int x1, int y2, int x2) {
		return (y1 == N - 1 && x2 == N - 1) || (y2 == N - 1 && x2 == N - 1);
	}
  
  // 큐에 저장
	static void add(int y1, int x1, int y2, int x2, int n, Queue<int[]> q) {
		q.add(new int[] { y1, x1, y2, x2, n });
		visit[y1][x1][y2][x2] = visit[y2][x2][y1][x1] = true; // 로봇은 대칭이므로 양방향에 대해 방문 true
	}
}
