import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.print.attribute.HashAttributeSet;

class Node {
    String name;
    boolean isBig;
    List<String> targets;

    Node(String tempName) {
        name = tempName;
        if (Character.isUpperCase(name.charAt(0))) {
            isBig = true;
        }
        targets = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name + " " + targets + " " + isBig;
    }
}

public class Solution {

    public static void printMap(HashMap<String, Node> map) {
        for (String key : map.keySet()) {
            System.out.println(key + " " + map.get(key));
        }
    }

    public static int traverseNode(HashMap<String, Node> map,
            Node current,
            HashMap<String, Integer> visited) {
        int count = 0;
        if (!visited.containsKey(current.name)) {
            visited.put(current.name, 0);
        }
        visited.put(current.name, visited.get(current.name) + 1);
        for (int i = 0; i < current.targets.size(); i++) {
            if (current.targets.get(i).equals("end")) {
                count++;
            } else {
                if (map.get(current.targets.get(i)).isBig ||
                        !visited.containsKey(current.targets.get(i))) {
                    count += traverseNode(map,
                            map.get(current.targets.get(i)),
                            new HashMap<String, Integer>(visited));
                }
            }
        }
        return count;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                HashMap<String, Node> map = new HashMap<>();
                for (int i = 0; i < strings.size(); i++) {
                    String[] startEnd = strings.get(i).split("-");
                    if (!map.containsKey(startEnd[0])) {
                        map.put(startEnd[0], new Node(startEnd[0]));
                    }
                    if (!map.containsKey(startEnd[1])) {
                        map.put(startEnd[1], new Node(startEnd[1]));
                    }
                    map.get(startEnd[0]).targets.add(startEnd[1]);
                    map.get(startEnd[1]).targets.add(startEnd[0]);
                }
                HashMap<String, Integer> visited = new HashMap<>();
                System.out.println(map);
                int count = traverseNode(map, map.get("start"), visited);
                System.out.println("input " + (fi + 1) + " answer is: " + count);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + count);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
