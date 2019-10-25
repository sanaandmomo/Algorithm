import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  // 1~5번 카메라가 보는 방향들
  // 0 -> 북 1 -> 동 2 -> 남 3 -> 서
	static final int[][][] CCTV = {
		{{0}, {1}, {2}, {3}},
		{{1, 3}, {0, 2}},
		{{0, 1}, {0, 3}, {2, 3}, {1, 2}},
		{{0, 1, 2}, {0, 2, 3}, {1, 2, 3}, {0, 1, 3}},
		{{0, 1, 2, 3}},
	};
  // 차례대로 북동남서일 때 더해야할 좌표값들
	static final int[][] DIRS = {
			{-1, 0}, {0, 1}, {1, 0}, {0, -1}
	};
	static int[][] map;
	static int N, M, CNT, MAX = 987654321;
  // cctv 좌표리스트
	static List<int[]> cctv = new ArrayList<>();
	
	static int min(int depth) {
		if (depth == CNT) {
			int i, j, ret = 0;
			
			for (i = 0; i < N; i++) 
				for (j = 0; j < M; j++) 
					if (map[i][j] == 0) ret++;
					
			return ret;
		}
		
		int ret = MAX, tmp[][] = new int[N][M], point[] = cctv.get(depth), y = point[0], x = point[1];
		
    // 현재 카메라의 스팩
		final int[][] camera = CCTV[map[y][x] - 1];
		
		copy(tmp, map);
		
    // 카메라 스팩대로 90도로 다 돌려서 모든 경우 다해보기
		for (int i = 0; i < camera.length; i++) {
			watch(camera[i], y, x); // 카메라 스팩대로 감시
			ret = Math.min(ret, min(depth + 1)); 
			copy(map, tmp);
		}
		
		return ret;
	}
	
  // 감시하기
	static void watch(int[] dirs, int y, int x) {
		int oriY = y, oriX = x;
		
		for (int i = 0; i < dirs.length; i++) {
			int[] way = DIRS[dirs[i]];
			int plusY = way[0], plusX = way[1];
			
			y = oriY + plusY;
			x = oriX + plusX;
			
      // 맵을 벗어나지 않고, 벽이 아닐 때까지
			while (0 <= y && y < N && 0 <= x && x < M && map[y][x] != 6) {
				if (map[y][x] < 1  || map[y][x] > 5) map[y][x] = -1; // 카메라가 아닐 때 감시 체크
				
				y += plusY;
				x += plusX;
			}
		}
	}
	
	static void copy(int[][] a, int[][] b) {
		int i, j;
		
		for (i = 0; i < N; i++) 
			for (j = 0; j < M; j++) 
				a[i][j] = b[i][j];
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		
		map = new int[N][M];
		
		int i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(s[j]);
				
				if (1 <= map[i][j] && map[i][j] <= 5) cctv.add(new int[] {i, j});
			}
		}
		
		CNT = cctv.size();
		
		bw.write(min(0) + "");
		bw.close();
	}
}
