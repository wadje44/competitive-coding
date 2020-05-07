import java.util.Arrays; 

class Solution {
    
    public int firstUniqChar(String s) {
        int[] index = new int[26];
        int[] count = new int[26];
        int min = s.length();
        
        for(int i=0; i<s.length(); i++) {
            count[(int) s.charAt(i) - 97]++;
            index[(int) s.charAt(i) - 97] = i;
        }
        for(int i=0; i<26; i++) {
            if(count[i] == 1) {
                int tempI = s.indexOf((char)(i+97));
                if(min > tempI) {
                    min = index[i];
                }
            }
        }
        if(min == s.length()) return -1;
        return min;
    }
}
