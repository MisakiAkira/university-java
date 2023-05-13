package zad1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Futil {

    private static final Charset cp1250 = Charset.forName("Cp1250"),
            utf8 = StandardCharsets.UTF_8;

    static void processDir(String dirName, String resultFile) {
        try (FileOutputStream fos = new FileOutputStream(resultFile);
             FileChannel fco = fos.getChannel()) {

            Path start = Paths.get(dirName);
            List<Path> files = new ArrayList<>();
            Files.walkFileTree(start, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isRegularFile()) {
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });

            for (Path file : files) {
                try (FileChannel fci = FileChannel.open(file)) {
                    ByteBuffer bb = ByteBuffer.allocate((int) fci.size());
                    fci.read(bb);
                    bb.flip();
                    CharBuffer cb = cp1250.decode(bb);
                    fco.write(utf8.encode(cb));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
