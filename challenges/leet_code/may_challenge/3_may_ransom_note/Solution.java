class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) return false;
        int[] ascii = new int[26];
        int i=0, count=0;
        for(i=0; i<ransomNote.length(); i++) {
            ascii[(int)ransomNote.charAt(i) - 97]++;
            count++;
        }
        for(i=0; i<magazine.length(); i++) {
            
            if(ascii[(int)magazine.charAt(i) - 97] != 0) {
                ascii[(int)magazine.charAt(i) - 97]--;
                count--;
            }
        }
        if(count != 0) {
            return false;
        }
        return true;
    }
}
