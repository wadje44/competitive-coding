class Solution {

    public boolean binarySquareroot(int square, int l, int h) {
        if(l <= h) {
            int mid = (l+h)/2;
            double midSquare = (double)mid*(double)mid;
            if(midSquare == square) return true;
            if(midSquare > square)
                return binarySquareroot(square, l, mid-1);
            return binarySquareroot(square, mid+1, h);
        }
        return false;
    }
    
    public boolean isPerfectSquare(int num) {
        if(num ==1 ) return true;
        int temp = Integer.toBinaryString(num).length(), reqL;
        if(temp%2 == 0)  reqL = temp/ 2;
        reqL = temp/2 + 1; 
        int temp2 = (1 << (reqL + 1)) - 1;
        return binarySquareroot(num, 1, temp2);
    }

    /*

    In Above method we can directly call binarySquareroot(num, 2, num/2) but this will have more iterations than above solution.
    Let's see following examples

            decimal      Binary
    number   5           101       -> number of bits 3
    Square   25          11001     -> number of bits 5

    number   6           110       -> number of bits 5
    Square   36          100100    -> number of bits 6

    number   2           10        -> number of bits 2
    Square   4           100       -> number of bits 4

    So, from above observation, when sqaure has number of bits 'n' then
    if n is even:
        square root of n will have n/2 bits
    if n is odd
        square root of n will have n/2 or (n/2)+1 bits
    
    so calculate number of bits in n, then according to above calculation take n/2 or (n/2)+1  bit max number,
    like 3 bit max number is 111 -> 7
    so for 36 root we will have binary search beatween 1 & 7 and not in 2 & 18.
    Optimized to save iterations.

    */
}

