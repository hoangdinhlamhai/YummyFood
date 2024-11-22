package com.example.yummyfood.Fragment.Voucher;

public class Voucher {
    private String title;
    private String condition;


    public Voucher(String title, String condition) {
        this.title = title;
        this.condition = condition;
    }


    public String getTitle() {
        return title;
    }

    public String getCondition() {
        return condition;
    }
}



