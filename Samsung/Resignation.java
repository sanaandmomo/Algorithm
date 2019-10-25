import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int n, t[], p[], memo[];
	
	static int max(int day) {
    // 기저사례1 N + 1일 다음에 퇴사일 때
		if (day > n + 1) return -987654321;
		
    // 기저사례2 N + 1일에 퇴사일 때
		if (day == n + 1) return 0;
		
		if (memo[day] != -1) return memo[day];
		
    // 상담 안한 경우 VS 상담한 경우
		return memo[day] = Math.max(max(day + 1), max(day + t[day]) + p[day]);
	}
	
	public static void main(String[] args) throws Exception {
		n = Integer.parseInt(br.readLine());
		
		t = new int[n + 1];
		p = new int[n + 1];
		memo = new int[n + 1];
		
		for (int i = 1; i <=n; i++) {
			String[] s = br.readLine().split(" ");
			
			t[i] = Integer.parseInt(s[0]);
			p[i] = Integer.parseInt(s[1]);
			memo[i] = -1;
		}
		
		bw.write(max(1) + "");
		bw.close();
	}
}
