package com.javaPlayground.dataStructures.stack;

public class Runner {
    public static void main(String[] args) {
        DynamicStack stack = new DynamicStack();
        for (int i=1;i<=9;i++){
            stack.push(i);
        }
        stack.peek();
        stack.pop();
        stack.show();

    }
}
