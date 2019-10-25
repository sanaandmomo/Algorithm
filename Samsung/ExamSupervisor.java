import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int N, A[], B, C;
	
	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		A = new int[N];
		String[] s = br.readLine().split(" ");
		int i;
		long cnt = N;
		
		for (i = 0; i < N; i++) 
			A[i] = Integer.parseInt(s[i]);
			
		s = br.readLine().split(" ");
		B = Integer.parseInt(s[0]);
		C = Integer.parseInt(s[1]);
		
		for (i = 0; i < N; i++) {
			int a = A[i] - B;
			
			if (a > 0) cnt += (int)Math.ceil(1.0 * a / C);
		}
		
		bw.write(cnt + "");
		bw.close();
	}
}
