class Solution {
    
    public void fillColour(int[][] image, int sr, int sc, int newColor, int oldColour) {
        if(sr != 0) {
            if((image[sr-1][sc] == oldColour)) {
                image[sr-1][sc] = newColor;
                fillColour(image, sr-1, sc, newColor, oldColour);
            }
        }
        if(sc!= 0) {
            if((image[sr][sc-1] == oldColour)) {
                image[sr][sc-1] = newColor;
                fillColour(image, sr, sc-1, newColor, oldColour);
            }
        }
        if(sr != (image.length-1)) {
            if((image[sr+1][sc] == oldColour)) {
                image[sr+1][sc] = newColor;
                fillColour(image, sr+1, sc, newColor, oldColour);
            }
        }
        if(sc != (image[sr].length-1)) {
            if((image[sr][sc+1] == oldColour)) {
                image[sr][sc+1] = newColor;
                fillColour(image, sr, sc+1, newColor, oldColour);
            }
        }
    }
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image[sr][sc] == newColor) return image;
        int oldColour = image[sr][sc];
        image[sr][sc] = newColor;
        fillColour(image, sr, sc, newColor, oldColour);
        return image;
    }
}
