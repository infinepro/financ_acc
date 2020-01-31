package ru.maksimka.jb.converters;

@FunctionalInterface
public interface Converter<D, T> {
    T convert(D object);
}
