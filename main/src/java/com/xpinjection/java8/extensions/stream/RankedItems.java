package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import io.vavr.collection.List;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.stream.IntStream;

public class RankedItems {
    @Ugly
    void rankByNaturalOrderWithOldJava(String... words) {
        String[] sorted = Arrays.copyOf(words, words.length);
        Arrays.sort(sorted);
        for (int i = 0; i < sorted.length; i++) {
            System.out.println(i + ". " + sorted[i]);
        }
    }

    @Ugly
    void rankByNaturalOrder(String... words) {
        String[] sorted = Arrays.copyOf(words, words.length);
        Arrays.sort(sorted);
        IntStream.range(0, words.length)
                .mapToObj(i -> i + ". " + sorted[i])
                .forEach(System.out::println);
    }

    @Good
    void rankByNaturalOrderWithJool(String... words) {
        Seq.of(words)
                .sorted()
                .zipWithIndex()
                .map(t -> t.v2 + ". " + t.v1)
                .forEach(System.out::println);
    }

    @Good
    void rankByNaturalOrderWithVavr(String... words) {
        List.of(words)
                .sorted()
                .zipWithIndex()
                .map(t -> t._2 + ". " + t._1)
                .forEach(System.out::println);
    }

    @Good
    void rankByNaturalOrderStreamEx(String... words) {
        StreamEx.of(words)
                .sorted()
                .zipWith(IntStream.range(0, words.length).boxed())
                .join(". ")
                .forEach(System.out::println);
    }
}
