import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[][] roll = {
			{36,37,38,18,19,20,45,46,47,27,28,29}, 	 // U
			{33,34,35,51,52,53,24,25,26,42,43,44},	 // D
			{6,7,8,44,41,38,11,10,9,45,48,51},		  	 // F
			{2,1,0,53,50,47,15,16,17,36,39,42},		 // B
			{0,3,6,35,32,29,9,12,15,18,21,24},			 // L
			{8,5,2,26,23,20,17,14,11,27,30,33}		     // R
	};
	static char[] init = {'w','y','r','o','g','b'}, arr = new char[54], tmp[] = new char[3][3];
	static int[][][] cube = new int[6][3][3];
	
	static void rotate(int f, int cnt) {
		char[] q = new char[12];
		
		int i, j, k;

		for (i = 0; i < cnt; i++) {
      // 옆에면 돌려주기
			for (j = 0; j < 12; j++) q[j] = arr[roll[f][j]];
				
			for (j = 0; j < 12; j++) arr[roll[f][j]] = q[(j + 3) % 12];
			
      // 자기꺼 
			for (j = 0; j < 3; j++)
				for (k = 0; k < 3; k++)
					tmp[k][2 - j] = arr[cube[f][j][k]];
			
			for (j = 0; j < 3; j++)
				for (k = 0; k < 3; k++)
					arr[cube[f][j][k]] = tmp[j][k];
		}
	}
	
  // 명령 변환하기
	static int convert(char order) {
		int ret = -1;
		
		switch(order) {
		case '-': ret = 3; break;
		case '+': ret = 1; break;
		case 'U': ret = 0; break;
		case 'D': ret = 1; break;
		case 'F': ret = 2; break;
		case 'B': ret = 3; break;
		case 'L': ret = 4; break;
		case 'R': ret = 5; break;
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		int i, j, k, T, N;
		// 큐브 좌표 초기화
		for (i = 0; i < 6; i++)
			for (j = 0; j < 3; j++)
				for (k = 0; k < 3; k++)
					cube[i][j][k] = i * 9 + j * 3 + k;
		
		T = Integer.parseInt(br.readLine());
		
		while (T --> 0) {
      // 큐브 초기화
			for (i = 0; i < 6; i++)
				for (j = 0; j < 9; j++)
					arr[i * 9 + j] = init[i];
			
			N = Integer.parseInt(br.readLine());
			
			String[] s = br.readLine().split(" ");
			
			for (i = 0; i < N; i++) {
				int f = convert(s[i].charAt(0)), cnt = convert(s[i].charAt(1));
				// 명령대로 돌려주기
				rotate(f, cnt);
			}
			
      // 출력
			for (i = 0; i < 3; i++) {
				for (j = 0; j < 3; j++)
					bw.write(arr[cube[0][i][j]]);
				bw.newLine();
			}
		}
		
		bw.close();
	}
}
