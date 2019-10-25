import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
  // 현재 톱니바퀴의 톱니가, 몇번째 톱니바퀴의, 톱니와 만나는지
	static int[][][] meet = {
			{{2, 1, 6}},
			{{6, 0, 2}, {2, 2, 6}},
			{{6, 1, 2}, {2, 3, 6}},
			{{6, 2, 2}}
	};
	static int gears[][] = new int[4][8], roll[] = new int[4], score[] = {1, 2, 4, 8}, K;
	
  // 재귀로 몇번재 톱니바퀴를 어떤 방향으로 돌릴지 체크
	static void check(int num, int dir) {
		roll[num] = dir;
		
		for (int i = 0; i <  meet[num].length; i++) {
			int[] v = meet[num][i];
			// 체크돼있지 않고, 만나는 톱니의 극이 다르다면
			if (roll[v[1]] == 0 && gears[num][v[0]] != gears[v[1]][v[2]]) 
				check(v[1], -dir);
		}
	}
	
  // 톱니 돌리기
	static void roll(int[] gear, int dir) {
		boolean clock = dir == 1;
		int tmp = clock ? gear[7] : gear[0], 
				s = clock ? 6 : 1,
						i;
		
		for (i = 0; i < 7; i++) {
			gear[s + dir] = gear[s];
			s -= dir;
		}
		
		gear[clock ? 0 : 7] = tmp;
	}
	
	public static void main(String[] args) throws Exception {
		int i, j, ans = 0;
		
		for (i = 0; i < 4; i++) {
			String s = br.readLine();
			for (j = 0; j < 8; j++)
				gears[i][j] = s.charAt(j) - '0';
		}
		
		K = Integer.parseInt(br.readLine());
		
		for (i = 0; i < K; i++) {
			String[] s = br.readLine().split(" ");
			int num = Integer.parseInt(s[0]) - 1, dir = Integer.parseInt(s[1]);
			
      // 어떻게 돌릴지 체크
			check(num, dir);
			
      // 체크 완료되면 돌려주기
			for (j = 0; j < 4; j++)
				if (roll[j] != 0)
					roll(gears[j], roll[j]);
		
      // 체크 초기화
			for (j = 0; j < 4; j++)
				roll[j] = 0;
		}
		
		for (i = 0; i < 4; i++)
			if (gears[i][0] == 1)
				ans += score[i];
		
		bw.write(ans + "");
		bw.close();
	}
}
