package zad1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Futil {
    public static void processDir(String src, String finFile) {
        try {
            List<String> srcText = new ArrayList<>();
            Files.walkFileTree(Paths.get(src), new FileFinder<Path>() {

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            System.out.println("visitFile: " + file);
                            Charset charset = Charset.forName("cp1250");
                            String textOfFile = readAllLines(file, charset);
                            srcText.add(textOfFile);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }
                    });
            String finText = String.join("\n", srcText);
            File fin = new File(src + "/" + finFile);
            fin.createNewFile();
            FileOutputStream fos = new FileOutputStream(fin);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(finText);
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAllLines(Path file, Charset charset) throws IOException {
        StringBuilder text = new StringBuilder();
        String line = "";
        int counter = 0;
        FileInputStream fis = new FileInputStream(file.toFile());
        InputStreamReader isr = new InputStreamReader(fis, charset);
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
            if (counter == 0)
                text.append(line);
            else
                text.append("\n").append(line);
            counter++;
        }
        return text.toString();
    }
}
