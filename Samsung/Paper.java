import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static final int SIZE = 10, MAX = 987654321;
	static int M[][] = new int [SIZE][SIZE], CNT;
	static ArrayList<int[]> list = new ArrayList<>();
	
	static int min(int depth, int[] paper) {
		if (depth == CNT) { // 붙여야 할 좌표를 다 봤을 때
			int i, j, sum = 0;
			// 못 붙인 곳이 있으면 max 리턴
			for (i = 0; i < SIZE; i++)
				for (j = 0; j < SIZE; j++)
					if (M[i][j] == 1) return MAX;
			
      // 다 붙였으면 사용한 색종이 수 리턴
			for (i = 1; i <= 5; i++) sum += paper[i];
			
			return 25 - sum;
		}
		
		int ret = MAX, coord[] = list.get(depth), y = coord[0], x = coord[1], i, j, k;
    
		// 만약 이미 붙여져 있으면 다음 좌표 붙이러 간다.
		if (M[y][x] == 0) return min(depth + 1, paper);
		
    // 한 좌표에 대해 다섯가지 타입의 색종이를 다 붙여본다.
		a: for (i = 1; i <= 5; i++) {
			if (paper[i] <= 0) continue; // 만약 색종이를 다썻으면 다음 색종이 쓰러가기.
			
      // 그 좌표에서 i크기 이상의 색종이를 못붙인다면 실패 max 리턴
			for (j = y; j < y + i; j++) 
				for (k = x; k < x + i; k++) 
					if (j < 0 || j >= SIZE || k < 0 || k >= SIZE || M[j][k] == 0) 
						break a;
			
      // 붙일 수 있다면 붙인다.
			cover(y, x, i, 0);
			paper[i]--; // i번 색종이 사용했으니 --
			ret = Math.min(ret, min(depth + 1, paper));
			paper[i]++; // 백트래킹
			cover(y, x, i, 1); // 백트래킹
		}
		
		return ret;
	}
	
	static void cover(int y, int x, int size, int color) {
		int i, j;
		
		for (i = y; i < y + size; i++)
			for (j = x; j < x + size; j++)
				M[i][j] = color;
	}
	
	public static void main(String[] args) throws Exception {
		int i, j, ans;
		
		for (i = 0; i < SIZE; i++) {
			String[] s = br.readLine().split(" ");
			for (j = 0; j < SIZE; j++) {
				M[i][j] = Integer.parseInt(s[j]);
				
				if (M[i][j] == 1) // 붙여야할 
					list.add(new int[] {i, j});
			}
		}
		
		CNT = list.size();
		ans = min(0, new int[] {0, 5, 5, 5, 5, 5});
		
		bw.write((ans == MAX ? -1 : ans) + "");
		bw.close();
	}
}
