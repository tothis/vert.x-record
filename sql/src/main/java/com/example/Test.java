package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        Model model = new Model();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            model.setFlag(true);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(model.getFlag() + Thread.currentThread().getName());
        });
        executorService.execute(() -> {
            model.setFlag(false);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(model.getFlag() + Thread.currentThread().getName());
        });
    }
}

class Model {
    private Boolean flag;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}