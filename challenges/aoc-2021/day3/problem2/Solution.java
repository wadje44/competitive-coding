import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());

                int stringSize = strings.get(0).length();

                Boolean oflag[] = new Boolean[strings.size()];
                Boolean cflag[] = new Boolean[strings.size()];
                int ozerocount[] = new int[stringSize];
                int oonecount[] = new int[stringSize];
                int czerocount[] = new int[stringSize];
                int conecount[] = new int[stringSize];
                String oresult = "0", cresult = "0";
                for (int j = 0; j < strings.size(); j++) {
                    cflag[j] = true;
                    oflag[j] = true;
                }
                int cflagcount = strings.size();
                int oflagcount = strings.size();
                for (int j = 0; j < stringSize; j++) {
                    for (int i = 0; i < strings.size(); i++) {
                        if (oflag[i] == true) {
                            if (strings.get(i).charAt(j) == '0') {
                                ozerocount[j]++;
                            } else {
                                oonecount[j]++;
                            }
                        }
                    }

                    for (int i = 0; i < strings.size(); i++) {
                        if (oflag[i] == true) {
                            if (ozerocount[j] > oonecount[j]) {
                                if (strings.get(i).charAt(j) == '0') {
                                    oflag[i] = true;
                                } else {
                                    oflag[i] = false;
                                    oflagcount--;
                                }
                            } else {
                                if (strings.get(i).charAt(j) == '1') {
                                    oflag[i] = true;
                                } else {
                                    oflag[i] = false;
                                    oflagcount--;
                                }
                            }
                        }
                    }
                    if (oflagcount == 1) {
                        int i;
                        for (i = 0; i < strings.size(); i++) {
                            if (oflag[i]) {
                                oresult = strings.get(i);
                                break;
                            }
                        }
                        if (i != strings.size()) {
                            break;
                        }
                    }
                }
                for (int j = 0; j < stringSize; j++) {
                    for (int i = 0; i < strings.size(); i++) {
                        if (cflag[i] == true) {
                            if (strings.get(i).charAt(j) == '0') {
                                czerocount[j]++;
                            } else {
                                conecount[j]++;
                            }
                        }
                    }

                    for (int i = 0; i < strings.size(); i++) {
                        if (cflag[i] == true) {
                            if (czerocount[j] > conecount[j]) {
                                if (strings.get(i).charAt(j) == '1') {
                                    cflag[i] = true;
                                } else {
                                    cflag[i] = false;
                                    cflagcount--;
                                }
                            } else {
                                if (strings.get(i).charAt(j) == '0') {
                                    cflag[i] = true;
                                } else {
                                    cflag[i] = false;
                                    cflagcount--;
                                }
                            }
                        }
                    }

                    if (cflagcount == 1) {
                        int i;
                        for (i = 0; i < strings.size(); i++) {
                            if (cflag[i]) {
                                cresult = strings.get(i);
                                break;
                            }
                        }
                        if (i != strings.size()) {
                            break;
                        }
                    }
                }

                int o = Integer.parseInt(oresult, 2);
                int c = Integer.parseInt(cresult, 2);
                System.out.println("input " + (fi + 1) + " answer is: " + (o * c));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + ((o * c)));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
