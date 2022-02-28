package com.bjpowernode.jdk8.lambda;

public class LambdaDemo1 {
    //代码的结构比较复杂
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("lambda表达式的使用"+Thread.currentThread().getName());
        }).start();
    }
}
