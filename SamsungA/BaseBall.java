import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M[][], MIN = -987654321;
	
	static int max(int depth, int[] player, boolean[] positioned) {
		if (depth == 9) { // 9명을 랜덤하게 다 뽑았을 때
			int outCnt = 0, curPlayer = player[0], cursor = 0, score = 0, i, j, field[] = {0, 0, 0, 0};
			
			for (i = 0; i < N; i++) { // n 이닝을 진행한다.
				while (true) {
					int result = M[i][curPlayer]; // player의 결과
					
					if (result == 0) { // 아웃일 때
						curPlayer = player[++cursor % 9]; // 다음 플레이어 갱신
						
						if (++outCnt >= 3)  break; // 만약 아웃칸트 3이면 끝
							
						continue;
					}
					
          // 필드에 나가있는 애들 좌표 갱신
					for (j = 3; j >= 1; j--) {
						if (field[j] > 0) {
							field[j] = 0;
							
							if (j + result > 3) score++; // 홈으로 들어올 때 스코어++
							else field[j + result] = 1; // 아니면 이동
						}
					}
					
					if (result == 4) score++; // 홈런 때렸을 때 스코어++
					else field[result] = 1; // 아니면 이동
					
					curPlayer = player[++cursor % 9]; // 플레이어 교체
				}
				// 이닝 끝나면 필드랑 아웃칸트 초기화
				for (j = 1; j <= 3; j++) field[j] = 0;
				outCnt = 0;
			}
			
			return score;
		}
		// 1번 선수는 이미 4번 타자로 정해져있기 때문에 넘어간다.
		if (depth == 3) return max(depth + 1, player, positioned);
		
		int ret = MIN, i;
    
		// 백트래킹으로 9명 무작위로 배치
		for (i = 1; i < 9; i++) {
			if (positioned[i]) continue;
			
			positioned[i] = true;
			player[depth] = i;
			ret = Math.max(ret, max(depth + 1, player, positioned));
			player[depth] = 0;
			positioned[i] = false;
		}
			
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		M = new int[N][9];
		
		int i, j, player[] = new int[9];
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < 9; j++)
				M[i][j] = Integer.parseInt(s[j]);
		}
		
		player[3] = 0;
		
		bw.write(max(0, player,  new boolean[9]) + "");
		bw.close();
	}
}
