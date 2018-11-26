package com.inkmate.app.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class InkmateUtil {
    public static String createTreeFromArray(int [] arr) {
        int length = arr.length;
        double height = (int) Math.floor(Math.log(arr.length) / Math.log(2) + 1);
        int range = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(new TreeNode(arr[0]));
        Queue<Integer> elementQueue = new LinkedList<>();
        for (int i = 1; i < arr.length; i++) {
            elementQueue.add(arr[i]);
        }
        TreeNode root = queue.peek();
        while (!queue.isEmpty()) {
            TreeNode node = queue.peek();
            if(!elementQueue.isEmpty()){
                if (elementQueue.peek() != -1) {
                    int value = elementQueue.peek();
                    node.left = new TreeNode(value);
                    System.out.println("left node added  1  = "+value);
                    queue.add(node.left);
                }
                elementQueue.remove();

                if (elementQueue.peek() != -1) {
                    int value = elementQueue.peek();
                    node.right = new TreeNode(value);
                    System.out.println("right node added  1  = "+value);
                    queue.add(node.right);
                }
                elementQueue.remove();
            }
            TreeNode removedNode  = queue.remove();
            System.out.println("removedNode   = "+removedNode);
        }

        System.out.println("traversin 123 g tree  ==================== " + root);
//        traverse(root);

        String json = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString("");
            System.out.println("json =================== " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

//    private static void traverse(TreeNode node1) {
//        Queue<TreeNode> queue = new LinkedList<TreeNode>();
//        System.out.println("tree traversal ============== "+node1.getVal());
//        queue.add(node1);
//        while(!queue.isEmpty()){
//            System.out.println(queue.peek().getVal()+" =========  ");
//            TreeNode node2 = queue.peek();
//
//            if(node2.left != null){
//                queue.add(node2.left);
//            }
//
//            if(node2.right != null){
//                queue.add(node2.right);
//            }
//            queue.remove();
//        }
//    }
}

@Data
class TreeNode {
    private  int val;

    TreeNode left;

    TreeNode right;

    TreeNode(){
    }

    TreeNode(int val){
        this.val = val;
    }
}

