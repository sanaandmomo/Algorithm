import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, K, circle[][];
	static boolean erase[][], isErase = false;
	
  // 인접한 좌표에 대해 지워야하는 플래그 찍기
	static void delete(int y1, int x1, int y2, int x2) {
		if (circle[y1][x1] != 0 &&  circle[y2][x2] != 0 && circle[y1][x1] == circle[y2][x2]) erase[y1][x1] = erase[y2][x2] = isErase = true;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		K = Integer.parseInt(s[2]);
		
		circle = new int[N + 2][M + 2]; // 인접 조건 따지기 좋게 크게 잡기
		erase = new boolean[N + 2][M + 2];
		
		int i, j, l, x, d, k, tmp[] = new int[M + 1];
		
		double avg, cnt;
		
		for (i = 1; i <= N; i++) {
			s = br.readLine().split(" ");
			for (j = 1; j <= M; j++) 
				circle[i][j] = Integer.parseInt(s[j - 1]);
		}
    //입력
    
		
		for (i = 0; i < K; i++) {
			s = br.readLine().split(" ");
			x = Integer.parseInt(s[0]);
			d = Integer.parseInt(s[1]);
			k = Integer.parseInt(s[2]);
			
			// 방향에 따라 칸 수 조정
			k = d == 0 ? k : (M - k); 
			
			// x의 부호인 원판을 d방향으로 k칸 돌리기
			for (j = x; j <= N; j += x) { 
				for (l = 1; l <= M; l++) tmp[l] = circle[j][l]; // 해당 원판 복사
				
				for (l = 1; l <= M; l++) {
					int idx = (l + k) % M;
					idx = idx == 0 ? M : idx;
					
					circle[j][idx] = tmp[l]; // 원판 돌리기
				}
			}
			
			isErase = false;
			
			// 인접한 좌표들에 대하여 지워주기
			for (j = 1; j <= N; j++) {
				for (l = 1; l <= M; l++) {
					delete(j - 1, l, j, l);
					delete(j + 1, l, j, l);
					delete(j, l - 1, j, l);
					delete(j, l + 1, j, l);
				}
				
				delete(j, 1, j, M);
			}
			
			// 인접한 곳이 없어 지워진 곳이 없을 때
			if (!isErase) {
				avg = cnt = 0;
				
				for (j = 1; j <= N; j++) {
					for (l = 1; l <= M; l++) {
						if (circle[j][l] <= 0) continue;
						avg += circle[j][l];
						cnt++;
					}
				}
				// 평균 계산
				avg /= 1.0 * cnt;
				
				for (j = 1; j <= N; j++) {
					for (l = 1; l <= M; l++) {
						if (circle[j][l] <= 0) continue;
						if (circle[j][l] > avg) // 크면 1 빼고
							circle[j][l]--;
						else if (circle[j][l] < avg)  // 작으면 1 더한다
							circle[j][l]++;
					}
				}
			} else { // 지워줘야하는 곳이 있다면 지워주기
				for (j = 1; j <= N; j++) {
					for (l = 1; l <= M; l++) {
						if (erase[j][l]) circle[j][l] = 0;
						erase[j][l] = false; // 다시 초기화
					}
				}
				
			}
		} 
		
		int ans = 0;
		
		for (i = 1; i <= N; i++)
			for (j = 1; j <= M; j++)
				ans += circle[i][j];
		
		bw.write(ans + "");
		bw.close();
	}
}
