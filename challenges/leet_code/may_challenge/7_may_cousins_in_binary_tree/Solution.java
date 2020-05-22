import java.util.Deque;
import java.util.*; 
import java.io.*; 
/*
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
 
class Solution {
    
    public static void depthOfelement(TreeNode node, int x, int ans[], int height) {
        if(node != null) {
            if( node.left != null) {
                if(node.left.val == x) {
                    ans[0] = node.val;
                    ans[1] = height+1;
                    return;
                }
            }
            if( node.right != null) {
                if(node.right.val == x) {
                    ans[0] = node.val;
                    ans[1] = height+1;
                    return;
                }
            }
            depthOfelement( node.left, x, ans, height+1);
            depthOfelement( node.right, x, ans, height+1);
        }
        return ;
    }
    
    
    public static boolean isCousins(TreeNode root, int x, int y) {
        if(root.val == x || root.val==y) return false;
        int[] leftParent= {root.val, 0};
        int[] rightParent= {root.val, 0};
        depthOfelement(root, x, leftParent, 1);
        depthOfelement(root, y, rightParent, 1);
        return (leftParent[0] != rightParent[0]) && (leftParent[1] == rightParent[1]);
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] paramsInString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()),3);
            String[] paramsStringArray = paramsInString[0].substring(1, paramsInString[0].length()-1).split(",");
            TreeNode head = createTree(paramsStringArray);
            printTree(head);
            System.out.println();
            String[] result = new String[] {Boolean.toString(isCousins(head, Integer.parseInt(paramsInString[1]), Integer.parseInt(paramsInString[2])))};
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
