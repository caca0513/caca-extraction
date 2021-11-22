package caca.extraction.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileHelper {
    public static List<String> readTxt(String folder, String source){
        List<String> lines;
        try {
            if (!source.toLowerCase().endsWith(".txt"))
                source += ".txt";
            lines = Files.readAllLines(Path.of(folder, source));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lines;
    }
}
