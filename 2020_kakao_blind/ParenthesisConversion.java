import java.util.Stack;

class Solution {
   static String solution(String p) {	
    // 올바르다면
		if (isCorrect(p)) return p;
		
		int cnt1 = 0, cnt2 = 0, i;
		// u와 v로 분리
		for (i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			
			if (c == '(') cnt1++;
			else cnt2++;
			
			if (cnt1 == cnt2) break;
		}
		
		String u = p.substring(0, i + 1), v = p.substring(i + 1);
		
    // u가 올바르다면
		if (isCorrect(u)) 
			return u + solution(v);
			
    // u가 올바르지 않다면 새로운 u 생성
		String newU = "";
		
		for (i = 1; i < u.length() - 1; i++) {
			newU += u.charAt(i) == '(' ? ')' : '(';
		}
	  
    return "(" + solution(v) + ")" + newU;
  }
	
  // 올바른 괄호인지 체크
	static boolean isCorrect(String p) {
		Stack<Character> st = new Stack<>();
		
		for (int i = 0; i < p.length(); i++) {
			char c = p.charAt(i);
			
			try {
				if (c == '(') 
					st.add(c);
				else
					st.pop();
			} catch (Exception e) {
				return false;
			}
		}
		
		return st.size() == 0;
	}
	
}
