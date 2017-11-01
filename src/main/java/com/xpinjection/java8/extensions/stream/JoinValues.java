package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import io.vavr.collection.List;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JoinValues {
    @Ugly
    String joinWithPlainJava(String... words) {
        StringBuilder builder = new StringBuilder();
        for (String s : words) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(s);
        }
        return builder.toString();
    }

    @Good
    String joinWithStreamAPI(String... words) {
        return Arrays.stream(words).collect(Collectors.joining(", "));
    }

    @Good
    String joinWithStreamEx(String... words) {
        return StreamEx.of(words).joining(", ");
    }

    @Good
    String joinWithVavr(String... words) {
        return List.of(words).mkString(", ");
    }

    @Good
    String joinWithJool(String... words) {
        return Seq.of(words).toString(", ");
    }
}
