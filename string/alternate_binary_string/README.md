# Convert binary string to alternate binary string

given a binary string of '0's and '1's, reorder the string to make it alternating (e.g. 01010... or 10101...). You can perform following operation:

    1. Change '00' to '01' or '10' => cost = 1
    2. Change '11' to '01' or '10' => cost = 1 

Return the minimum cost required

    Examples:

        Input  -> 1100101
        Output ->  2

        Input  -> 0000101010
        Output -> 2

        Input  -> 00101
        Output -> 1

        Input  -> 0101010101
        Output -> 0

        Input  -> 01110
        Output -> 1

        Input  -> 110011
        Output -> 3
