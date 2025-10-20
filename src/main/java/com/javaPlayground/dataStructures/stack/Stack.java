package com.javaPlayground.dataStructures.stack;

public class Stack {
    int[] stack = new int[5];
    int top = 0;

    public void push(int data){
        if (top <= stack.length - 1){
            stack[top] = data;
            top++;
        } else {
            System.out.println("Element " + data + " cannot pe pushed to the stack, exceeded maximum stack size.");
        }
    }

    public void pop(){
        int data;
        if(top <= 0){
            System.out.println("Stack is empty.");
        } else {
            top--;
            data = stack[top];
            stack[top] = 0;
            System.out.println("Element " + data + " have been taken out from stack.");
        }
    }

    public void peek(){
        int data;
        data = stack[top - 1];
        System.out.println("Element " + data + " have been looked at.");
    }

    public void size(){
        System.out.println("Stack size is: " + top);
    }

    public void isEmpty(){
        if(top <= 0) {
            System.out.println("Stack is empty.");
        }
    }

    public void show(){
        for(int i = stack.length - 1; i >= 0; i--){
            if(stack[i] != 0){
                System.out.println(stack[i]);
            }
        }
    }

}