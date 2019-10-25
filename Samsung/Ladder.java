import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, H, line[][];
	
	static int min(int cnt, int y) {
    // 3개 넘어가거나 사다리를 둘 수 없을 때
		if (cnt >= 4 || y > H) return 4;
		// 일대일 대응이 될 때
		if (oneToOne()) return cnt;
		
		int ret = 4, r, c;
		
		for (r = y; r <= H; r++) {
			for (c = 1; c < N; c++) {
        // 설치돼있거나 양옆에 설치돼있을 때
				if (line[r][c] == 1 || line[r][c - 1] == 1 || line[r][c + 1] == 1) continue;
				
				line[r][c] = 1; // 사다리 설치
				ret = Math.min(ret, min(cnt + 1, r)); // 다음 번
				line[r][c] = 0;
			}
		}
		
		return ret;
	}
	
  // 일대일 대응 여부
	static boolean oneToOne() {
		int i, j, cur;
		
		for (i = 1; i < N; i++) {
			cur = i;
			for (j = 1; j <= H; j++) {
				if (line[j][cur] == 1) cur++;
				else if (line[j][cur - 1] == 1) cur--;
			}
			
			if (cur != i) return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		H = Integer.parseInt(s[2]);
		
		line = new int[H + 1][N + 1];
		
		for (int r = 0; r < M; r++) {
			s = br.readLine().split(" ");
			line[Integer.parseInt(s[0])][Integer.parseInt(s[1])] = 1;
		}
		
		int ans = min(0, 1);
		bw.write((ans == 4 ? -1 : ans) + "");
		bw.close();
	}
}
