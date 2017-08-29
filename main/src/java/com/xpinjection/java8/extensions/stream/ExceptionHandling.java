package com.xpinjection.java8.extensions.stream;

import com.xpinjection.java8.extensions.Annotations.Good;
import com.xpinjection.java8.extensions.Annotations.Ugly;
import com.xpinjection.java8.extensions.Permission;
import io.vavr.control.Try;

import java.util.EnumSet;
import java.util.Set;

public class ExceptionHandling {
    @Ugly
    Set<Permission> getUserPermissions(long userId) {
        try {
            return retrievePermissions(userId);
        } catch (RuntimeException e) {
            System.out.println(e);
            return EnumSet.of(Permission.READ);
        }
    }

    @Good
    Set<Permission> getUserPermissionsWithVavr(long userId) {
        return Try.ofSupplier(() -> retrievePermissions(userId))
                .onFailure(System.out::println)
                .getOrElse(EnumSet.of(Permission.READ));
    }

    private Set<Permission> retrievePermissions(long userId) {
        //connect to some external service
        return EnumSet.noneOf(Permission.class);
    }
}
