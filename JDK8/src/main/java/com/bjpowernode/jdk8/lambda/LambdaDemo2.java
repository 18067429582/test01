package com.bjpowernode.jdk8.lambda;

public class LambdaDemo2 {

    public static void main(String[] args) {
        show(new ServiceUser() {
            @Override
            public void show() {
                System.out.println("执行");
            }
        });

        //lambda表达式
        show(() -> {
            System.out.println("lambda表达式执行");
        });
    }
    public static void show(ServiceUser service){
        service.show();
    }
}
