package com.sochanski.pick;

public enum PickTaskStatus {
    READY(1), PICKED(2), COMPLETED(3), SHIPPED(4);

    private final int order;

    PickTaskStatus(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public static PickTaskStatus fromOrder(int order) {
        switch (order) {
            case 1:
                return READY;
            case 2:
                return PICKED;
            case 3:
                return COMPLETED;
            case 4:
                return SHIPPED;
            default:
                throw new IllegalStateException();
        }
    }
}
