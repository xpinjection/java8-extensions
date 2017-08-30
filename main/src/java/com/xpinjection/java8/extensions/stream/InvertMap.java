package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import com.xpinjection.java8.extensions.Permission;
import com.xpinjection.java8.extensions.Person;
import io.vavr.Tuple;
import one.util.streamex.EntryStream;
import org.jooq.lambda.Seq;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;

import static java.util.stream.Collectors.*;
import static org.jooq.lambda.tuple.Tuple.tuple;

public class InvertMap {
    @Ugly
    Map<Person, Set<Permission>> groupByPerson(Map<Permission, List<Person>> permissions) {
        return permissions.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(person -> new AbstractMap.SimpleEntry<>(person, entry.getKey())))
                .collect(groupingBy(Map.Entry::getKey,
                        mapping(Map.Entry::getValue, toSet())));
    }

    @Good
    Map<Person, Set<Permission>> groupByPersonWithStreamEx(Map<Permission, List<Person>> permissions) {
        return EntryStream.of(permissions)
                .flatMapValues(List::stream)
                .invert()
                .groupingTo(HashSet::new);
    }

    @Good
    Map<Person, Set<Permission>> groupByPersonWithJool(Map<Permission, List<Person>> permissions) {
        return Seq.seq(permissions)
                .flatMap(t -> t.v2.stream()
                        .map(person -> tuple(person, t.v1)))
                .groupBy(Tuple2::v1, mapping(Tuple2::v2, toSet()));
    }

    @Good
    Map<Person, Set<Permission>> groupByPersonWithVavr(Map<Permission, List<Person>> permissions) {
        return io.vavr.collection.HashMap.ofAll(permissions)
                .flatMap(t -> io.vavr.collection.List.ofAll(t._2)
                        .map(person -> Tuple.of(person, t._1)))
                .collect(groupingBy(io.vavr.Tuple2::_1,
                        mapping(io.vavr.Tuple2::_2, toSet())));
    }
}
