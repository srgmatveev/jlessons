package ru.hw03.custom;

import java.lang.reflect.Array;
import java.util.*;

public class MyOwnArrayList<T> implements List<T>, RandomAccess, Cloneable {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 1;

    public MyOwnArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity < 0) {

            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public MyOwnArrayList() {
        this.elementData = new Object[0];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && elementData[0] != null;
            }

            @Override
            public T next() {
                return (T) elementData[currentIndex++];
            }


            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        try {
            if (!checkRange(size)) return false;
        } catch (Exception e) {
            return false;
        }
        if (size == 0) elementData = new Object[1];
        if (needResize()) this.resize(size + 1);
        elementData[size++] = t;


        return true;

    }

    private boolean needResize() {
        if (size >= elementData.length) return true;
        return false;
    }

    private void resize(int newSize) {
        if (newSize >= Integer.MAX_VALUE - 1) throw new ArrayStoreException();
        elementData = Arrays.copyOf(elementData, newSize);
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        try {
            if (!checkRange(size + c.size())) return false;
        } catch (Exception e) {
            return false;
        }

        int cSize = c.size();
        if (cSize == 0)
            return false;

        for (T item : c) add(item);

        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) elementData, 0, size, c);
    }

    public MyOwnArrayList clone(){
        try {
            MyOwnArrayList<?> v = (MyOwnArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.size = size;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    public void clear() {

    }

    public T get(int index) {
        return (T) elementData[index];
    }

    public T set(int index, T element) {
        elementData[index] = element;
        return element;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator() {
        return null;
    }

    public ListIterator<T> listIterator(int index) {
        return null;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private boolean checkRange(int value) {
        if (value > MAX_ARRAY_SIZE - 1) return false;
        return true;
    }
}
