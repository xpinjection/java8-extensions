package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import io.vavr.collection.List;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public class AddElementsToStream {
    @Ugly
    void printHtmlStructure(String... tokens) {
        concat(
                Stream.of("<html>"),
                concat(Arrays.stream(tokens).filter(this::isHtmlTag),
                Stream.of("</html>"))
        ).forEach(System.out::println);
    }

    @Good
    void printHtmlStructureWithJool(String... tokens) {
        Seq.of(tokens)
                .filter(this::isHtmlTag)
                .prepend("<html>")
                .append("</html>")
                .forEach(System.out::println);
    }

    @Good
    void printHtmlStructureWithVavr(String... tokens) {
        List.of(tokens)
                .filter(this::isHtmlTag)
                .prepend("<html>")
                .append("</html>")
                .forEach(System.out::println);
    }

    @Good
    void printHtmlStructureWithStreamEx(String... tokens) {
        StreamEx.of(tokens)
                .filter(this::isHtmlTag)
                .prepend("<html>")
                .append("</html>")
                .forEach(System.out::println);
    }

    private boolean isHtmlTag(String token) {
        return token.startsWith("<") && token.endsWith(">");
    }
}
