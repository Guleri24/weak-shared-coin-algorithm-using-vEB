package com.guleri24;

public class MaxRegister {
    private final int count;
    private final int var;
    private final int total;

    private MaxRegister(MaxRegisterBuilder builder) {
        this.count = builder.count;
        this.var = builder.var;
        this.total = builder.total;
    }

    public int getCount() {
        return count;
    }

    public int getVar() {
        return var;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "MaxRegister: count " + this.count + ", var " + this.var + ", total " + this.total;
    }

    public static class MaxRegisterBuilder {
        private int count;
        private int var;
        private int total;

        public MaxRegisterBuilder count(int count) {
            this.count = count;
            return this;
        }

        public MaxRegisterBuilder var(int var) {
            this.var = var;
            return this;
        }

        public MaxRegisterBuilder total(int total) {
            this.total = total;
            return this;
        }

        public MaxRegister build() {
            return new MaxRegister(this);
        }
    }
}
