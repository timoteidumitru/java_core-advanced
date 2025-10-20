package com.javaPlayground.dataStructures.stack;

import java.util.Arrays;

public class DynamicStack {
    int capacity = 2;
    int[] stack = new int[capacity];
    int top = 0;

    public void shrink(){
        int stackSize = size();
        if(stackSize<=(capacity/2)/2){
            capacity = capacity/2;
        }
        int[] newStack = new int[capacity];
        System.arraycopy(stack, 0, newStack, 0, stackSize);
        stack = newStack;
    }

    public void expand(){
        int stackSize = size();
        int[] newStack = new int[capacity*2];
        System.arraycopy(stack, 0, newStack, 0, stackSize);
        stack = newStack;
        capacity *= 2;
    }

    public void push(int data){
        if(size() == capacity){
            expand();
        }
        stack[top] = data;
        top++;
    }

    public void pop(){
        int data;
        if(isEmpty()){
            System.out.println("Stack is empty.");
        } else {
            top--;
            data = stack[top];
            stack[top] = 0;
            System.out.println("Element " + data + " have been taken out from the stack.");
            shrink();
        }
    }

    public void peek(){
        int data;
        data = stack[top - 1];
        System.out.println("Element " + data + " have been looked at.");
    }

    public int size(){
        return top;
    }

    public boolean isEmpty(){
        return top <= 0;
    }

    public void show(){
        for(int i = stack.length - 1; i >= 0; i--){
            if(stack[i] != 0){
                System.out.println(stack[i]);
            }

        }
        System.out.println("All stack elements: " + Arrays.toString(stack));
    }

}
