import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int R, C, K, M[][] = new int[100][100], cnt[] = new int[101];
		
		PriorityQueue<Node> q = new PriorityQueue<>();

		String[] s = br.readLine().split(" ");

		R = Integer.parseInt(s[0]) - 1;
		C = Integer.parseInt(s[1]) - 1;
		K = Integer.parseInt(s[2]);

		int i, j, k, ans = -1, curR = 3, curC = 3;
		
		for (i = 0; i < 3; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < 3; j++)
				M[i][j] = Integer.parseInt(s[j]);
		}
    // 여기까지 입력
  
    // 마지노선 100
		for (i = 0; i <= 100; i++) {
			if (M[R][C] == K) { // 목표가 됐을 때
				ans = i;
				break;
			}

			if (curR >= curC) { // R 연산
				int nextC = -1;
				
				for (j = 0; j < curR; j++) { // 모든행에 대해서 실행
					for (k = 0; k < curC; k++)
						if (M[j][k] != 0)
							cnt[M[j][k]]++; // 열의 숫자들을 센다
					
					for (k = 1; k <= 100; k++) {
						if (cnt[k] != 0)
							q.add(new Node(k, cnt[k])); // 그 숫자들을 다시 우선순위 큐에 넣는다
						
						cnt[k] = 0; // cnt 배열 초기화
					}

					nextC = Math.max(nextC, q.size() * 2); // 다음 열의 길이 초기화
					
					int idx = 0;
					
					while (!q.isEmpty()) { // 정렬된 노드를 가지고 배열을 재정의해준다
						Node n = q.poll();
						M[j][idx++] = n.num;
						M[j][idx++] = n.cnt;
					}
					
					for (k = idx; k < curC; k++) // 나머지 뒷부분은 0으로 초기화
						M[j][k] = 0;
				}

				curC = Math.min(nextC, 100);
			} else { // C 연산 (r연산과 동일)
				int nextR = -1;
				
				for (j = 0; j < curC; j++) {
					for (k = 0; k < curR; k++) 
						if (M[k][j] != 0)
							cnt[M[k][j]]++;
						
					for (k = 1; k <= 100; k++) {
						if (cnt[k] != 0)
							q.add(new Node(k, cnt[k]));
						
						cnt[k] = 0;
					}
					
					nextR = Math.max(nextR, q.size() * 2);
					
					int idx = 0;
					
					while (!q.isEmpty()) {
						Node n = q.poll();
						
						M[idx++][j] = n.num;
						M[idx++][j] = n.cnt;
					}
					
					for (k = idx; k < curR; k++)
						M[k][j] = 0;
				}
				
				curR = Math.min(nextR, 100);
			}

		}

		bw.write(ans + "");
		bw.close();
	}
}

class Node implements Comparable<Node> {
	int num, cnt;
	
	Node (int c, int d) {
		num = c;
		cnt = d;
	}

	public int compareTo(Node o) {
		if (o.cnt > this.cnt ) { // 개수가 많은 순으로
			return -1;
		} else if (o.cnt == this.cnt ) { // 개수가 많다면 숫자 크기순으로 
			return o.num > this.num ? -1 : 1;
		}
		return 1;
	}
}
