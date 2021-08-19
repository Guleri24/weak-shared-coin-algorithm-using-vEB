package com.guleri24;

public class Register {
    private final int count;
    private final int var;
    private int total;

    public Register(int count, int var, int total) {
        this.count = count;
        this.var = var;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MaxRegister: count " + this.count + ", var " + this.var + ", total " + this.total;
    }
}
