import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int n, m, board[][];
  // 덮을 수 있는 모든 타입들의 좌표
	static int[][][] coverType = {
			{{0,0},{0,1},{1,0},{1,1}},
			{{0,0},{0,1},{0,2},{0,3}},
			{{0,0},{1,0},{2,0},{3,0}},
			{{0,0},{1,0},{2,0},{2,1}},
			{{0,0},{0,1},{0,2},{1,0}},
			{{0,0},{0,1},{1,1},{2,1}},
			{{0,0},{0,1},{0,2},{-1,2}},
			{{0,0},{0,1},{-1,1},{-2,1}},
			{{0,0},{1,0},{1,1},{1,2}},
			{{0,0},{0,1},{1,0},{2,0}},
			{{0,0},{0,1},{0,2},{1,2}},
			{{0,0},{1,0},{1,1},{2,1}},
			{{0,0},{0,1},{-1,1},{-1,2}},
			{{0,0},{1,0},{1,-1},{2,-1}},
			{{0,0},{0,1},{1,1},{1,2}},
			{{0,0},{1,-1},{1,0},{1,1}},
			{{0,0},{1,0},{2,0},{1,1}},
			{{0,0},{0,1},{0,2},{1,1}},
			{{0,0},{1,0},{2,0},{1,-1}}
	};
	
  // 덮기
	static int cover(int type, int y, int x) {
		int ret = 0;
		
		for (int i=0; i<4; i++) {
			int ny = y + coverType[type][i][0];
			int nx = x + coverType[type][i][1];
			
      // 벗어날 경우 아주 작은 값 리턴
			if (ny<0 || ny>=n || nx<0 || nx>=m) return -987654321;
			
			ret += board[ny][nx];
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		n = Integer.parseInt(s[0]);
		m = Integer.parseInt(s[1]);
		
		board = new int[n][m];
		
		for (int i=0; i<n; i++) {
			s = br.readLine().split(" ");
			for (int j=0; j<m; j++) {
				board[i][j] = Integer.parseInt(s[j]);
			}
		}
		
		int ans = -987654321;
		// 모든 좌표에 대해 19가지의 경우들을 다 덮어본다
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				for (int type=0;type<19; type++) {
					ans = Math.max(ans, cover(type, i, j));
				}
			}
		}
		
		bw.write(ans + "");
		bw.close();
	}
}
