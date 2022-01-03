import java.util.*;
import java.io.*;
import java.lang.Runtime.Version;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Packet {
    int version;
    int type;
    boolean isOperator;
    List<Packet> subPackets = new ArrayList<>();
    String literalValue;

    public String toString() {
        return ("version: " + version +
                " type: " + type +
                " isOperator: " + isOperator +
                " literalValue: " + literalValue);
    }
}

class LiteralRetrun {
    int endIndex;
    String value;
}

class PacketReturn {
    int endIndex;
    Packet value;
}

public class Solution {

    public static int count = 0;

    public static int binaryToDec(String binary) {
        // System.out.println(binary);
        int temp = 0;
        try {
            temp = Integer.parseInt(binary, 2);
        } catch (Exception e) {
            return 999999;
        }
        return temp;
    }

    public static LiteralRetrun getLiteralValue(char[] inputInHexChar, int startIndex) {
        LiteralRetrun l = new LiteralRetrun();
        String binaryValue = "";
        boolean isLast = inputInHexChar[startIndex] == '0' ? true : false;
        int i = 0;
        while (!isLast) {

            binaryValue += inputInHexChar[startIndex + (++i)] + "" +
                    inputInHexChar[startIndex + (++i)] + "" +
                    inputInHexChar[startIndex + (++i)] + "" +
                    inputInHexChar[startIndex + (++i)];
            isLast = inputInHexChar[startIndex + (++i)] == '0' ? true : false;
        }
        binaryValue += inputInHexChar[startIndex + (++i)] + "" +
                inputInHexChar[startIndex + (++i)] + "" +
                inputInHexChar[startIndex + (++i)] + "" +
                inputInHexChar[startIndex + (++i)];
        l.endIndex = startIndex + i;
        // System.out.println(binaryValue);
        // binaryToDec(binaryValue);
        l.value = binaryValue; // binaryToDec(binaryValue);
        return l;

    }

    public static PacketReturn processPacket(char[] inputInHexChar, int startIndex) {
        // for (int i = 0; i < inputInHexChar.length; i++) {
        // System.out.print(inputInHexChar[i]);
        // }
        // System.out.println();

        Packet p = new Packet();
        int endIndex = -1;
        p.version = binaryToDec(
                "" + inputInHexChar[startIndex + 0] + inputInHexChar[startIndex + 1] + inputInHexChar[startIndex + 2]);
        count += p.version;
        System.out.println("p.version: " + p.version);
        p.type = binaryToDec(
                "" + inputInHexChar[startIndex + 3] + inputInHexChar[startIndex + 4] + inputInHexChar[startIndex + 5]);
        System.out.println("p.type: " + p.type);
        if (p.type == 4) {
            p.isOperator = false;
            LiteralRetrun lr = getLiteralValue(inputInHexChar, startIndex + 6);
            System.out.println("lr.value: " + lr.value);
            p.literalValue = lr.value;
            endIndex = lr.endIndex;
        } else {
            p.isOperator = true;
            if (inputInHexChar[startIndex + 6] == '0') {
                String totalBitsBinary = "";
                for (int i = startIndex + 7; i <= (startIndex + 21); i++) {
                    totalBitsBinary += inputInHexChar[i];
                }
                int totalBits = binaryToDec(totalBitsBinary);
                // System.out.println("totalBits: " + totalBits);
                int nextStartIndex = startIndex + 21 + 1;

                while (nextStartIndex < (totalBits + startIndex + 7 + 15)) {
                    PacketReturn pr = new PacketReturn();
                    pr = processPacket(inputInHexChar, nextStartIndex);
                    p.subPackets.add(pr.value);
                    nextStartIndex = pr.endIndex + 1;
                }
                endIndex = nextStartIndex - 1;
            } else {
                String totalPacketsBinary = "";
                for (int i = (startIndex + 7); i <= (startIndex + 17); i++) {
                    totalPacketsBinary += inputInHexChar[i];
                }
                // System.out.println("totalPacketsBinary: " + totalPacketsBinary);
                int totalPackets = binaryToDec(totalPacketsBinary);
                // System.out.println("totalPackets: " + totalPackets);
                int nextStartIndex = startIndex + 17 + 1;
                while (totalPackets > 0) {
                    PacketReturn pr = new PacketReturn();
                    pr = processPacket(inputInHexChar, nextStartIndex);
                    p.subPackets.add(pr.value);
                    nextStartIndex = pr.endIndex + 1;
                    totalPackets--;
                }
                endIndex = nextStartIndex - 1;
            }
        }
        PacketReturn pr = new PacketReturn();
        pr.value = p;
        pr.endIndex = endIndex;
        return pr;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();

            HashMap<Character, String> charToHex = new HashMap<>();
            charToHex.put('0', "0000");
            charToHex.put('1', "0001");
            charToHex.put('2', "0010");
            charToHex.put('3', "0011");
            charToHex.put('4', "0100");
            charToHex.put('5', "0101");
            charToHex.put('6', "0110");
            charToHex.put('7', "0111");
            charToHex.put('8', "1000");
            charToHex.put('9', "1001");
            charToHex.put('A', "1010");
            charToHex.put('B', "1011");
            charToHex.put('C', "1100");
            charToHex.put('D', "1101");
            charToHex.put('E', "1110");
            charToHex.put('F', "1111");
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                char[] inputInChar = strings.get(0).toCharArray();
                String inputInHex = "";
                for (int i = 0; i < inputInChar.length; i++) {
                    inputInHex = inputInHex + charToHex.get(inputInChar[i]);
                }
                System.out.println("inputInHex : " + inputInHex);

                char[] inputInHexChar = inputInHex.toCharArray();
                PacketReturn root = processPacket(inputInHexChar, 0);
                // System.out.println("total length : " + "(root.endIndex + 1)");
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
