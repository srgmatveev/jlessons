package ru.hw07.Memento;

public interface IMemento<T> {

    IMemento save(T t);
    T restore();
}
