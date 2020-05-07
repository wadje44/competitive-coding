class Solution {
    public int numJewelsInStones(String J, String S) {
        if (J == null || J.length() == 0) {
            return 0;
        }
        boolean[] ascii = new boolean[256];
        int i=0, count=0;
        for(i=0; i<J.length(); i++) {
            ascii[(int)J.charAt(i)] = true;
        }
        for(i=0; i<S.length(); i++) {
            if(ascii[(int)S.charAt(i)]) {
                count++;
            }
        }
        return count;
    }
}
