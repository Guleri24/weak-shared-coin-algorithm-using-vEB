package com.guleri24;

public class MaxRegister {
    private final Long count;
    private final Long var;
    private final Long total;

    private MaxRegister(MaxRegisterBuilder builder) {
        this.count = builder.count;
        this.var = builder.var;
        this.total = builder.total;
    }

    public Long getCount() {
        return count;
    }

    public Long getVar() {
        return var;
    }

    public Long getTotal() {
        return total;
    }

    public static class MaxRegisterBuilder {
        private Long count;
        private Long var;
        private Long total;

        public MaxRegisterBuilder count(Long count) {
            this.count = count;
            return this;
        }

        public MaxRegisterBuilder var(Long var) {
            this.var = var;
            return this;
        }

        public MaxRegisterBuilder total(Long total) {
            this.total = total;
            return this;
        }

    }
}
