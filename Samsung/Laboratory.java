import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int n, m, board[][], test[][], ans = -1;
	static int[] my = {-1,0,1,0};
	static int[] mx = {0,1,0,-1};
	static Queue<Point> q = new LinkedList<>();
	
	static void dfs(int depth, int y) {
		if (depth == 3) { // 벽 3개 세웠을 때
				test = copy(board); // 복사하기
				bfs(); // 바이러스 퍼뜨리기
				ans = Math.max(ans, area()); // 영역 최댓값 갱신
				return;
		}
		
		for (int i=y; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (board[i][j] == 0) {
					board[i][j] = 1; // 벽 세우기
					dfs(depth+1, i); // 다음 행부터 다시 모든 경우 탐색
					board[i][j] = 0; // 벽 제거 (백트래킹)
				}
			}
		}
	}
	
	static void bfs() {
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (test[i][j] == 2) q.add(new Point(i,j)); // 바이러스 좌표
			}
		}
		
    // 바이러스 퍼뜨리기
		while(!q.isEmpty()) {
			Point cur = q.poll();
			for (int i=0; i<4; i++) {
				int y = cur.y + my[i];
				int x = cur.x + mx[i];
				if (y<0 || y>=n || x<0 || x>=m) continue;
				
				if (test[y][x] == 0) {
					test[y][x] = 2;
					q.add(new Point(y,x));
				}
			}
		}
	}
	
  // 안전 구역 구하기
	static int area() {
		int ret = 0;
		
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				if (test[i][j] == 0) ret++;
			}
		}
		
		return ret;
	}
	
	static int[][] copy(int[][] board) {
		int[][] ret = new int[n][m];
		for (int i=0; i<n; i++)
			for (int j=0; j<m; j++) 
				ret[i][j] = board[i][j];
			
		return ret;
	}
	
	
	public static void main(String[] args) throws Exception {
		String[] st = br.readLine().split(" ");
		n = Integer.parseInt(st[0]);
		m = Integer.parseInt(st[1]);
		
		board = new int[n][m];
		
		for (int i=0; i<n; i++) {
			st = br.readLine().split(" ");
			for (int j=0; j<m; j++) {
				board[i][j] = Integer.parseInt(st[j]);
			}
		}
		
		dfs(0,0);
		bw.write(ans + "");
		bw.close();
	}
}

class Point {
	int y,x;
	
	Point (int a, int b) {
		y = a;
		x = b;
	}
}
