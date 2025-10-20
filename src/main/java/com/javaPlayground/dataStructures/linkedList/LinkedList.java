package com.javaPlayground.dataStructures.linkedList;

public class LinkedList {
    Node head;

    public void insert(int data){
        Node node = new Node();
        node.data = data;
        node.next = null;

        if(head == null){
          head = node;
        } else {
            Node n = head;
            while(n.next != null){
                n = n.next;
            }
            n.next = node;
        }
    }

    public void showData(){
        Node node = head;
        while (node.next != null){
            System.out.println(node.data);
            node = node.next;
        }
        System.out.println(node.data);
    }

    public void insertAtStart(int data){
        Node node = new Node();
        node.data = data;
        node.next = head;

        head = node;
        System.out.println("Element " + head.data + " has been set as the new head of the list");
    }

    public void insertAtLocation(int index, int data){
        if (index == 0){
            insertAtStart(data);
            return;
        }

        Node node = new Node();
        node.data = data;

        Node n = head;
        for(int i = 0; i < index - 1; i++){
            // Check if we've reached the end of the list before reaching the desired index
            if (n == null || n.next == null) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
            }
            n = n.next;
        }

        // Insert the new node at the desired location
        node.next = n.next;
        n.next = node;
        System.out.println("Element " + n.data + " added at index " + (index - 1));
    }

    public void deleteAt(int index){
        if(index == 0){
            head = head.next;
        } else {
            Node n = head;
            Node n1 = null;
            for(int i = 0; i < index - 1; i++){
                // Check if we've reached the end of the list before reaching the desired index
                if (n == null || n.next == null) {
                    throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
                }
                n = n.next;
            }
            n1 = n.next;
            n.next = n1.next;
            System.out.println("Element " + n1.data + " deleted at index " + index);
            n1 = null;
        }
    }


}
