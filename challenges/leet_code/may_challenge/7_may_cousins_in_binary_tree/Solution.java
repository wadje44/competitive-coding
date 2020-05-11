/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
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
    
    
    public boolean isCousins(TreeNode root, int x, int y) {
        if(root.val == x || root.val==y) return false;
        int[] leftParent= {root.val, 0};
        int[] rightParent= {root.val, 0};
        depthOfelement(root, x, leftParent, 1);
        depthOfelement(root, y, rightParent, 1);
        return (leftParent[0] != rightParent[0]) && (leftParent[1] == rightParent[1]);
    }
}
