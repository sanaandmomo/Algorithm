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
		
		boolean[] preCur = new boolean[N]; // 먼저 계산할 부호
		Queue<Integer> q = new LinkedList<>(); // 계산을 진행할 큐
		
		String s = br.readLine();
        
        	for (j = 0; j < s.length(); j++) {
			char c = s.charAt(j);
			// 숫자와 부호 따로 담아주기
			if (j % 2 == 0) arrN[idx1++] = c - '0'; 
			else buhoArr[idx2++] = c;
		}
		// 비트마스킹으로 모든 경우를 다 해본다
		for (long i = 0; i < (1 << N); i++) {
			for (j = 0; j < N; j++)
				if (((1 << j) & i) > 0) preCur[j] = true; 
			// 일단 첫번째 숫자부터 집어넣기
			q.add(arrN[0]);
			
			for (j = 0; j < N; j++) {
				if (preCur[j]) { // 먼저 계산해야될 부호라면 (괄호로 쌓여져 있다는 뜻)
					int n = ((LinkedList<Integer>)q).pollLast(); // 마지막에 넣은 숫자를 뺀다
					// 부호에 따라서 다음 수와 계산을 해준다
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
					// j가 true면 j+1은 숫자이다. 숫자는 부호가 아니므로 넘겨준다.
					if (j < N - 1) preCur[j + 1] = false;
				} else {// 아니라면 큐에 순서대로 넣기
					q.add(buhoArr[j]);
					q.add(arrN[j + 1]);
				}
			}
			// 괄호부터 우선 계산된 결과
			int result = q.poll();
			// 나머지 연산을 실행
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
			// 최댓값 갱신
			ans = Math.max(ans, result);
			// 먼저 계산할 부호 배열 초기화
			for (j = 0; j < N; j++)
				preCur[j] = false;
		}
		
		bw.write(ans + "");
		bw.close();
	}
}
