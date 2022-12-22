package com.example.bill.entity;

public class BillInfo {

    private int id;
    private String date;
    private int type;
    private double amount;
    private String remark;

    // 账单类型，0 收入，1 支出
    public static final int BILL_TYPE_INCOME = 0;
    public static final int BILL_TYPE_COST = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
