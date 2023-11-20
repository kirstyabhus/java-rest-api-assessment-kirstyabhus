package com.cbfacademy.apiassessment.model;

public enum ESGRating {
    AAA(1), AA(2), A(3), BBB(4), BB(5), B(6), CCC(7), UNSPECIFIED(-999);

    private final int order;

    ESGRating(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        // Returning the name of the rating as the meaningful representation
        return this.name();
    }
}
