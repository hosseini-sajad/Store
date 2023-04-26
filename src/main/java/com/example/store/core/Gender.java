package com.example.store.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE(0, "مرد"),
    FEMALE(1, "زن"),
    UNKNOWN(2, "نامشخص");

    private final Integer Key;
    private final String value;
}