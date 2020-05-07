class BetterSolution {
    public int firstUniqChar(String s) {
        int res = Integer.MAX_VALUE;
        for(char c ='a';c<='z';c++){
            int index = s.indexOf(c);
            if(index!=-1&&index==s.lastIndexOf(c)){
                res = Math.min(index,res);
            }
        }
        return res == Integer.MAX_VALUE? -1:res;
    }
}
