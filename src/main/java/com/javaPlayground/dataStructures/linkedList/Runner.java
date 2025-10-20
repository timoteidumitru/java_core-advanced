package com.javaPlayground.dataStructures.linkedList;

public class Runner {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.insert(8);
        list.insert(12);
        list.insert(77);
        list.insertAtStart(101);
        list.insertAtLocation(2, 89);
        list.insertAtLocation(0, 50);
        list.deleteAt(1);

        list.showData();
    }

}
