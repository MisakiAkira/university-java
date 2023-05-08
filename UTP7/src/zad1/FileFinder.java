package zad1;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileFinder<A> implements FileVisitor <A> {

    @Override
    public FileVisitResult preVisitDirectory(A dir, BasicFileAttributes attrs) {
        return this.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(A file, BasicFileAttributes attrs) throws IOException {
        return this.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(A file, IOException exc) {
        return this.visitFileFailed(file, exc);
    }

    @Override
    public FileVisitResult postVisitDirectory(A dir, IOException exc) {
        return this.postVisitDirectory(dir, exc);
    }
}
