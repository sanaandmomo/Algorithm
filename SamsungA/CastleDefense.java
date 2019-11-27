import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, D, B[][], test[][], tmp[];
	
	static int max(int depth, int s) throws Exception {
		if (depth == 3) { // 궁수 3명을 랜덤하게 배치했을 때
			int i, j, k, l, cnt = 0;
			// test맵에 옮겨담기
			for (i = 0; i <= N; i++)
				for (j = 0; j < M; j++)
					test[i][j] = B[i][j];
			
			Queue<int[]> q = new LinkedList<>();
			
			// 행번만큼 진행
			for (i = 0 ; i < N; i++) {
				// 궁수 공격
				for (j = 0; j < M; j++) {
					if (test[N][j] == 0) continue; // 궁수가 아니면 넘어감
					
					int minDist = D, y = N, x = M;
					// 궁수의 범위 내에서 제일 가까운 적을 찾는다
					for (k = Math.max(0, N - D); k < N; k++) {
						for (l = Math.max(0, j - D); l < Math.min(M, j + D + 1); l++) {
							if (test[k][l] == 1) { // 적일 때
								int dist = N - k + Math.abs(j - l); // 거리 계산
								
                // 거리가 D 이하고 최소 거리보다 짧거나 같아도 왼쪽에 있을 때 갱신
								if (dist <= D && (dist < minDist || (dist == minDist && l < x))) { 
									minDist = dist;
									y = k;
									x = l;
								} 
							}
						}
					}
					// 겨냥한 적을 큐에다 넣는다
					if (x != M) q.add(new int[] {y, x});
				}
				
        // 큐에서 하나씩 적들을 빼서 죽여준다.
				while (!q.isEmpty()) {
					int[] enemy = q.poll();
					
					if (test[enemy[0]][enemy[1]] == 1) {
						test[enemy[0]][enemy[1]] = 0;
						cnt++;
					}
				}
        
				// 굳이 마지막 턴에 적 이동을 해줄 필요가 없음
				if (i == N - 1) break;
				
				// 적 이동
				for (j = 0; j < M; j++) {
					for (k = i; k < N - 1; k++)
						tmp[k] = test[k][j];
					
                    test[i][j] = 0;
                    
					for (k = i; k < N - 1; k++)
						test[k + 1][j] = tmp[k];
				}
			}
			return cnt;
		}
		
		int ret = 0, i;
		// 백트래킹으로 궁수를 배치한다
		for (i = s; i < M; i++) {
			B[N][i] = 2; // 2번이 궁수라는 뜻
			ret = Math.max(ret, max(depth + 1, i + 1));
			B[N][i] = 0;
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		D = Integer.parseInt(s[2]);
		B = new int[N + 1][M];
		test = new int[N + 1][M];
		tmp = new int[N];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < M; j++)
				B[i][j] = Integer.parseInt(s[j]);
		}
		
		bw.write(max(0, 0) + "");
		bw.close();
	}
}
