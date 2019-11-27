import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, person[], MAX = 987654321, ans = MAX;
	static boolean[] visit, select, connect[];
	static ArrayList<Integer> a = new ArrayList<>(), b = new ArrayList<>();
	static Queue<Integer> q = new LinkedList<>();
	
	static void min(int depth, int idx) {
		if (depth >= 1) { // 지역이 1개라도 선거구를 만들 수 있다
			int i, sumA = 0, sumB = 0;
			
			for (i = 0; i < N; i++) {
				if (select[i]) a.add(i); // A 선거구 추가
				else b.add(i); // B 선거구 추가
			}
			
      // 둘 다 지역을 가지고 있고 둘 다 선거구가 연결돼있을 때
			if (a.size() > 0 && b.size() > 0 && isConnect(a, true) && isConnect(b, false)) {
        // 둘의 지역인들을 합산
				for (i = 0; i < N; i++) {
					if (select[i]) sumA += person[i];
					else sumB += person[i];
				}
				// 최솟값 
				ans = Math.min(ans,  Math.abs(sumA - sumB));
			}
			
			a.clear();
			b.clear();
		}
		
    // 백트래킹으로 모든 선거구 조합 만들기
		for (int i = idx; i < N; i++) {
			select[i] = true;
			min(depth + 1, i + 1);
			select[i]= false;
		}
		
	}
	// 선거구가 연결돼있는지
	static boolean isConnect(ArrayList<Integer> list, boolean type) {
		int first = list.get(0), cnt = 1, next, i;
		q.add(first);
		visit[first] = true;
		
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (next = 0; next < N; next++) {
      // 다음으로 연결돼있고, 선거구 타입이 똑같고, 방문하지도 않았을 때
				if (connect[cur][next] && select[next] == type && !visit[next]) {
					visit[next] = true;
					q.add(next);
					cnt++;
				}
			}
		}
		// visit 배열 초기화
		for (i = 0; i < N; i++)
			 visit[i] = false;
		
    // 선거구 리스트 사이즈와 카운트가 같다면 true
		return list.size() == cnt;
	}
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		person = new int[N];
		visit = new boolean[N];
		select = new boolean[N];
		connect = new boolean[N][N];
		
		String[] s = br.readLine().split(" ");
		
		int i, j, cnt, a;
		
		for (i = 0; i < N; i++)
			person[i] = Integer.parseInt(s[i]);
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			cnt = Integer.parseInt(s[0]);
			
			for (j = 0; j < cnt; j++) {
				a = Integer.parseInt(s[j + 1]) - 1;
				connect[i][a] = connect[a][i] = true;
			}
		}
		
		min(0, 0);
		
		ans = ans == MAX ? -1 : ans;
		
		bw.write(ans + "");
		bw.close();
	}
}
