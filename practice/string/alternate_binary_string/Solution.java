class Solution {
    public void alternateBinaryString(int[][] coordinates) {
        
        String s = "0000101010";
        char[] arr = s.toCharArray();
        int oneStart = 0, zeroStart = 0;
        
        for(int i = 0; i<s.length(); i++ ) {
            if(i % 2 == 0){
                if(arr[i] != '1') {
                    oneStart++;
                } else {
                    zeroStart++;
                }
                
            } else {
                if(arr[i] != '0') {
                    oneStart++;
                } else {
                    zeroStart++;
                }
            }
        }
        
        if(oneStart < zeroStart)  System.out.println(oneStart);
        else System.out.println(zeroStart);
    }
}
