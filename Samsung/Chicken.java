import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, MAX = 987654321;
	static List<int[]> home = new ArrayList<>(), chicken = new ArrayList<>();
  
  // 재귀로 모든 경우 다해봄
	static int min(int m, int s, Stack<int[]> picked) {
		if (m == 0) {
			int sum = 0, dist;
			// 모든 집과
			for (int[] h : home) {
				dist = MAX;
				
				for (int[] c : picked) // 고른 치킨집들에 대해서
					dist = Math.min(dist, Math.abs(h[0] - c[0]) + Math.abs(h[1] - c[1])); //최소 거리 누적
				
				sum += dist;
			}
			
			return sum;
		}
		
		int ret = MAX, i;
		
		for (i = s; i < chicken.size(); i++) {
			picked.add(chicken.get(i)); // 치킨집 고르기
			ret = Math.min(ret, min(m - 1, i + 1, picked)); // 다음
			picked.pop(); // 백트래킹
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
	
		int i, j;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				if (s[j].equals("1")) home.add(new int[] {i + 1, j + 1});
				else if (s[j].equals("2")) chicken.add(new int[] {i + 1, j + 1});
			}
		}
		
		bw.write(min(M, 0, new Stack<>()) + "");
		bw.close();
	}
}

