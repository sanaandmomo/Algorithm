import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M[][], my[] = {-1,0,1,0}, mx[] = {0,1,0,-1};
	static boolean[][] visit;
	static Queue<int[]> q = new LinkedList<>();
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		M = new int[N][N];
		visit = new boolean[N][N];
		
		int i, j, y = -1, x = -1, MAX = N * N, len = MAX, minY = MAX, minX = MAX, size = 2, cnt = 0, time = 0;
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				M[i][j] = Integer.parseInt(s[j]);
				// 아기 상어일 때
				if (M[i][j] == 9) {
					y = i; // 좌표 저장
					x = j;
					M[i][j] = 0; // 0으로 초기화
				}
			}
		}
		
		q.add(new int[] {y, x, 0});
		visit[y][x] = true;
		
		while (true) {
    // bfs로 최단 거리의 먹을 수 있는 물고기 탐색
			while (!q.isEmpty()) {
				int[] cur = q.poll();
				
				for (int dir = 0; dir < 4; dir++) {
					int ny = cur[0] + my[dir], nx = cur[1] + mx[dir];
					
          // 범위 밖으로 가거나 더 큰 물고기가 있거나, 방문했었다면 
					if (ny < 0 || ny >= N || nx < 0 || nx >= N || M[ny][nx] > size || visit[ny][nx]) continue;
					
					int move = cur[2] + 1;
					
					q.add(new int[] {ny, nx, move});
					visit[ny][nx] = true;
					// 물고기보다 크기가 더 크고, 더 적게 이동했거나, 아니면 거리가 같을 때 맨 위에 물고기를 고르고
          // 맨 위에 물고기가 많다면 그 중 왼쪽 물고기를 선택한다
					if ((M[ny][nx] != 0 && size > M[ny][nx]) && (move < len || move == len && ((ny == minY && nx < minX) || ny < minY))) {
						minY = ny;
						minX = nx;
						len = move;
					}
				}
			}
			
			// 먹은 물고기가 없다면
			if (minY ==  MAX) break;
      
      // 시간 갱신
			time += len;
      // 자기 무게만큼 먹으면 무게 업
			if (size == ++cnt) {
				size++;
				cnt = 0;
			}
			
      // 초기화
			for (i = 0; i < N; i++)
				for (j = 0; j < N; j++)
					visit[i][j] = false;
			
			y = minY;
			x = minX;
			q.add(new int[] {y, x, 0});
			visit[y][x] = true;
			M[y][x] = 0;
			
			minY = minX = len = MAX;
		}
		
		bw.write(time + "");
		bw.close();
	}
}
