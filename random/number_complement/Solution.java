class Solution {
    public int findComplement(int num) {
        int d = num, r, result = 0,two_power=1;
        do {
            r = d%2;
            d = d/2;
            if(r == 0) {
                result += two_power;
            }
            two_power *= 2;
        } while(d!=0);
        return result;
    }
}
