import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, K, L, M[][], my[] = {-1, 0, 1, 0}, mx[] = {0, 1, 0, -1};
	static HashMap<Integer, Character> dir = new HashMap<>();
	static Queue<int[]> snake = new LinkedList<>();
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		M = new int[N][N];
		
		int i, time = 0, curDir = 1, head[] = {0, 0};
		
		for (i = 0; i < K; i++) {
			String[] s = br.readLine().split(" ");
			int y = Integer.parseInt(s[0]), x = Integer.parseInt(s[1]);
			M[y - 1][x - 1] = 1;
		}
		
		L = Integer.parseInt(br.readLine());
		
		for (i = 0; i < L; i++) {
			String[] s = br.readLine().split(" ");
			dir.put(Integer.parseInt(s[0]), s[1].charAt(0));
		}
		
		snake.add(new int[] {0, 0});
		
		while (true) {
			time++;
			
			int ny = head[0] + my[curDir], nx = head[1] + mx[curDir];
            
			// 다음 갈 좌표가 낙 or 자기 자신이라면
			if (ny < 0 || ny >= N || nx < 0 || nx >= N || M[ny][nx] == -1) break;
			
			// 길일 때 꼬리를 비워주고 꼬리 좌표 갱신
			if (M[ny][nx] == 0) {
				int[] tail = snake.poll();
				M[tail[0]][tail[1]] = 0;
			} 
			
			// 머리 좌표
			M[ny][nx] = -1;
			head[0] = ny;
			head[1] = nx;
			snake.add(new int[] {ny, nx});
			
			// 방향 전환
			if (dir.containsKey(time)) {
				char direct = dir.get(time);
				
				if (direct == 'D') {
					curDir = (curDir + 1) % 4;
				} else {
					curDir -= 1;
					if (curDir < 0) curDir += 4;
				}
			}
		}
		
		bw.write(time + "");
		bw.close();
	}
}
