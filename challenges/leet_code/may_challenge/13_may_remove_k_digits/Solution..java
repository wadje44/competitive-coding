class Solution {
    public String removeKdigits(String num, int k) {
        
        int i, n=num.length(), ansi=0;
        if(n == k) return "0";
        char[] arr = num.toCharArray();
        Deque<Character> ans = new LinkedList<Character>();
        ans.addLast(arr[0]);
        for(i=1;i<n;i++) {
            while(ans.size()>0 && k>0 && ans.peekLast()>arr[i]) {
                ans.removeLast();
                k--;
            }
            if(ans.size()==0 && arr[i]=='0') {
                continue;
            }
            ans.addLast(arr[i]);
            
        }
        while(k>0) {
            ans.removeLast();
            k--;
        }
        String result="";
        while(!ans.isEmpty()){
            result += ans.pop();
        }
        if(result.equals("")) return "0";
        return result;

    }
}
