import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, M, K, A[][], land[][], my[] = {-1,-1,-1,0,0,1,1,1}, mx[] = {-1,0,1,-1,1,-1,0,1};
	static PriorityQueue<Integer>[][] trees; 
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);
		K = Integer.parseInt(s[2]);
		
		A = new int[N][N];
		land = new int[N][N];
		trees = new PriorityQueue[N][N]; // 나무의 나이 담기
		
		int i, j, y, x, age, cnt = 0;
		
		for (i = 0; i < N; i++) {
			s = br.readLine().split(" ");
			for (j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(s[j]);
				land[i][j] = 5; // 초기 양분 5
				trees[i][j] = new PriorityQueue<>();
			}
		}
		
		for (i = 0; i < M; i++) {
			s = br.readLine().split(" ");
			y = Integer.parseInt(s[0]) - 1;
			x = Integer.parseInt(s[1]) - 1;
			age = Integer.parseInt(s[2]);
			trees[y][x].add(age);
		}
		
		for (i = 0; i < K; i++) {
			// 봄 여름
			for (y = 0; y < N; y++) {
				for (x = 0; x < N; x++) {
					int size = trees[y][x].size(), idx = 0, nutrient = land[y][x], eat = 0;
					int[] ages = new int[size];
					// 나이순 정렬
					while (!trees[y][x].isEmpty()) 
						ages[idx++] = trees[y][x].poll();
					
					for (j = 0; j < size; j++) {
						if (ages[j] <= nutrient) { // 먹을 수 있을 때
							nutrient -= ages[j];
							eat += ages[j];
							trees[y][x].add(ages[j] + 1); // 나이 한살 업
						} else { // 못 먹을 때 사망하고 그 자리에 양분 뿌림
							land[y][x] += Math.floor(1.0 * ages[j] / 2);
						}
					}
					
					land[y][x] -= eat;
				}
			}
			
			// 가을 겨울
			for (y = 0; y < N; y++) {
				for (x = 0; x < N; x++) {
					for (int ag : trees[y][x]) {
						if (ag % 5 == 0) {
							for (int dir = 0; dir < 8; dir++) {
								int ny = y + my[dir], nx = x + mx[dir];
								
								if (ny < 0 || ny >= N || nx < 0 || nx >= N) continue;
								// 나무 번식
								trees[ny][nx].add(1);
							}
						}
					}
					// 양분 주기
					land[y][x] += A[y][x];
				}
			}
		}
		
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++)
				cnt += trees[i][j].size();
		
		bw.write(cnt + "");
		bw.close();
	}
}
