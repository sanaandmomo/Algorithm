class Solution {
    public int solution(String s) {
        int len = s.length() / 2, // 맥시멈 길이는 문자열 길이의 절반
        i, j, k, ans = s.length(), cnt;
      
        // 맥시멈 길이만큼 반복
        for (i = 1; i <= len; i++) {
            cnt = 1;
            String make = "", stand = "", tmp ="";
        
            // 압축 기준 문자열 생성
            for (j = 0; j < i; j++) {
                stand += s.charAt(j);
            }

            for (j = i; j < s.length(); j += i) {
                tmp = "";
                
                // 다음 압축 문자열 생성
                for (k = j; k < Math.min(j + i, s.length()); k++) {
                    tmp += s.charAt(k);
                }
                
                if (stand.equals(tmp)) { // 같다면 카운트 증가
                    cnt++;
                } else { // 다르다면 기준 문자열을 더해주고 기준을 다음 압축 문자열로 바꿔줌
                    make += (cnt == 1 ? "" : cnt) + stand;
                    stand = tmp;
                    cnt = 1;
                }

            }
            // 나머지
            make += (cnt == 1 ? "" : cnt) + stand;
            ans = Math.min(ans, make.length());
        }

        return ans;
    }
}
