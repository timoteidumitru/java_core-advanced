package com.javaPlayground.collections.customArrayList;

import java.util.ArrayList;

public class CustomArrayList extends ArrayList<Object> {

    @Override
    public boolean add(Object obj){
        if (this.contains(obj)){
            return true;
        } else {
            return super.add(obj);
        }
    }

    public static void main(String[] args) {
        CustomArrayList customArrayList = new CustomArrayList();
        customArrayList.add(1);
        customArrayList.add(2);
        customArrayList.add(1);
        customArrayList.add(2);
        customArrayList.add(3);

        System.out.println(customArrayList);

    }
}
