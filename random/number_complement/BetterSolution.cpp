class Solution {
    public:
        long findComplement(long N) {
            return (~(N)) & ( (1L<<(int)(ceil(log2(N)))) - 1 );
        }
};
