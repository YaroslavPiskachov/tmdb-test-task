package com.spintech.testtask.entity;

public enum Gender {
    FEMALE(1), MALE(2);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender getByValue(int value) {
        Gender res = null;
        for (Gender gender : Gender.values()) {
            if (gender.getValue() == value) {
                res = gender;
            }
        }

        return res;
    }
}
