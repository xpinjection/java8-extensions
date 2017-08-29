package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.jooq.lambda.Unchecked;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.xpinjection.java8.extensions.Annotations.Ugly;

public class CheckedExceptions {
    @Ugly
    void printFilesInDir(File dir) {
        Arrays.stream(dir.listFiles()).map(file -> {
            try {
                return file.getCanonicalPath();
            } catch (IOException e) {
                throw new IllegalStateException("Can't access file", e);
            }
        }).forEach(System.out::println);
    }

    @Good
    void printFilesInDirWithJool(File dir) {
        Arrays.stream(dir.listFiles())
                .map(Unchecked.function(File::getCanonicalPath,
                        e -> { throw new IllegalStateException("Can't access file", e); }))
                .forEach(System.out::println);
    }

    @Good
    void printFilesInDirWithVavr(File dir) {
        List.of(dir.listFiles())
                .map(file -> Try.ofCallable(file::getCanonicalPath)
                        .getOrElseThrow(e -> new IllegalStateException("Can't access file", e)))
                .forEach(System.out::println);
    }
}
