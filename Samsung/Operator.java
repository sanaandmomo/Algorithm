import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int n, num[], operator[] = new int[4];
	static int max = Integer.MIN_VALUE;
	static int min = Integer.MAX_VALUE;
	
	static void dfs(int depth, int calc) {
		if (depth == n) { // 최댓값과 최솟값 갱신
			max = Math.max(max, calc);
			min = Math.min(min, calc);
			return;
		}
		
		for (int i=0; i<4; i++) {
			if (operator[i] == 0) continue;
			
      // 각 연산 명령에 대해서 재귀로 모두 다해본다
			switch(i) {
			case 0:
				operator[i]--;
				dfs(depth+1, calc+num[depth]);
				operator[i]++;
				break;
			case 1:
				operator[i]--;
				dfs(depth+1, calc-num[depth]);
				operator[i]++;
				break;
			case 2:
				operator[i]--;
				dfs(depth+1, calc*num[depth]);
				operator[i]++;
				break;
			case 3:
				operator[i]--;
				dfs(depth+1, calc/num[depth]);
				operator[i]++;
				break;
			}
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		n = Integer.parseInt(br.readLine());
		num = new int[n];
		
		String[] s = br.readLine().split(" ");
		for (int i=0; i<n; i++) num[i] = Integer.parseInt(s[i]);
		
		s = br.readLine().split(" ");
		for (int i=0; i<4; i++) operator[i] = Integer.parseInt(s[i]);

		dfs(1,num[0]);
		
		bw.write(max +"\n" + min + "\n");
		bw.close();
	}
}
