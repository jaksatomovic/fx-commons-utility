/*
 * Boomega
 * Copyright (C)  2021  Daniel Gyoerffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.jaksatomovic.fx.commons.utility;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility class for making some reflection tasks easier.
 *
 * @author Jakša Tomović
 * since 1.0.0
 */
public final class ReflectionUtility
{

    private ReflectionUtility()
    {
    }

    /**
     * Returns the value of the static field-reference.
     *
     * <p>
     * If an exception occurs it throws a {@link RuntimeException} instead of
     * a checked exception.
     *
     * @param field the reflected {@link Field} object
     * @return the read object
     */
    public static Object getDeclaredStaticValue(Field field)
    {
        try
        {
            return field.get(null);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an object through reflection: with a class-reference and the
     * constructor parameters.
     * <p>
     * Example:
     * <pre>{@code
     * class X {
     *     X(String arg0, int arg1) {
     *         ...
     *     }
     * }
     *
     * X instance = ReflectionUtils.constructObject(X.class, "First argument", 1024);
     *
     * }</pre>
     *
     * @param classRef the class-reference
     * @param args     the constructor arguments; empty if you want to call the default constructor
     * @param <O>      the type of the object that should constructed
     * @return the object instance
     * @throws ReflectiveOperationException if some reflection-exception occurs
     */
    public static <O> O constructObject(@NotNull Class<? extends O> classRef, Object... args)
        throws ReflectiveOperationException
    {
        Objects.requireNonNull(classRef);
        Class<?>[] constructorParamTypes = Stream.of(args)
                                                 .map(Object::getClass)
                                                 .toArray(Class[]::new);

        Constructor<? extends O> constructor = classRef.getDeclaredConstructor(constructorParamTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }

    public static <O> O constructObject(@NotNull Class<? extends O> classRef)
        throws ReflectiveOperationException
    {
        Objects.requireNonNull(classRef);
        Constructor<? extends O> constructor = classRef.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    public static <O> O tryConstructObject(@NotNull Class<? extends O> classRef)
    {
        try
        {
            return constructObject(classRef);
        }
        catch (Throwable ignored)
        {
            return null;
        }
    }

    @SuppressWarnings ({"unchecked", "UnusedReturnValue"})
    public static <T> Class<T> forName(@NotNull Class<T> classRef)
        throws ClassNotFoundException
    {
        return (Class<T>)Class.forName(classRef.getName());
    }
}
