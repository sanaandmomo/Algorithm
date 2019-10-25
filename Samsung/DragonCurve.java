import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, my[] = {0, -1, 0, 1}, mx[] = {1, 0, -1, 0};
	static boolean[][] M = new boolean[101][101];
	static ArrayList<Integer> dragon = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		
		int i, j, k, size, dir, x, y, d, g, ans = 0;
		
		for (i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			x = Integer.parseInt(s[0]);
			y = Integer.parseInt(s[1]);
			d = Integer.parseInt(s[2]);
			g = Integer.parseInt(s[3]);
			
      // 초기 방향 추가
			dragon.add(d);
			
      // 세대만큼 돌아서
			for (j = 0; j < g; j++) {
				size = dragon.size(); // 현재 세대 크기만큼 돈다
				for (k = size - 1; k >= 0; k--) // 다음 세대는 이전 세대를 거꾸로 돌면서 방향을 전환해주면 된다
					dragon.add((dragon.get(k) + 1) % 4);
			}
			
      // 커브대로 그리기
			M[x][y] = true;
			
			for (j = 0; j < dragon.size(); j++) {
				dir = dragon.get(j);
				x += mx[dir];
				y += my[dir];
				M[x][y] = true;
			}
			
      // 드래곤 세대 초기화
			dragon.clear();
		}
		
		for (i = 0; i < 100; i++)
			for (j =0; j < 100; j++)
				if (M[i][j] && M[i + 1][j] && M[i][j + 1] && M[i + 1][j + 1])
					ans++;
		
		bw.write(ans + "");
		bw.close();
	}
}
