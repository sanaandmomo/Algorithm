import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	// 0번째 idx -> 그 자리에서 얻는 점수, 1~5 idx -> 주사위가 나왔을 때 가야할 idx
	static int[][] rule = {
			{0,1,2,3,4,5},
			{2,2,3,4,5,9},
			{4,3,4,5,9,10},
			{6,4,5,9,10,11},
			{8,5,9,10,11,12},
			{10,6,7,8,21,22},
			{13,7,8,21,22,23},
			{16,8,21,22,23,31},
			{19,21,22,23,31,32},
			{12,10,11,12,13,14},
			{14,11,12,13,14,15},
			{16,12,13,14,15,16},
			{18,13,14,15,16,17},
			{20,19,20,21,22,23},
			{22,15,16,17,18,27},
			{24,16,17,18,27,28},
			{26,17,18,27,28,29},
			{28,18,27,28,29,30},
			{30,24,25,26,21,22},
			{22,20,21,22,23,31},
			{24,21,22,23,31,32},
			{25,22,23,31,32,32},
			{30,23,31,32,32,32},
			{35,31,32,32,32,32},
			{28,25,26,21,22,23},
			{27,26,21,22,23,31},
			{26,21,22,23,31,32},
			{32,28,29,30,31,32},
			{34,29,30,31,32,32},
			{36,30,31,32,32,32},
			{38,31,32,32,32,32},
			{40,32,32,32,32,32},
			{0,32,32,32,32,32},
	};
	static final int LEN = 10, HORSE = 4;
	static int M[] = new int[LEN], pos[] = new int[HORSE];
	static boolean[] locate = new boolean[33];
	
	static int max(int turn, int score) {
		if (turn == LEN) return score;
		
		int ret = -1, i, dice, curPos, nextPos;
		
		for (i = 0; i < HORSE; i++) {
			// 현재 말의 정보
			int[] v = rule[pos[i]];
			
			// 현재 나온 주사위
			dice = M[turn];
			
			// 다음 위치
			nextPos = v[dice];
			
			// 현재 말이 도착했거나, 이동하려는 칸에 말이 있다면
			if (pos[i] == 32 || (nextPos != 32 && locate[nextPos])) continue;
			
			// 현재 위치 저장
			curPos = pos[i];
			
			// 이동
			locate[curPos] = false; // 현재 있던 곳 false
			locate[nextPos] = true; // 이동하는 곳 true
			pos[i] = nextPos; // 위치 갱신
			ret = Math.max(ret, max(turn + 1, score + rule[nextPos][0])); // 다음 위치의 점수를 더해준다
			pos[i] = curPos; // 위치 되돌리기 (백트래킹)
			locate[nextPos] = false; // 이동했던 곳  false
			locate[curPos] = true; // 현재있는 곳 true
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		String[] s = br.readLine().split(" ");
		
		for (int i = 0; i < LEN; i++)
			M[i] = Integer.parseInt(s[i]);
		
		bw.write(max(0, 0) + "");
		bw.close();
	}
}
