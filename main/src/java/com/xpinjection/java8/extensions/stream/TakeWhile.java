package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import io.vavr.collection.List;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TakeWhile {
    @Ugly
    void takeAllUntilStopWithOldJava(String... words) {
        for (String word : words) {
            if ("STOP".equals(word)) {
                return;
            }
            System.out.println(word);
        }
    }

    private <T> Spliterator<T> takeWhile(Spliterator<T> spliterator, Predicate<? super T> predicate) {
      return new Spliterators.AbstractSpliterator<T>(spliterator.estimateSize(), 0) {
        boolean stillGoing = true;
        @Override public boolean tryAdvance(Consumer<? super T> consumer) {
          if (stillGoing) {
            boolean hadNext = spliterator.tryAdvance(elem -> {
              if (predicate.test(elem)) {
                consumer.accept(elem);
              } else {
                stillGoing = false;
              }
            });
            return hadNext && stillGoing;
          }
          return false;
        }
      };
    }

    private <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
       return StreamSupport.stream(takeWhile(stream.spliterator(), predicate), false);
    }

    @Ugly
    void takeAllUntilStop(String... words) {
        takeWhile(Arrays.stream(words), word -> !"STOP".equals(word))
                .forEach(System.out::println);
    }

    @Good
    void takeAllUntilStopWithJool(String... words) {
        Seq.of(words)
                .limitUntil("STOP"::equals)
                .forEach(System.out::println);
    }

    @Good
    void takeAllUntilStopWithVavr(String... words) {
        List.of(words)
                .takeUntil("STOP"::equals)
                .forEach(System.out::println);
    }

    @Good
    void takeAllUntilStopWithStreamEx(String... words) {
        StreamEx.of(words)
                .takeWhile(word -> !"STOP".equals(word))
                .forEach(System.out::println);
    }
}
