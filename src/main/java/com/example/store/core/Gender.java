package com.example.store.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("مرد"),
    FEMALE("زن"),
    UNKNOWN("نامشخص");

    private final String value;
}
