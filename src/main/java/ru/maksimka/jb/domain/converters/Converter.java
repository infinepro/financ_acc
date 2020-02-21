package ru.maksimka.jb.domain.converters;

@FunctionalInterface
public interface Converter<D, T> {
    T convert(D object);
}
