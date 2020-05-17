class Solution {

    public List<Integer> findAnagrams(String s, String p) {
        char[] parr = p.toCharArray();
        char[] sarr = s.toCharArray();
        int[] anagram = new int[26];
        List<Integer> result = new ArrayList<Integer>();
        int pn=p.length(), temppn=p.length(),i,tempsn=s.length(),si=0;
        for(i=0;i<pn;i++) {
            anagram[parr[i]-97]++;
        }
        int[] tempAnagram = anagram.clone();
        for(i=0;i<tempsn;i++) {
            if(temppn == 0 && sarr[i]==sarr[si]) {
                si++;
                result.add(si);
                continue;
            } else if(temppn == 0) {
                temppn++;
                tempAnagram[sarr[si]-97]++;
                si++;
            }
            if(tempAnagram[sarr[i]-97] == 0) {
                if(anagram[sarr[i]-97] != 0) {
                    int j;
                    for(j=si; sarr[i] != sarr[j]; j++) {
                        tempAnagram[sarr[j]-97]++;
                        temppn++;
                    }
                    si = j+1;
                } else {
                    temppn = pn;
                    tempAnagram = anagram.clone();
                    si = i + 1;
                }
            } else {
                tempAnagram[sarr[i]-97]--;
                temppn--;
                if(temppn==0) {
                    result.add(si);
                }
            }
        }
        return result;
    }
}
