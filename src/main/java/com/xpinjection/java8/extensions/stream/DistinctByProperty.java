package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import com.xpinjection.java8.extensions.Person;
import one.util.streamex.StreamEx;
import org.jooq.lambda.Seq;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class DistinctByProperty {
    @Ugly
    List<Person> filterByName(List<Person> people) {
        Set<String> names = new HashSet<>();
        return people.stream()
                .filter(person -> names.add(person.getName()))
                .collect(toList());
    }

    @Ugly
    List<Person> filterByNameWithVavr(List<Person> people) {
        return io.vavr.collection.List.ofAll(people)
                .distinctBy(Person::getName)
                .toJavaList();
    }

    @Good
    List<Person> filterByNameWithStreamEx(List<Person> people) {
        return StreamEx.of(people)
                .distinct(Person::getName)
                .toList();
    }

    @Good
    List<Person> filterByNameWithJool(List<Person> people) {
        return Seq.seq(people)
                .distinct(Person::getName)
                .toList();
    }
}
