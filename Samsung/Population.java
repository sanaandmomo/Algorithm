import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static Queue<int[]> q = new LinkedList<>();
	static List<int []> nation = new ArrayList<>();
	static boolean[][] open;
	static int N, L, R, M[][], my[] = {-1, 0, 1, 0}, mx[] = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		L = Integer.parseInt(s[1]);
		R = Integer.parseInt(s[2]);
		
		M = new int[N][N];
		open = new boolean[N][N];
		
		int r, c, dir, day = 0;
		
		for (r = 0; r < N; r++) {
			s = br.readLine().split(" ");
			for (c = 0; c < N; c++)
				M[r][c] = Integer.parseInt(s[c]);
		}
		
		while (true) {
			boolean isOpen = false;
			
			for (r = 0; r < N; r++) {
				for (c = 0; c < N; c++) {
					if (!open[r][c]) { // 열려있지 않을 때
						int[] point = {r, c};
						int population = M[r][c], union = 1; // 인구와 국가수 초기화
						
						q.add(point);
						nation.add(point);
						open[r][c] = true;
						
            // 너비우선탐색 실시
						while (!q.isEmpty()) {
							int[] cur = q.poll();
							int y = cur[0], x = cur[1];
							
							for (dir = 0; dir < 4; dir++) {
								int ny = y + my[dir], nx = x + mx[dir];
								
								if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
								
								int diff = Math.abs(M[y][x] - M[ny][nx]);
								
                // 인구차이가 L과 R사이고 열리지 않았을 때
								if (L <= diff && diff <= R && !open[ny][nx]) {
									point = new int[] {ny, nx};
									q.add(point);
									nation.add(point);
									open[ny][nx] = true;
									population += M[ny][nx]; // 인구수 합산
									union++;
									isOpen = true;
								}
							}
						}
						
            // 평균값
						int avg = (int)Math.floor(1.0 * population / union); 
						
            // 국가들에 대해 평균값 갱신
						for (int[] p : nation) 
							M[p[0]][p[1]] = avg;
						
						nation.clear();
					}
				}
			}
			
      // 안 열렸으면 끝
			if (!isOpen) break;
			
			for (r = 0; r < N; r++)
				for (c = 0; c < N; c++)
					open[r][c] = false;
			
			day++;
		}
		
		bw.write(day + "");
		bw.close();
	}
}
