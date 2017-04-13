package io.github.yizhiru.thulac4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TestA {
    public static void main(String args[]) throws Exception {
        String file = "/Users/chenke/Downloads/icwb2-data/testing/pku_test.utf8";
        BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
        FileOutputStream fos = new FileOutputStream(
                "/Users/chenke/Downloads/icwb2-data/scripts/pku_test_seg.utf8");

        SegOnly seg = new SegOnly(
                "/Users/chenke/Documents/javawork/thulac4j/src/test/java/seg_only.bin");
        String line = null;
        while ((line = fileReader.readLine()) != null) {
            String sentence = line;
            List<String> list = seg.segment(sentence);
            for (String str : list) {
                fos.write(str.getBytes());
                fos.write("  ".getBytes());
            }
            fos.write("\r\n".getBytes());
        }
    }
}
