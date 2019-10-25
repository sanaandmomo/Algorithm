import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, L, M[][], my[] = {-1, 0, 1, 0}, mx[] = {0, 1, 0, -1};
	static boolean[][] fit; // 경사로 놓은 위치 검사
  
	// y, x에서 dir 방향으로 경사로 놓을 수 있는지 검사
	static boolean isFit(int y, int x, int dir) {
		if (fit[y][x]) return false;
		
    // 일단 y, x에 설치하고 prev에 그 값 저장
		int oriY = y, oriX = x, prev = M[y][x], i;
		
		for (i = 0; i < L - 1; i++) {
			y += my[dir];
			x += mx[dir];
			
      // 범위 넘어가거나 이전 높이와 현재 높이가 다르거나, 이미 설치돼있다면
			if (y < 0 || y >= N || x < 0 || x >= N || prev != M[y][x] || fit[y][x]) return false;
			
			prev = M[y][x]; // 이전 갱신
		}
		
		y = oriY;
		x = oriX;
		
    // 설치 가능하면 설치 좌표 저장
		fit[y][x] = true;
		for (i = 0; i < L - 1; i++) {
			y += my[dir];
			x += mx[dir];
			fit[y][x] = true;
		}
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		L = Integer.parseInt(s[1]);
		M = new int[N][N];
		fit= new boolean[N][N];
		
		int r, c, cnt = 0;
		
		for (r = 0; r < N; r++) {
			s = br.readLine().split(" ");
			for (c = 0; c < N; c++)
				M[r][c] = Integer.parseInt(s[c]);
		}
		
    // 행 검사
		out: for (r = 0; r < N; r++) {
			int prev = M[r][0]; // 이전 높이 저장
			
			for (c = 1; c < N; c++) {
				int cur = M[r][c]; // 현재 높이
				
        // 이전과 현재 높이 차이가 1 초과거나, 경사로를 둘 수 없을 때
				if (Math.abs(cur - prev) > 1 || (cur > prev && !isFit(r, c - 1, 3)) || (cur < prev && !isFit(r, c, 1))) continue out;
				
				prev = cur; // 이전 갱신
			}
			
			cnt++;
		}
		
    // 설치 좌표 리셋
		for (r = 0; r < N; r++)
			for (c = 0; c < N; c++)
				fit[r][c] = false;
		
    // 열 검사
		out: for(c = 0; c < N; c++) {
			int prev = M[0][c];
			
			for (r = 1; r < N; r++) {
				int cur = M[r][c];
				
				if (Math.abs(cur - prev) > 1 || (cur > prev && !isFit(r - 1, c, 0)) || (cur < prev && !isFit(r, c, 2))) continue out;
				
				prev = cur;
			}
			
			cnt++;
		}
		
		bw.write(cnt + "");
		bw.close();
	}
}
