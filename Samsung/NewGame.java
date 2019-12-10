import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, K, pos[][], my[] = {0, 0, -1, 1}, mx[] = {1, -1, 0, 0}, trans[] = {1, 0, 3, 2}, map[][];
	static Stack<Integer>[][] M; // 게임판을 스택으로 만든다
	
  // 파란칸인지 여부
	static boolean isBlue(int y, int x) {
		return y < 0 || y >= N || x < 0 || x >= N || map[y][x] == 2;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		K = Integer.parseInt(s[1]);
		pos = new int[K][3];
		M = new Stack[N][N];
		map = new int[N][N];
		
		Stack<Integer> white = new Stack<>();
		
		int i, j, y, x, dir, ans = -1, cnt = 0;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				M[i][j] = new Stack<>();
				map[i][j] = Integer.parseInt(s[j]);
			}
		}
		// 입력
    
    
		for (i = 0; i < K; i++) {
			s = br.readLine().split(" ");
			
			y = Integer.parseInt(s[0]) - 1;
			x = Integer.parseInt(s[1]) - 1;
			dir = Integer.parseInt(s[2]) - 1;
      
			// pos 배열에 말의 위치와 방향을 저장
			pos[i][0] = y;
			pos[i][1] = x;
			pos[i][2] = dir;
      
      // 게임판에 말을 추가
			M[y][x].add(i);
		}
		
		a: while (true) {
			cnt++;
			
			// 턴 돌리기
			for (i = 0; i < K; i++) {
				// 현재 말의 정보를 가져온다
				int[] cur = pos[i];
				y = cur[0];
				x = cur[1];
				dir = cur[2];
				
				// 이동하려는 다음 칸
				int ny = y + my[dir], nx = x + mx[dir];
				
				// 파랑칸일 때
				if (isBlue(ny, nx)) {
					// 좌표를 반대로 바꿔준다
					ny = y - my[dir]; 
					nx = x - mx[dir];
					cur[2] = trans[dir];
					
					// 반대 방향도 blue면
					if (isBlue(ny, nx)) continue;
				}
				
				switch (map[ny][nx]) {
				case 0: // 흰색일 땐 스택에 다시 넣어서 뺀다
					while (!M[y][x].isEmpty()) {
						int piece = M[y][x].pop();
						white.add(piece);
						if (piece == i) break;
					}
					
					while (!white.isEmpty()) {
						int piece = white.pop();
						
						// 이동하는 말들의 좌표 갱신
						int[] info = pos[piece];
						info[0] = ny;
						info[1] = nx;
						
						M[ny][nx].add(piece);
					}
					break;
				case 1: // 빨간색일땐 그대로 이동시킨다
					while (!M[y][x].isEmpty()) {
						int piece = M[y][x].pop();
                        
                        // 이동하는 말들의 좌표 갱신
						int[] info = pos[piece];
						info[0] = ny;
						info[1] = nx;
						
						M[ny][nx].add(piece);
                        
						if (piece == i) break;
					}
					break;
				}
				
				if (M[ny][nx].size() >= 4) {
					ans = cnt;
					break a;
				}
				
				if (cnt > 1000) break a;
			}
		}
		
		bw.write(ans + "");
		bw.close();
	}
}
