package ru.maksimka.jb.converters;

@FunctionalInterface
public interface Converter<Entity, ObjectDTO> {
    ObjectDTO convertToDto(Entity entity);
}
