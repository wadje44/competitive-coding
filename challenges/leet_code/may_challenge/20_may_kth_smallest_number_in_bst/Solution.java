import java.util.Deque;
import java.util.*; 
import java.io.*; 
/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Solution {
    public static void getKHeightElement(TreeNode node, int k, int[] parentIndexAns) {
        if(node != null) {
            if(parentIndexAns[1] == -1) {
                getKHeightElement(node.left, k, parentIndexAns);
                parentIndexAns[0] += (1);
                if((parentIndexAns[0]) == k) {
                    parentIndexAns[1] = node.val;
                    return;
                }
                getKHeightElement(node.right, k,parentIndexAns);
            }
        }
    }
    
    public static int kthSmallest(TreeNode root, int k) {
        int[] parentIndexAns = new int[] {0, -1};
        getKHeightElement(root, k, parentIndexAns);
        return parentIndexAns[1];
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] paramsInString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()),2);
            String[] paramsStringArray = paramsInString[0].substring(1, paramsInString[0].length()-1).split(",");
            TreeNode head = createTree(paramsStringArray);
            printTree(head);
            System.out.println();
            String[] result = new String[] {Integer.toString(kthSmallest(head, Integer.parseInt(paramsInString[1])))};
            writeLinesAsStrings(String.format("test_cases/output/%d.txt",(i+1)), result);
        }
    }

    public static String[] readLinesAsStrings(String path, int numberOfLines){
        String[] result = new String[numberOfLines];
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            int lineNumber=0; 
            while (lineNumber<numberOfLines && ((result[lineNumber++] = br.readLine()) != null));
            br.close();
        } catch(IOException E) {
            System.out.println("IOException caught while reading file: " + E);
        } 
        return result;
    }

    public static void writeLinesAsStrings(String path, String[] data){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
             for(int i=0; i<data.length; i++) {
                writer.write(data[i]);
                if(i!=(data.length-1)) writer.newLine();
            }
            writer.close();
        } catch(IOException E) {
            System.out.println("IOException caught while reading file: " + E);
        } 
    }

    public static TreeNode createTree (String arr[]) {
        if(arr.length == 0) return null;
        if(arr.length == 1 ) 
            if(arr[0].equals("null")) return null;
            else return new TreeNode(Integer.parseInt(arr[0]));

        TreeNode head = new TreeNode(Integer.parseInt(arr[0]));
        int i=1;
        Deque<TreeNode> deque = new LinkedList<TreeNode>();
        deque.addLast(head);
        while(i<arr.length && deque.size()!=0) {
            TreeNode temp = deque.pop();
            if(!arr[i].equals("null")) {
                temp.left = new TreeNode(Integer.parseInt(arr[i]));
                deque.addLast(temp.left);
            }
            i++;
            if(i<arr.length && !arr[i].equals("null")) {
                temp.right = new TreeNode(Integer.parseInt(arr[i]));
                deque.addLast(temp.right);
            }
            i++;
        }
        return head;
    }

    public static void printTree(TreeNode head) {
        if(head != null) {
            System.out.print(head.val + "  ");
            printTree(head.left);
            printTree(head.right);
        } else {
            System.out.print("null  ");
        }
    }

}
