package com.xpinjection.java8.extensions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Person {
    private Long id;

    private final String name;
    private final String surname;
    private final int age;
}
