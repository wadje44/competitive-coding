import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            HashMap<Character, Integer> map = new HashMap<>();
            map.put(')', 3);
            map.put(']', 57);
            map.put('}', 1197);
            map.put('>', 25137);
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int result = 0;
                for (int i = 0; i < strings.size(); i++) {
                    char[] temp = strings.get(i).toCharArray();
                    Stack<Character> stack = new Stack<Character>();

                    for (int j = 0; j < temp.length; j++) {
                        if(temp[j] == '(' || temp[j] == '[' || temp[j] == '{' || temp[j] == '<') {
                            stack.push(temp[j]);
                        } else {
                            char t = ' ';
                            switch(temp[j]) {
                                case ')':
                                    t = '(';
                                    break;
                                case ']':
                                    t = '[';
                                    break;
                                case '}':
                                    t = '{';
                                    break;
                                case '>':
                                    t = '<';
                                    break;
                            }
                            if(stack.pop() != t) {
                                System.out.println(temp[j]);
                                result += map.get(temp[j]);
                                break;
                            }
                        }
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + result);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + result);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
