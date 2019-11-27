import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine()), 
				ans = -987654321, idx1 = 0, idx2 = 0, j,
				arrN[] = new int[N / 2 + 1], buhoArr[] = new int[N / 2];
		
		N /= 2;
		
		boolean[] preCur = new boolean[N];
		Queue<Integer> q = new LinkedList<>();
		
		String s = br.readLine();
        
        for (j = 0; j < s.length(); j++) {
			char c = s.charAt(j);
			
			if (j % 2 == 0) arrN[idx1++] = c - '0';
			else buhoArr[idx2++] = c;
		}
		
		for (long i = 0; i < (1 << N); i++) {
			for (j = 0; j < N; j++)
				if (((1 << j) & i) > 0) preCur[j] = true;
			
			q.add(arrN[0]);
			
			for (j = 0; j < N; j++) {
				if (preCur[j]) {
					int n = ((LinkedList<Integer>)q).pollLast();
					
					switch (buhoArr[j]) {
					case '+':
						q.add(n + arrN[j + 1]);
						break;
					case '-':
						q.add(n - arrN[j + 1]);
						break;
					case '*':
						q.add(n * arrN[j + 1]);
					}
					
					if (j < N - 1) preCur[j + 1] = false;
				} else {
					q.add(buhoArr[j]);
					q.add(arrN[j + 1]);
				}
			}
			
			int result = q.poll();
			
			while (!q.isEmpty()) {
				int buho = q.poll(), n = q.poll();
				
				switch (buho) {
				case '+':
					result += n;
					break;
				case '-':
					result -= n;
					break;
				case '*':
					result *= n;
				}
			}
			
			ans = Math.max(ans, result);
			
			for (j = 0; j < N; j++)
				preCur[j] = false;
		}
		
		bw.write(ans + "");
		bw.close();
	}
}
