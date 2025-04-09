/**
 * Jamie Casino
 * jamiec9981@gmail.com
 * jamiec9981
 */

import java.io.*;
import java.util.*;
import java.util.stream.*;

interface IArrayList {
    public int getSize();
    public int getCapacity();
    public int get(int index);
    public void add(int value);
    public void addAtIndex(int value, int index);
    public void remove(int value);
    public void removeAtIndex(int index);
    public void clear();
}

class ArrayList implements IArrayList {
    private int[] arr;
    private int capacity;
    private int size;

    public ArrayList(int capacity) {
        this.capacity = capacity;
        this.arr = new int[capacity];
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("INDEX_OUT_OF_BOUNDS");
            return -1; // Placeholder for invalid access
        }
        return arr[index];
    }

    public void add(int value) {
        if (size == capacity) {
            doubleCapacity();
        }
        arr[size++] = value;
    }

    public void addAtIndex(int value, int index) {
        if (index < 0 || index > size) {
            System.out.println("INDEX_OUT_OF_BOUNDS");
            return;
        }
        if (size == capacity) {
            doubleCapacity();
        }
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = value;
        size++;
    }

    public void remove(int value) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            removeAtIndex(index);
        }
    }

    public void removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            System.out.println("INDEX_OUT_OF_BOUNDS");
            return;
        }
        for (int i = index; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[--size] = 0;
        if (size <= capacity / 4 && capacity > 1) {
            halveCapacity();
        }
    }

    public void clear() {
        size = 0;
        if (capacity > 5) {
            capacity = 5;
            arr = new int[capacity];
        } else {
            Arrays.fill(arr, 0);
        }
    }

    private void doubleCapacity() {
        capacity *= 2;
        arr = Arrays.copyOf(arr, capacity);
    }

    private void halveCapacity() {
        capacity = Math.max(1, capacity / 2);
        arr = Arrays.copyOf(arr, capacity);
    }

    public void print() {
        StringBuilder elements = new StringBuilder("[");
        for (int i = 0; i < capacity; i++) {
            elements.append(arr[i]);
            if (i < capacity - 1) {
                elements.append(", ");
            }
        }
        elements.append("]");
        String summary = String.format("{ capacity: %d, size: %d, elements: %s }", capacity, size, elements);
        System.out.println(summary);
    }
}

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int capacity = Integer.parseInt(bufferedReader.readLine().replaceAll("\\s+$", "").split("=")[1].trim());
        int operationCount = Integer.parseInt(bufferedReader.readLine().replaceAll("\\s+$", "").split("=")[1].trim());
        bufferedReader.readLine();

        ArrayList arrList = new ArrayList(capacity);
        IntStream.range(0, operationCount).forEach(opCountItr -> {
            try {
                List<String> theInput = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(","))
                        .collect(Collectors.toList());
                String action = theInput.get(0);
                String arg1 = theInput.size() > 1 ? theInput.get(1).trim() : null;
                String arg2 = theInput.size() > 2 ? theInput.get(2).trim() : null;
                ProcessInputs(arrList, action, arg1, arg2);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
        bufferedReader.close();
    }

    private static void ProcessInputs(ArrayList arrList, String action, String arg1, String arg2) {
        int result;
        int value;
        int index;
        switch (action) {
            case "getSize":
                result = arrList.getSize();
                System.out.println(result);
                break;
            case "getCapacity":
                result = arrList.getCapacity();
                System.out.println(result);
                break;
            case "get":
                index = Integer.parseInt(arg1);
                result = arrList.get(index);
                if (result != -1) System.out.println(result);
                break;
            case "add":
                value = Integer.parseInt(arg1);
                arrList.add(value);
                break;
            case "addAtIndex":
                value = Integer.parseInt(arg1);
                index = Integer.parseInt(arg2);
                arrList.addAtIndex(value, index);
                break;
            case "remove":
                value = Integer.parseInt(arg1);
                arrList.remove(value);
                break;
            case "removeAtIndex":
                index = Integer.parseInt(arg1);
                arrList.removeAtIndex(index);
                break;
            case "clear":
                arrList.clear();
                break;
            case "print":
                arrList.print();
                break;
        }
    }
}